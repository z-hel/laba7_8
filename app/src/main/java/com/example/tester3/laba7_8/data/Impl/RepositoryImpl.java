package com.example.tester3.laba7_8.data.Impl;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.tester3.laba7_8.R;
import com.example.tester3.laba7_8.data.Repository;
import com.example.tester3.laba7_8.models.Joke;
import com.example.tester3.laba7_8.ui.activities.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    private static Context context;
    private static String json;

    public RepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<Joke> getJokes() {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<ArrayList<Joke>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }


    public static class MyTask extends AsyncTask<Void, Void, Void> {

        static final String URL_STRING =
                "https://official-joke-api.herokuapp.com/random_ten";
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            progressBar.setVisibility(View.VISIBLE);



        }

        @Override
        protected Void doInBackground(Void... params) {

            response = creatingURLConnection(URL_STRING);
            return null;


        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            json = response;

//            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static String creatingURLConnection (String GET_URL) {
        String response = "";
        HttpURLConnection conn ;
        StringBuilder jsonResults = new StringBuilder();
        try {
            //setting URL to connect with
            URL url = new URL(GET_URL);
            //creating connection
            conn = (HttpURLConnection) url.openConnection();
            /*
            converting response into String
            */
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            response = jsonResults.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }


}
