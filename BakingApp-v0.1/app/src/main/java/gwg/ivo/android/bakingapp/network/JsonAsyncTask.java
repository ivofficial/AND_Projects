package gwg.ivo.android.bakingapp.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import gwg.ivo.android.bakingapp.MainActivity;
import gwg.ivo.android.bakingapp.model.Recipe;

public class JsonAsyncTask extends AsyncTask {

    private static final String TAG = "JsonAsyncTask";

    private Context mContext;
    private String mFileName;
    private List<Recipe> mRecipeList;

    public JsonAsyncTask(Context context, String fileName) {
        this.mContext = context;
        this.mFileName = fileName;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if(mContext != null && mFileName != null) {
            String jsonString = "";
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(mContext.getAssets().open(mFileName)));
                String line;
                while ((line = bufferedReader.readLine()) != null ) {
                    jsonString = jsonString.concat(line);
                }
                mRecipeList = ParseRecipeJson.parseRecipeListFromJson(jsonString);
                MainActivity.setRecipeList(mRecipeList);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } finally {
                if (bufferedReader != null ) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        } else {
            mRecipeList = new ArrayList<>();
        }

        return mRecipeList;
    }

    @Override
    protected void onPostExecute(Object o) {
        MainActivity.setRecipeList(mRecipeList);
    }
}
