package com.example.tester3.laba7_8.ui.activities;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.tester3.laba7_8.R;
import com.example.tester3.laba7_8.data.Impl.RepositoryImpl;
import com.example.tester3.laba7_8.data.Repository;
import com.example.tester3.laba7_8.models.Joke;
import com.example.tester3.laba7_8.ui.adapters.JokesAdapter;
import org.json.JSONArray;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.os.AsyncTask.Status.FINISHED;

public class MainActivity extends AppCompatActivity {

    RepositoryImpl.MyTask mt;

    private ProgressBar progressBar;
    private List<Joke> listJokes = new ArrayList<>();

    RecyclerView recyclerView;
    //https://official-joke-api.herokuapp.com/random_ten

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.jokes_list);

        Repository repository = new RepositoryImpl(this);


        mt = new RepositoryImpl.MyTask();
        mt.execute();

//        progressBar.setVisibility(View.VISIBLE);
//
//        while (mt.getStatus() != FINISHED) {
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
        progressBar.setVisibility(View.VISIBLE);

        Joke joke = new Joke("type", "setup", "punchline");
        listJokes.add(joke);

        if (repository.getJokes() != null) {
            for (Joke i : repository.getJokes()) { //TODO why repository.getJokes() null?
                listJokes.add(i);
            }
        }
//        listJokes = repository.getJokes();

        JokesAdapter adapter = new JokesAdapter(this, listJokes);

        recyclerView.setAdapter(adapter);


    }

//    public class MyTask extends AsyncTask<Void, Void, Void> {
//
//        static final String URL_STRING =
//                "https://official-joke-api.herokuapp.com/random_ten";
//        String response;
//        Repository repository = new RepositoryImpl(MainActivity.this);
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressBar.setVisibility(View.VISIBLE);
//
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            //                TimeUnit.SECONDS.sleep(2);
//
//            response = creatingURLConnection(URL_STRING);
//            return null;
//
//            //TODO ?
//
//
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//
//
//            //TODO transfer response to data.json
//
//            repository.getJokes(response);
//
//            progressBar.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    public String creatingURLConnection (String GET_URL) {
//        String response = "";
//        HttpURLConnection conn ;
//        StringBuilder jsonResults = new StringBuilder();
//        try {
//            //setting URL to connect with
//            URL url = new URL(GET_URL);
//            //creating connection
//            conn = (HttpURLConnection) url.openConnection();
//            /*
//            converting response into String
//            */
//            InputStreamReader in = new InputStreamReader(conn.getInputStream());
//            int read;
//            char[] buff = new char[1024];
//            while ((read = in.read(buff)) != -1) {
//                jsonResults.append(buff, 0, read);
//            }
//            response = jsonResults.toString();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return response;
//    }
}
