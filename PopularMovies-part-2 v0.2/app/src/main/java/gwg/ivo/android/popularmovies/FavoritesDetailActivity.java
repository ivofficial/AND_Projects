package gwg.ivo.android.popularmovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDetailActivity extends AppCompatActivity {

    private List<Object[]> resultsList = new ArrayList<>();
    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_POSTER = "extra_poster";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_RATING = "extra_rating";
    public static String EXTRA_DATE = "extra_date";
    public static String EXTRA_DATE_ADDED = "extra_date_added";
    public static String EXTRA_PARENT_VIEW = "extra_parent_view";

    public static String BACKGROUND_COLOR = "#b7d2ff";
    private String parentView = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_detail);

        Intent intent = getIntent();

        String title = intent.getStringExtra(EXTRA_TITLE);
        byte[] imageByteArray = intent.getByteArrayExtra(EXTRA_POSTER);
        String overview = intent.getStringExtra(EXTRA_OVERVIEW);
        String rating = intent.getStringExtra(EXTRA_RATING);
        String releaseDate = intent.getStringExtra(EXTRA_DATE);
        String dateAdded= intent.getStringExtra(EXTRA_DATE_ADDED);

        ImageView posterIV = (ImageView) findViewById(R.id.fav_poster_iv);
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray,0, imageByteArray.length);
        posterIV.setImageBitmap(imageBitmap);

        TextView titleTV = (TextView) findViewById(R.id.fav_title_tv);
        titleTV.setText(title);

        TextView ratingTV = (TextView) findViewById(R.id.fav_rating_tv);
        ratingTV.setText(rating);

        TextView dateTV = (TextView) findViewById(R.id.fav_date_tv);
        dateTV.setText(releaseDate);

        TextView overviewTV = (TextView) findViewById(R.id.fav_overview_tv);
        overviewTV.setText(overview);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.fav_movie_detail_layout);
        constraintLayout.setBackgroundColor(Color.parseColor(BACKGROUND_COLOR));
    }

}
