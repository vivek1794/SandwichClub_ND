package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView sandwichImageView = findViewById(R.id.image_iv);

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

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView placeOfOriginLabel = findViewById(R.id.detail_place_of_origin_label_tv);
        TextView placeOfOrigin = findViewById(R.id.origin_tv);

        TextView alsoKnownAsLabel = findViewById(R.id.also_known_label_tv);
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);

        TextView ingredientsLabel = findViewById(R.id.ingredients_label_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);

        TextView descriptionLabel = findViewById(R.id.description_label_tv);
        TextView description = findViewById(R.id.description_tv);

        if (checkIfNullOrEmpty(sandwich.getPlaceOfOrigin())) {
            placeOfOrigin.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);
        } else {
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        if (checkIfNullOrEmpty(sandwich.getDescription())) {
            description.setVisibility(View.GONE);
            descriptionLabel.setVisibility(View.GONE);
        } else {
            description.setText(sandwich.getDescription());
        }

        if (checkIfNullOrEmpty(sandwich.getIngredients())) {
            ingredients.setVisibility(View.GONE);
            ingredientsLabel.setVisibility(View.GONE);
        } else {
            ingredients.setText(TextUtils.join(",", sandwich.getIngredients()));
        }

        if (checkIfNullOrEmpty(sandwich.getAlsoKnownAs())) {
            alsoKnownAs.setVisibility(View.GONE);
            alsoKnownAsLabel.setVisibility(View.GONE);
        } else {
            alsoKnownAs.setText(TextUtils.join(",", sandwich.getAlsoKnownAs()));
        }
    }


    /**
     * This method would essentially return `true` if the given value is null or empty (string or list)
     *
     * @param t
     * @param <T>
     * @return true or false depending on the value
     */
    private <T> boolean checkIfNullOrEmpty(T t) {

        if (t == null) {
            return true;
        } else if (t instanceof String) {
            return ((String) t).isEmpty(); //return true if empty string
        } else if (t instanceof List) {
            return ((List) t).size() == 0; //return true if list is empty
        } else {
            return false; //return false for other types of values
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
