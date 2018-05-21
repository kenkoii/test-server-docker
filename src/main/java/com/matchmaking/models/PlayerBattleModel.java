package com.matchmaking.models;

public class PlayerBattleModel
{
    private PlayerData playerData;

    private String characterId;

    public PlayerData getPlayerData ()
    {
        return playerData;
    }

    public void setPlayerData (PlayerData playerData)
    {
        this.playerData = playerData;
    }

    public String getCharacterId ()
    {
        return characterId;
    }

    public void setCharacterId (String characterId)
    {
        this.characterId = characterId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [playerData = "+playerData+", characterId = "+characterId+"]";
    }
}