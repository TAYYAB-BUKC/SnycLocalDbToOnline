package com.example.synconlinedatabase;

public class User {

    private String Name;
    private int syncStatus;

    public User(String Name,int syncStatus)
    {
     this.setName(Name);
     this.setSyncStatus(syncStatus);
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }


}
