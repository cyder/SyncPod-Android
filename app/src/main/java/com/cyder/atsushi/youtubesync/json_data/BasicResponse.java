package com.cyder.atsushi.youtubesync.json_data;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/26.
 */

public class BasicResponse extends JsonParameter {
    public String result;
    public User user;
    public Room room;
    public ArrayList<Room> joined_rooms;
}
