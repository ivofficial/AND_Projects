package gwg.ivo.android.popularmovies;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_POSTER = "extra_poster";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_RATING = "extra_rating";
    public static String EXTRA_DATE = "extra_date";
    public static String EXTRA_PARENT_VIEW = "extra_parent_view";

    public static String BACKGROUND_COLOR = "#5d7396";
    private String parentView = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();

        String title = intent.getStringExtra(EXTRA_TITLE);
        String posterUrl = intent.getStringExtra(EXTRA_POSTER);
        String overview = intent.getStringExtra(EXTRA_OVERVIEW);
        String rating = intent.getStringExtra(EXTRA_RATING);
        String releaseDate = intent.getStringExtra(EXTRA_DATE);
        parentView= intent.getStringExtra(EXTRA_PARENT_VIEW);

        ImageView posterIV = (ImageView) findViewById(R.id.poster_iv);
        Picasso.with(this)
                .load(posterUrl)
                .error(R.drawable.image48x48)
                .placeholder(R.drawable.image48x48)
                .into(posterIV);

        TextView titleTV = (TextView) findViewById(R.id.title_tv);
        titleTV.setText(title);

        TextView ratingTV = (TextView) findViewById(R.id.rating_tv);
        ratingTV.setText(rating);

        TextView dateTV = (TextView) findViewById(R.id.date_tv);
        dateTV.setText(releaseDate);

        TextView overviewTV = (TextView) findViewById(R.id.overview_tv);
        overviewTV.setText(overview);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.movie_detail_layout);
        constraintLayout.setBackgroundColor(Color.parseColor(BACKGROUND_COLOR));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (parentView.equals(MainActivity.RATING_VIEW)) {
                MainActivity.backNavigation = MainActivity.RATING_VIEW;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
