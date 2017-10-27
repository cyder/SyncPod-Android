package com.example.atsushi.youtubesync.json_data;

/**
 * Created by atsushi on 2017/10/27.
 */

public class CreateRoomParam {
    public Room room;

    public CreateRoomParam(String name, String description) {
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
