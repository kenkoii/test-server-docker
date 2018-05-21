package com.matchmaking.models;

import org.eclipse.jetty.websocket.api.Session;

public class PlayerData
{
    private String _country;

    private String _id;

    private int _exp;

    private String _name;

    private int _trophy;

    private int _level;

    private int _rank;

    public PlayerData(String country,
                      String id,
                      int exp,
                      String name,
                      int trophy,
                      int level,
                      int rank) {

        _country = country;
        _id = id;
        _name = name;
        _exp = exp;
        _trophy = trophy;
        _level = level;
        _rank = rank;
    }

    public String get_country ()
    {
        return _country;
    }

    public void set_country (String _country)
    {
        this._country = _country;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public int get_exp ()
    {
        return _exp;
    }

    public void set_exp (int _exp)
    {
        this._exp = _exp;
    }

    public String get_name ()
    {
        return _name;
    }

    public void set_name (String _name)
    {
        this._name = _name;
    }

    public int get_trophy ()
    {
        return _trophy;
    }

    public void set_trophy (int _trophy)
    {
        this._trophy = _trophy;
    }

    public int get_level ()
    {
        return _level;
    }

    public void set_level (int _level)
    {
        this._level = _level;
    }

    public int get_rank ()
    {
        return _rank;
    }

    public void set_rank (int _rank)
    {
        this._rank = _rank;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [_country = "+_country+", _id = "+_id+", _exp = "+_exp+", _name = "+_name+", _trophy = "+_trophy+", _level = "+_level+", _rank = "+_rank+"]";
    }
}

