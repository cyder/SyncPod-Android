package com.cyder.atsushi.youtubesync.json_data;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/27.
 */

public class Room {
    public int id;
    public String name;
    public String description;
    public String key;
    public Video now_playing_video;
    public Video last_played_video;
    public ArrayList<User> online_users;
}
