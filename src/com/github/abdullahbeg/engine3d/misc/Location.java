package com.github.abdullahbeg.engine3d.misc;

public class Location {
    
    private float x, y;

    public Location(float x, float y) {

        this.x = x;
        this.y = y;

    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void setNewLocation(Location location) {

        x = location.getX();
        y = location.getY();

    }

    public void setNewLocation(float x, float y) {

        this.x = x;
        this.y = y;

    }

}
