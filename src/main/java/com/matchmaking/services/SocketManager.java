package com.matchmaking.services;

import org.eclipse.jetty.websocket.api.Session;

import java.util.List;

public interface SocketManager {
    <E> void SendMessage(Session session, E object);
    <E> void BroadcastMessage(List<Session> sessions, E object);
    void BroadcastMessage(List<Session> sessions, String message);
}
