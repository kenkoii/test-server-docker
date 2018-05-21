package com.matchmaking.services;

import com.matchmaking.helpers.ActionMessage;
import com.matchmaking.models.PlayerData;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatchmakerService implements MatchService {

    private Map<Session, PlayerData> matchmakingPool = new ConcurrentHashMap<>();
    private Map<Session, Session> matchedPlayers = new ConcurrentHashMap<>();

    private static volatile MatchmakerService _instance = null;
    private static SocketManager _socketManager = SocketMessageManager.getInstance();

    public static MatchmakerService getInstance() {
        if(_instance == null){
            // Make sure it's thread safe
            synchronized(MatchmakerService.class) {
                if (_instance == null) {
                    _instance = new MatchmakerService();
                }
            }
        }
        return _instance;
    }

    public static void setSocketManager(SocketManager socketManager)
    {
        _socketManager = socketManager;
    }

    public void AddToMatchmakingPool(Session session, PlayerData player) {
        if(GetMatchmakingPool().size() > 0) {
            PlayerData opponent = FindOpponentFromPool(player);
            if(opponent == null) {
                matchmakingPool.put(session, player);
                return;
            }

            for (Map.Entry s : matchmakingPool.entrySet()) {
                if (s.getValue() == opponent) {
                    RemoveFromMatchmakingPool((Session)s.getKey());
                    Session gameHost = (Session)s.getKey();
                    AddToMatchedPlayers(gameHost, session);
                    _socketManager.SendMessage(gameHost, new ActionMessage(1, ""));
                }
            }
        } else {
            matchmakingPool.put(session, player);
        }
    }

    public void AddToMatchedPlayers(Session host, Session client) {
        if(matchedPlayers.containsKey(host))
        {
            return;
        }
        matchedPlayers.put(host, client);
    }

    public PlayerData RemoveFromMatchmakingPool(Session session) {  return matchmakingPool.remove(session); }

    public Session RemoveFromMatchedPlayers(Session session) { return matchedPlayers.remove(session); }

    public Map<Session, PlayerData> GetMatchmakingPool() {  return matchmakingPool; }

    public Map<Session, Session> GetMatchedPlayers() { return matchedPlayers; }


    //TODO: Abstract Transfer Matching logic
    private PlayerData FindOpponentFromPool(PlayerData player) {
        return matchmakingPool.values().stream()
                .filter(p -> CanBeMatched(p, player))
                .findAny()
                .orElse(null);
    }

    //TODO: Make Logic about this.
    private boolean CanBeMatched(PlayerData player1, PlayerData player2) {
        long rankDifference = 1;
        long actualDifference = (player1.get_rank() - player2.get_rank());
//        return (actualDifference <= rankDifference && actualDifference >= rankDifference * -1);
        return true;
    }

}
