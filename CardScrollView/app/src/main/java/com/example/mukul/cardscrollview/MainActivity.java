package com.example.mukul.cardscrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.glass.app.Card.ImageLayout;
import com.google.android.glass.widget.CardScrollView;

import info.androidhive.cardscrollview.R;


public class MainActivity extends Activity {

    private List<MovieCard> mCards;
    private CardScrollView mCardScrollView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        prepareMovieCards();

        mCardScrollView = new CardScrollView(this);
        MovieCardsAdapter adapter = new MovieCardsAdapter(context, mCards);
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
    }

    private void prepareMovieCards() {
        mCards = new ArrayList<MovieCard>();

        // Card with no background image
        MovieCard mc = new MovieCard("I don't know. But who cares! Ha ha!",
                "Wait! What does that mean?", ImageLayout.FULL, new int[] {});
        mCards.add(mc);

        // Card with full background image
        mc = new MovieCard("I wanna go home. Does anyone know where my dad is?",
                "Pet store?", ImageLayout.FULL,
                new int[] { R.drawable.card_full });
        mCards.add(mc);

        // Card with full background of 3 images
        mc = new MovieCard("Dude? Dude? Focus dude... Dude?",
                "Oh, he lives. Hey, dude!", ImageLayout.FULL, new int[] {
                R.drawable.card_bottom_left,
                R.drawable.card_bottom_right, R.drawable.card_top });
        mCards.add(mc);

        // Card with left aligned images
        mc = new MovieCard("Just keep swimming.",
                "I'm sorry, Dory. But I... do", ImageLayout.LEFT, new int[] {
                R.drawable.card_bottom_left,
                R.drawable.card_bottom_right, R.drawable.card_top });
        mCards.add(mc);

    }
}