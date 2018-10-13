package com.example.geoguessswipe;

import android.graphics.drawable.Drawable;

/**
 * This class is used to create an object. This has two variables one for the drawable id an the
 * other for the boolean.
 */
public class GeoImage {
    //Variables
    private Drawable id;
    private boolean europe;

    //Constructor
    public GeoImage(Drawable id, boolean europe) {
        this.id = id;
        this.europe = europe;
    }

    //Getter
    public Drawable getId() {
        return id;
    }

    //Setter
    public void setId(Drawable id) {
        this.id = id;
    }
    //Getter
    public boolean isEurope() {
        return europe;
    }

    //Setter
    public void setEurope(boolean europe) {
        this.europe = europe;
    }

}
