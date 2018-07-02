package gwg.ivo.android.bakingapp.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import gwg.ivo.android.bakingapp.MainActivity;
import gwg.ivo.android.bakingapp.R;
import gwg.ivo.android.bakingapp.model.Recipe;
import gwg.ivo.android.bakingapp.ui.RecipeDetailActivity;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Recipe mRecipe;
    List<Recipe> mRecipeList;

    public GridRemoteViewsFactory (Context context) {
        mRecipeList = MainActivity.getRecipeList();
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipeList = MainActivity.getRecipeList();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mRecipeList == null) return -1;
        return mRecipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mContext == null) return null;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.layout_widget);
        Intent fillInIntent = new Intent();
        mRecipe = mRecipeList.get(position);
        views.setTextViewText(R.id.widget_text_view, mRecipe.getName());
        Bundle extras = new Bundle();
        extras.putParcelable(RecipeDetailActivity.EXTRA_RECIPE, mRecipe);
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_image_view, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

