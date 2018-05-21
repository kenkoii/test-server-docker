package com.matchmaking.services;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.List;

public class SocketMessageManager implements SocketManager {
    private static SocketManager _instance = null;
    private Gson g = new Gson();

    public static SocketManager getInstance() {
        if(_instance == null){
            _instance = new SocketMessageManager();
        }
        return _instance;
    }

    public <E> void SendMessage(Session session, E object) {
        String message = g.toJson(object);
        System.out.println("Sending Message: " + message);
        try {
            session.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <E> void BroadcastMessage(List<Session> sessions, E object) {
        sessions.stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(g.toJson(object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void BroadcastMessage(List<Session> sessions, String message) {
        sessions.stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
