package gwg.ivo.android.popularmovies.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

import gwg.ivo.android.popularmovies.MainActivity;
import gwg.ivo.android.popularmovies.R;

public class MoviesAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Movie> mMovies;

    private static final String IMDB_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE_W92 = "w92";
    private static final String IMAGE_SIZE_W154 = "w154";
    private static final String IMAGE_SIZE_W185 = "w185";
    private static final String IMAGE_SIZE_W342 = "w342";
    private static final String IMAGE_SIZE_W500 = "w500";
    private static final String IMAGE_SIZE_W780 = "w780";
    private static final String IMAGE_SIZE_ORIGINAL = "original";

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.mContext = context;
        this.mMovies = movies;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = mMovies.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_movie_grid, null);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_grid_image_view);

        if (movie != null) {
            String imageUrl = IMDB_IMAGE_URL + IMAGE_SIZE_W342 + movie.getPosterPath();
            Picasso.with(mContext)
                    .load(imageUrl)
                    .into(imageView);
        }

        return convertView;
    }


}
