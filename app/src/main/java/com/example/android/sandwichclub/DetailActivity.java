package com.example.android.sandwichclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.sandwichclub.Model.Sandwich;
import com.example.android.sandwichclub.Utils.JsonUtils;
import com.squareup.picasso.Picasso;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView image;
    private TextView origin_label, origin, alsoKnown_label, alsoKnown, ingredients_label, ingredients, description_label, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        image = findViewById(R.id.image_iv);
        origin_label = findViewById(R.id.origin_label);
        origin = findViewById(R.id.origin_tv);
        alsoKnown_label = findViewById(R.id.also_known_label);
        alsoKnown = findViewById(R.id.also_known_tv);
        ingredients_label = findViewById(R.id.ingredients_label);
        ingredients = findViewById(R.id.ingredients_tv);
        description_label = findViewById(R.id.description_label);
        description = findViewById(R.id.description_tv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }
        setTitle(sandwich.getMainName());
        populateUI(sandwich);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this).load(sandwich.getImage()).into(image);
        String originText = sandwich.getPlaceOfOrigin();
        if (originText.isEmpty()) {
            origin_label.setVisibility(View.GONE);
            origin.setVisibility(View.GONE);
        } else
            origin.setText(originText);

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.isEmpty()) {
            alsoKnown_label.setVisibility(View.GONE);
            alsoKnown.setVisibility(View.GONE);
        } else {
            StringBuilder otherNames = new StringBuilder();
            for (String otherName : alsoKnownAsList) {
                otherNames.append(otherName).append(", ");
            }
            otherNames.setLength(otherNames.length() - 2);
            alsoKnown.setText(otherNames);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.isEmpty()) {
            ingredients_label.setVisibility(View.GONE);
            ingredients.setVisibility(View.GONE);
        } else {
            StringBuilder ingredientsText = new StringBuilder();
            for (String ingredient : ingredientsList) {
                ingredientsText.append(ingredient).append(", ");
            }
            ingredientsText.setLength(ingredientsText.length() - 2);
            ingredients.setText(ingredientsText);
        }

        String descriptionText = sandwich.getDescription();
        if (descriptionText.isEmpty()) {
            description_label.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
        } else {
            description.setText(descriptionText);
        }
    }
}
