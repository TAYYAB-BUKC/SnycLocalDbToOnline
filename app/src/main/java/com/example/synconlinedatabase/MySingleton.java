package com.example.synconlinedatabase;

import android.content.Context;

import androidx.core.util.Pools;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    public MySingleton(Context context) {
        this.myContext = context;
        requestQueue = getRequestQueue();
    }

    private static MySingleton Instance;
    private RequestQueue requestQueue;
    private static Context myContext;

    private RequestQueue getRequestQueue()
    {
        if ( requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(myContext.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context)
    {
        if (Instance == null){
            Instance = new MySingleton(context);
        }
        return Instance;
    }

    public<T> void AddToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
