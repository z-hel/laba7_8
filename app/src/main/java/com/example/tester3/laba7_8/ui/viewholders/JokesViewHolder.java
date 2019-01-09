package com.example.tester3.laba7_8.ui.viewholders;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.tester3.laba7_8.R;
import com.example.tester3.laba7_8.models.Joke;

public class JokesViewHolder extends RecyclerView.ViewHolder {

    TextView type;
    TextView setup;
    TextView punchline;
    ConstraintLayout constraintLayout;

    public JokesViewHolder(View view) {
        super(view);

        type = view.findViewById(R.id.type_joke);
        setup = view.findViewById(R.id.setup_joke);
        punchline = view.findViewById(R.id.punchline_joke);
        constraintLayout = view.findViewById(R.id.constraint_layout);

    }

    public void bind(Joke joke, int position) {

        type.setText(String.format("№ %d. %s", position + 1, joke.getType()));
        setup.setText(String.format("- %s", joke.getSetup()));
        punchline.setText(String.format("- %s", joke.getPunchline()));
    }
}
