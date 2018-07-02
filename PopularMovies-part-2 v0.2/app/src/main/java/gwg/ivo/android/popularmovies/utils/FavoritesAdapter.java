package gwg.ivo.android.popularmovies.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.List;

import gwg.ivo.android.popularmovies.R;

public class FavoritesAdapter extends BaseAdapter {

    private Context mContext;
    private List<Object[]> mObjects;
    public static final int MOVIE_INDEX = 0;
    public static final int BITMAP_INDEX = 1;
    public static final int DATE_ADDED_INDEX = 2;

    public FavoritesAdapter(Context context, List<Object[]> objects) {
        this.mContext = context;
        this.mObjects = objects;
    }

    @Override
    public int getCount() {
        return mObjects.size();
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

        Object[] objects = mObjects.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_favorites_grid, null);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.favorites_grid_image_view);

        if (objects != null) {
            byte[] imageByteArray = (byte[]) objects[BITMAP_INDEX];
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            imageView.setImageBitmap(imageBitmap);
        }

        return convertView;
    }
}
