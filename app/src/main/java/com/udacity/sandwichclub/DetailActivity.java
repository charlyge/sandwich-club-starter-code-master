package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView originTextView;
    private TextView descriptionTv;
    private TextView alsoKnownAsTv;
    private TextView ingredientsTv;
   private Sandwich sandwich = null;
    private TextView alsoKnownAsLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTextView = findViewById(R.id.origin_tv);
         descriptionTv = findViewById(R.id.description_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
         ingredientsTv = findViewById(R.id.ingredients_tv);
       alsoKnownAsLabel = findViewById(R.id.also_known_label);
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

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        originTextView.setText(sandwich.getPlaceOfOrigin());

        List<String> ingredientsList = sandwich.getIngredients();
            for (int i = 0; i < ingredientsList.size(); i++) {

                ingredientsTv.append(" " + ingredientsList.get(i));
            }

            descriptionTv.setText(sandwich.getDescription());



        List<String> alsoKnownasList = sandwich.getAlsoKnownAs();
        if(alsoKnownasList.size()>0){
            for (int i = 0; i<alsoKnownasList.size();i++){

                alsoKnownAsTv.append( " " + alsoKnownasList.get(i));
            }
        }
        else {
            alsoKnownAsTv.setVisibility(View.GONE);
            alsoKnownAsLabel.setVisibility(View.GONE);

        }



    }
}
