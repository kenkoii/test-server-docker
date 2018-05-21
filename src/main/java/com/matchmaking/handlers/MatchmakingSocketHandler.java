package com.matchmaking.handlers;

import com.google.gson.Gson;
import com.matchmaking.helpers.ActionMessage;
import com.matchmaking.models.PlayerData;
import com.matchmaking.models.RoomData;
import com.matchmaking.services.MatchmakerService;
import com.matchmaking.services.SocketManager;
import com.matchmaking.services.SocketMessageManager;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@WebSocket
public class MatchmakingSocketHandler {
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    private static MatchmakerService matchmakerService = MatchmakerService.getInstance();
    private SocketManager socketManager = SocketMessageManager.getInstance();

    @OnWebSocketConnect
    public void onConnected(Session session) {
        System.out.println("Connected: " + session.getRemoteAddress());
        sessions.add(session);
        socketManager.SendMessage(session, new ActionMessage(0, ""));
    }

    @OnWebSocketClose
    public void onClosed(Session session, int statusCode, String reason) {
        System.out.println("Disconnected: " + session.getRemoteAddress());
        PlayerData removedPlayer = matchmakerService.RemoveFromMatchmakingPool(session);
        sessions.remove(session);
        socketManager.BroadcastMessage((List)sessions, matchmakerService.GetMatchmakingPool().values());
        socketManager.BroadcastMessage((List)sessions, removedPlayer.get_name() + " has left.");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("Got: " + message);
        OnMessageHandler(session, message);
    }

    private void OnMessageHandler(Session session, String message) throws IOException {
        Gson g = new Gson();

        ActionMessage msg = g.fromJson(message, ActionMessage.class);
        switch(msg.getCode()) {
            case 0:
                PlayerData player = g.fromJson(msg.getPayload(), PlayerData.class);
                matchmakerService.AddToMatchmakingPool(session, player);
                break;
            case 2:
                RoomData room = g.fromJson(msg.getPayload(), RoomData.class);
                Session player2 = matchmakerService.GetMatchedPlayers().get(session);
                socketManager.SendMessage(player2, new ActionMessage(2, g.toJson(room)));
                break;

        }
    }

}