package com.example.ttphong.loginapplication;

import java.io.Serializable;

/**
 * Created by ttphong on 7/27/2016.
 */
public class Album implements Serializable {
    private int mId;
    private String mName;

    public Album(){

    }

    public Album(String name) {
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String toString(){
        return this.mName;
    }
}
