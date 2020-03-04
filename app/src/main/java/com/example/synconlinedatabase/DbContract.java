package com.example.synconlinedatabase;

import java.util.jar.Attributes;

public class DbContract {

    public final static int SYNC_STATUS_OK = 0;
    public final static int SYNC_STATUS_FAILED = 1;
    public final static String SERVER_FILE_URL = "https://tayyabdost.000webhostapp.com/Insert.php";


    //local Db Schema

    public final static String DATABASE_NAME = "TestDb";
    public final static String TABLE_NAME = "Users";
    public final static String NAME= "Name";
    public final static String SYNC_STATUS = "SyncStatus";

}
