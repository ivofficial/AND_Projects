package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        String placeholderImageURL = "https://commons.wikimedia.org/wiki/File:Loading_icon.gif";

        populateUI(sandwich, json);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich, String json) {

        //Map the relevant text fields
        TextView alsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);
        TextView placeOfOriginTV = (TextView) findViewById(R.id.origin_tv);
        TextView descriptionTV = (TextView) findViewById(R.id.description_tv);
        TextView ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);

        //Map the relevant labels
        TextView alsoKnownAsLabel = (TextView) findViewById(R.id.also_known_label);
        TextView placeOfOriginLabel = (TextView) findViewById(R.id.origin_label);
        TextView descriptionLabel = (TextView) findViewById(R.id.description_label);
        TextView ingredientsLabel = (TextView) findViewById(R.id.ingredients_label);

        String akaString = "";
        List<String> akaList = sandwich.getAlsoKnownAs();

        if (akaList != null && akaList.size()>0) {
            akaString = TextUtils.join(" | ", akaList);
        }

        String ingrString = "";
        List<String> ingrList = sandwich.getIngredients();

        if(ingrList != null && ingrList.size()>0) {
            ingrString = TextUtils.join(" | ", ingrList);
        }

        ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
        sv.setBackgroundColor(Color.parseColor("#D3D3D3"));

        //In case certain text fields are empty, display message
        String emptyMessage = "No Additional Info Available";
        int blackColor = Color.BLACK;
        String purpleColor = "#4B0082";

        if (akaString.isEmpty()) {
            alsoKnownAsTV.setText(emptyMessage);
            alsoKnownAsTV.setTextColor(blackColor);
        } else {
            alsoKnownAsTV.setText(akaString);
            alsoKnownAsTV.setTextColor(Color.parseColor(purpleColor));
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginTV.setText(emptyMessage);
            placeOfOriginTV.setTextColor(blackColor);
        } else {
            placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
            placeOfOriginTV.setTextColor(Color.parseColor(purpleColor));
        }

        if (sandwich.getDescription().isEmpty()) {
            descriptionTV.setText(emptyMessage);
            descriptionTV.setTextColor(blackColor);
        } else {
            descriptionTV.setText(sandwich.getDescription());
            descriptionTV.setTextColor(Color.parseColor(purpleColor));
        }

        if (ingrString.isEmpty()) {
            ingredientsTV.setText(emptyMessage);
            ingredientsTV.setTextColor(blackColor);
        } else {
            ingredientsTV.setText(ingrString);
            ingredientsTV.setTextColor(Color.parseColor(purpleColor));
        }

    }
}
