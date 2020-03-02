package com.example.synconlinedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

    }
}
