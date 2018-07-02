package gwg.ivo.android.bakingapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gwg.ivo.android.bakingapp.R;
import gwg.ivo.android.bakingapp.model.Ingredient;

public class IngredientsListAdapter extends BaseAdapter {

    private List<Ingredient> mIngredientsList;
    private Context mContext;

    public IngredientsListAdapter(Context context, List<Ingredient> ingredients) {
        this.mContext = context;
        this.mIngredientsList = ingredients;
    }

    @Override
    public int getCount() {
        if (mIngredientsList != null) return mIngredientsList.size();
        return -1;
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
        if (mContext == null || mIngredientsList == null || position >= mIngredientsList.size()) return null;

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_ingredients_list, null);
        }

        final TextView ingredientTV = (TextView) convertView.findViewById(R.id.ingredient_tv);
        final TextView measureTV = (TextView) convertView.findViewById(R.id.measure_tv);
        final TextView quantityTV = (TextView) convertView.findViewById(R.id.quantity_tv);

        ingredientTV.setText(mIngredientsList.get(position).getIngredient());
        measureTV.setText(mIngredientsList.get(position).getMeasure());
        quantityTV.setText(mIngredientsList.get(position).getQuantity());

        return convertView;
    }
}
