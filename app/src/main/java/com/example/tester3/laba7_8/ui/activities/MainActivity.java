package com.example.tester3.laba7_8.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.example.tester3.laba7_8.R;
import com.example.tester3.laba7_8.data.Impl.RepositoryImpl;
import com.example.tester3.laba7_8.data.Repository;
import com.example.tester3.laba7_8.models.Joke;
import com.example.tester3.laba7_8.ui.adapters.JokesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  { //implements SwipeRefreshLayout.OnRefreshListener

    public static MyTask mt;

    public static SwipeRefreshLayout swipeRefreshLayout;

    public static List<Joke> listJokes = new ArrayList<>();


    RecyclerView recyclerView;
    //https://official-joke-api.herokuapp.com/random_ten

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.jokes_list);
        swipeRefreshLayout = findViewById(R.id.swipe_container);


//        listJokes.add(new Joke("type", "setup", "punchline"));


        updateResults();

//        swipeRefreshLayout.setRefreshing(true);

//        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            swipeRefreshLayout.setRefreshing(true);

            if (isNetworkAvailable()) {

                updateResults();

                swipeRefreshLayout.postDelayed(() -> {
                    swipeRefreshLayout.setRefreshing(false);

                    popupError(R.string.dialog_message_long_time, R.string.dialog_title_long_time);
                }, 20000);


            } else if (!isNetworkAvailable()) {

                swipeRefreshLayout.setRefreshing(false);

                popupError(R.string.dialog_message_network_is_not_available, R.string.dialog_title_network_is_not_available);
            }

        });


        }


//        @Override
//        public void onRefresh () {
//
//            swipeRefreshLayout.setRefreshing(true);
//
//            if (isNetworkAvailable()) {
//
//                updateResults();
//
//                swipeRefreshLayout.postDelayed(() -> {
//                    swipeRefreshLayout.setRefreshing(false);
//
//                    popupError(R.string.dialog_message_long_time, R.string.dialog_title_long_time);
//                }, 5000);
//
//
//            } else if (!isNetworkAvailable()) {
//
//                swipeRefreshLayout.setRefreshing(false);
//
//                popupError(R.string.dialog_message_network_is_not_available, R.string.dialog_title_network_is_not_available);
//            }
//
//        }

        public void popupError ( int dialog_message, int dialog_title){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton(R.string.ok, (dialog, id) -> {
                dialog.dismiss();
            });

            builder.setMessage(dialog_message)
                    .setTitle(dialog_title);

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private boolean isNetworkAvailable () {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        private void updateResults () {
            cancelTask();
            listJokes.clear();


            Repository repository = new RepositoryImpl(this);

            mt = new MyTask(repository, jokes -> {
                if (jokes != null) {
                    for (Joke i : jokes) {
                        listJokes.add(i);
                    }

                }

            });
            mt.execute();


            JokesAdapter adapter = new JokesAdapter(this, listJokes);

            recyclerView.setAdapter(adapter);
        }


        private void cancelTask () {

            if (mt == null)
                return;

            mt.cancel(true);
            mt = null;
        }


        public static class MyTask extends AsyncTask<List<Joke>, Void, List<Joke>> {

            private final Repository repository;
            private final OnUpdateJokesCallback callback;

            interface OnUpdateJokesCallback {

                void updateJokes(List<Joke> jokes);
            }

            public MyTask(Repository repository, OnUpdateJokesCallback callback) {
                this.repository = repository;
                this.callback = callback;
            }

            @Override
            protected List<Joke> doInBackground(List<Joke>... lists) {
                return repository.getJokes();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<Joke> result) {
                super.onPostExecute(result);
                callback.updateJokes(result);
            }
        }

    }
