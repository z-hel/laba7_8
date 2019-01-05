package com.example.tester3.laba7_8.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tester3.laba7_8.R;
import com.example.tester3.laba7_8.models.Joke;
import com.example.tester3.laba7_8.ui.viewholders.JokesViewHolder;

import java.util.List;

public class JokesAdapter extends RecyclerView.Adapter<JokesViewHolder>{
    private Context context;
    private List<Joke> listJokes;

    public JokesAdapter (Context context, List<Joke> listJokes) {
        this.context = context;
        this.listJokes = listJokes;
    }

    @NonNull
    @Override
    public JokesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.viewholder_joke, viewGroup, false);

        return new JokesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokesViewHolder jokesViewHolder, int position) {
        jokesViewHolder.bind(listJokes.get(position));
    }

    @Override
    public int getItemCount() {
        return listJokes.size();
    }
}
