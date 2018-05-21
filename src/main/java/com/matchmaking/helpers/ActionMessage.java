package com.matchmaking.helpers;

public class ActionMessage {
    private int _code;
    private String _payload;

    public ActionMessage(int code, String payload) {
        _code = code;
        _payload = payload;
    }

    public int getCode() {
        return _code;
    }

    public String getPayload() {
        return _payload;
    }
}