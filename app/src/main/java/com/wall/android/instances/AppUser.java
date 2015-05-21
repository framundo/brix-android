package com.wall.android.instances;

public class AppUser {
    final String id;
    final String name;

    public AppUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
