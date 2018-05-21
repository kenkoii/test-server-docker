package com.matchmaking.services;

import com.matchmaking.models.PlayerData;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;

public interface MatchService {
    void AddToMatchmakingPool(Session session, PlayerData player);
    PlayerData RemoveFromMatchmakingPool(Session session);
    void AddToMatchedPlayers(Session host, Session client);
    Session RemoveFromMatchedPlayers(Session session);
    Map<Session, PlayerData> GetMatchmakingPool();
    Map<Session, Session> GetMatchedPlayers();
}
