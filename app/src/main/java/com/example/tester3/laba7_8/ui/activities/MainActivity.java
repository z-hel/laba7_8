package com.example.tester3.laba7_8.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    MyTask mt;

    private static ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Joke> listJokes = new ArrayList<>();

//    public static final int STATUS_TASK_PRE = 0;
//    public static final int STATUS_TASK_POST = 1;

    RecyclerView recyclerView;
    //https://official-joke-api.herokuapp.com/random_ten

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.jokes_list);
        swipeRefreshLayout = findViewById(R.id.swipe_container);

//        updateResults();

        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(this); // THIS???

    }


    @Override
    public void onRefresh() {

//        cancelTask();
//        listJokes.clear();
//
//
//        Repository repository = new RepositoryImpl(this);
//
//        mt = new MyTask(repository, jokes -> {
//            if (jokes != null) {
//                for (Joke i : repository.getJokes()) {
//                    listJokes.add(i);
//                }
//            }
//        });
//        mt.execute();
//
//        if (repository.getJokes() != null) {
//            for (Joke i : repository.getJokes()) {
//                listJokes.add(i);
//            }
//        }
////        if (result != null) {
////            for (Joke i : result) {
////                listJokes.add(i);
////            }
////        }
//
//        for (Joke i : repository.getJokes()) {
//            System.out.println(i);
//        }
//
//
//        JokesAdapter adapter = new JokesAdapter(this, listJokes);
//
//        recyclerView.setAdapter(adapter);



        updateResults();
        swipeRefreshLayout.setRefreshing(false);
    }


//    private static void updateProgressBar(int statusTask) {
//
//        if (statusTask == STATUS_TASK_PRE) {
//            progressBar.setVisibility(View.VISIBLE);
//        }
//        if (statusTask == STATUS_TASK_POST) {
//            progressBar.setVisibility(View.INVISIBLE);
//        }
//
//    }


    private void updateResults() {
        cancelTask();
        listJokes.clear();


        Repository repository = new RepositoryImpl(this);

        mt = new MyTask(repository, jokes -> {
            if (jokes != null) {
                for (Joke i : jokes) {
                    listJokes.add(i);
                }

                for (Joke i : jokes) {
                    System.out.println("///////////////////////////" + i);
                }
            }

        });
        mt.execute();


        JokesAdapter adapter = new JokesAdapter(this, listJokes);

        recyclerView.setAdapter(adapter);
    }


    private void cancelTask() {

        if (mt == null)
            return;

        mt.cancel(true);
        mt = null;
    }


    public static class MyTask extends AsyncTask<List<Joke>, Void, List<Joke>> {

//        static final String URL_STRING =
//                "https://official-joke-api.herokuapp.com/random_ten";
//        String response;
        private final Repository repository;
        private final OnUpdateJokesCallback callback;

        interface OnUpdateJokesCallback {

            void updateJokes(List<Joke> jokes);
        }

        public MyTask (Repository repository, OnUpdateJokesCallback callback) {
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
//            updateProgressBar(STATUS_TASK_PRE);
        }

        @Override
        protected void onPostExecute(List<Joke> result) {
            super.onPostExecute(result);
//            updateProgressBar(STATUS_TASK_POST);
            callback.updateJokes(result);
        }
    }

}
