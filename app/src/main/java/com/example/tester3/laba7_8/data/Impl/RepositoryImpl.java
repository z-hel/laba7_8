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

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    private static Context context;
    private static String json;
    private static final String BASE_URL =
            "https://official-joke-api.herokuapp.com"; // + /random_ten

    public RepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<Joke> getJokes() {

        HttpURLConnection urlConnection = null;
        List<Joke> result = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            URL url = new URL(String.format("%s%s", BASE_URL, "/random_ten"));

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setConnectTimeout(10000);

            int response = urlConnection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {


                InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }

            } else if (response == HttpURLConnection.HTTP_GATEWAY_TIMEOUT || response == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
                jsonResults = new StringBuilder("timeout");
            }

            json = jsonResults.toString();


            Gson gson = new Gson();



            Type listType = new TypeToken<ArrayList<Joke>>() {
            }.getType();

            result = gson.fromJson(json, listType);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return result;
    }


}
