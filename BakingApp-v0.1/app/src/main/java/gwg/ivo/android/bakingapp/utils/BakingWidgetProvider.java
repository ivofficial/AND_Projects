package gwg.ivo.android.bakingapp.utils;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import gwg.ivo.android.bakingapp.MainActivity;
import gwg.ivo.android.bakingapp.R;
import gwg.ivo.android.bakingapp.ui.RecipeDetailActivity;

public class BakingWidgetProvider extends AppWidgetProvider {

    private static final int MIN_WIDTH_GRID = 100;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews views;
        if (width < MIN_WIDTH_GRID) {
            views = getSingleRecipeRemoteView(context);
        } else {
            views = getRecipeGridRemoteView(context);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int id : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, id);
        }
    }

    private static RemoteViews getSingleRecipeRemoteView (Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        views.setOnClickPendingIntent(R.id.widget_image_view, pendingIntent);

        return views;
    }

    private static RemoteViews getRecipeGridRemoteView (Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget_grid);
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        Intent appIntent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, pendingIntent);
        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);

        return views;
    }
}
