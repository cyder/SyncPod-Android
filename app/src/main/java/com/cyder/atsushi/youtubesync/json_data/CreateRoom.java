package com.cyder.atsushi.youtubesync.json_data;

/**
 * Created by atsushi on 2017/10/27.
 */

public class CreateRoom extends JsonParameter{
    public Room room;

    public CreateRoom(String name, String description) {
        room = new Room(name, description);
    }

    private class Room {
        public String name;
        public String description;

        public Room(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
}
