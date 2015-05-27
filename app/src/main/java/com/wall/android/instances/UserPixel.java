package com.wall.android.instances;

public class UserPixel {

    final int id;
    final int x;
    final int y;
    final String color;

    public UserPixel(int id, int x, int y, String color) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

}
