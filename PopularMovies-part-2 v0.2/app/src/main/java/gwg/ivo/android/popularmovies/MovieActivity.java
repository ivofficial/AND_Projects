package gwg.ivo.android.popularmovies;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import gwg.ivo.android.popularmovies.utils.TabFragmentPagerAdapter;

public class MovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_POSTER = "extra_poster";
    public static final String EXTRA_OVERVIEW = "extra_overview";
    public static final String EXTRA_RATING = "extra_rating";
    public static final String EXTRA_DATE = "extra_date";
    public static final String EXTRA_PARENT_VIEW = "extra_parent_view";

    public static final int INDEX_AUTHOR = 0;
    public static final int INDEX_CONTENT = 1;

    private String parentView = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager(), this, getIntent().getExtras()));
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
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
