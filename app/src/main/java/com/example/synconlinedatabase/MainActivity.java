package com.example.synconlinedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editTextName;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter recyclerAdapter;
    public ArrayList<User> users = new ArrayList<>();
    private ProgressDialog pDialog;

    //JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RecyclerView);
        editTextName = findViewById(R.id.Name);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerAdapter = new RecyclerAdapter(users);
        recyclerView.setAdapter(recyclerAdapter);

    }


    public void onSubmitButton(View view) {
        String name = editTextName.getText().toString();
        saveDataToServer(name);
        editTextName.setText("");
    }


    public void readFromLocalStorage()
    {
        users.clear();
        DbHelper dbHelper = new DbHelper(getApplicationContext());
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

    private void saveDataToServer(final String name){

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //if connection is available send data to Server otherwise save it to local storage
        if (CheckInternetConnection()){
            //final String innername = name;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_FILE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String serverResponse = jsonObject.getString("response");

                                if (serverResponse.equals("OK")) {
                                    saveDataToLocalStorage(name,DbContract.SYNC_STATUS_OK);
                                }
                                else{
                                    saveDataToLocalStorage(name,DbContract.SYNC_STATUS_FAILED);
                                }
                                }
                            catch(Exception e)
                            {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                        saveDataToLocalStorage(name,DbContract.SYNC_STATUS_FAILED);
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("Name",name);
                    return params;
                }
            };

            MySingleton.getInstance(MainActivity.this).AddToRequestQueue(stringRequest);
        }
        else {
            saveDataToLocalStorage(name,DbContract.SYNC_STATUS_FAILED);
        }
        Toast.makeText(this,"Data has been saved successfully",Toast.LENGTH_LONG);
    }


    public boolean CheckInternetConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void saveDataToLocalStorage(String name,int syncStatus){

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.SaveToLocalDatabase(name,syncStatus,database);
        readFromLocalStorage();
        dbHelper.close();
    }

    /**
     * Background Async Task to Create new Patient
     * */
    /*class AddDataToServer extends AsyncTask<String, String, String> {

        *//**
         * Before starting background thread Show Progress Dialog
         * *//*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Uploading to server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        *//**
         * Creating product
         * *//*
        protected String doInBackground(String... args) {
            String name = inputName.getText().toString();
            String gender = inputGender.getText().toString();
            String email = inputEmail.getText().toString();
            String Address= inputAddress.getText().toString();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("patient_name", name));
            params.add(new BasicNameValuePair("patient_gender", gender));
            params.add(new BasicNameValuePair("patient_email", email));
            params.add(new BasicNameValuePair("patient_address",Address));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), patient_info.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        *//**
         * After completing background task Dismiss the progress dialog
         * **//*
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }*/
}


