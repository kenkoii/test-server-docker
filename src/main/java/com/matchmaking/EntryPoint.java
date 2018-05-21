package com.matchmaking;

import com.matchmaking.handlers.MatchmakingSocketHandler;

import static spark.Spark.*;

public class EntryPoint {

    public static void main(String[] args) {
        webSocket("/matchmake", MatchmakingSocketHandler.class);
        get("/", (req, res) -> "Matchmaking Server is now running.");
        init();
    }
}