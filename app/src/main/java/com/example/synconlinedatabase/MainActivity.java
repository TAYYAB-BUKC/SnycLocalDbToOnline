package com.example.synconlinedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editTextName;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter recyclerAdapter;
    public ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        editTextName = (EditText)findViewById(R.id.Name);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerAdapter = new RecyclerAdapter(users);
        recyclerView.setAdapter(recyclerAdapter);

    }


    public void onSubmitButton(View view) {
        String name = editTextName.getText().toString();
        saveData(name);
        editTextName.setText("");
    }


    public void readFromLocalStorage()
    {
        users.clear();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readFromLocalDatabase(database);

        while (cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex(DbContract.NAME));
            int sync_status = cursor.getInt(cursor.getColumnIndex(DbContract.SYNC_STATUS));
            users.add(new User(name,sync_status));
        }

        if (users.size() == 0)
        {
            Toast.makeText(this,"No reccrd found in the database",Toast.LENGTH_LONG);
        }
        else {
            recyclerAdapter.notifyDataSetChanged();
            cursor.close();
            dbHelper.close();
        }
    }

    private void saveData(String name){

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //if connection is available send data to Server otherwise save it to local storage
        if (CheckInternetConnection()){

        }
        else {
                dbHelper.SaveToLocalDatabase(name,DbContract.SYNC_STATUS_FAILED,database);
        }
        readFromLocalStorage();
        dbHelper.close();

    }


    public boolean CheckInternetConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }

}
