package gwg.ivo.android.bakingapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import gwg.ivo.android.bakingapp.R;
import gwg.ivo.android.bakingapp.model.Recipe;

public class RecipeListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Recipe> mRecipeList;

    public RecipeListAdapter(Context context, List<Recipe> recipes) {
        this.mContext = context;
        this.mRecipeList = recipes;
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
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
        if (mRecipeList == null || mContext == null) {
            return null;
        } else if (position >= mRecipeList.size()) {
            return null;
        }
        Recipe recipe = mRecipeList.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_recipe_grid, null);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.recipe_image_view);
        final TextView textView = (TextView) convertView.findViewById(R.id.recipe_title_tv);

        if (recipe != null) {
            textView.setText(recipe.getName());
            String imageUrl = recipe.getImage();
            if(imageUrl != null && imageUrl.trim().length()>0) {
                Picasso.with(mContext)
                        .load(imageUrl)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.recipe_512);
            }
        }

        return convertView;
    }
}
