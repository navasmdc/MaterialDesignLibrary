package com.example.mukul.cardscrollview;

/**
 * Created by mukul on 10/21/2015.
 */




        import java.util.List;

        import android.content.Context;
        import android.view.View;
        import android.view.ViewGroup;

        import com.google.android.glass.app.Card;
        import com.google.android.glass.widget.CardScrollAdapter;

//        import com.google.android.glass.app.Card;
  //      import com.google.android.glass.widget.CardScrollAdapter;

public class MovieCardsAdapter extends CardScrollAdapter {
    private List<MovieCard> mCards;
    private Context context;

    public MovieCardsAdapter(Context context, List<MovieCard> mCards) {
        this.context = context;
        this.mCards = mCards;
    }


    public int getPosition(Object item) {
        return mCards.indexOf(item);
    }


    public int getCount() {
        return mCards.size();
    }

    @Override
    public Object getItem(int position) {
        return mCards.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = new Card(context);

        MovieCard mc = mCards.get(position);

        // Card text
        if (mc.getText() != null)
            card.setText(mc.getText());

        // Card footer note
        if (mc.getFooterText() != null)
            card.setFootnote(mc.getFooterText());

        // Set image layout
        if (mc.getImgLayout() != null)
            card.setImageLayout(mc.getImgLayout());

        // loop and set card images
        for(int img : mc.getImages()){
            card.addImage(img);
        }

        return card.toView();
    }

    @Override
    public int findIdPosition(Object o) {
        return 0;
    }

    @Override
    public int findItemPosition(Object o) {
        return 0;
    }


}
