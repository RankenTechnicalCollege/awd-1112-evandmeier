package edu.ranken.emeier.homework45;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeInfoActivity extends AppCompatActivity {

    private TextView mRecipeDesc, mRecipeSteps, mRecipeIngredients;
    private ImageView mRecipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        mRecipeDesc = findViewById(R.id.recipe_description);
        mRecipeSteps = findViewById(R.id.recipe_steps);
        mRecipeIngredients = findViewById(R.id.recipe_ingredients);
        mRecipeImage = findViewById(R.id.recipe_image);

        Intent intent = getIntent();
        mRecipeDesc.setText(intent.getStringExtra("desc"));
        mRecipeSteps.setText(intent.getStringExtra("steps"));
        mRecipeIngredients.setText(intent.getStringExtra("ingredients"));
        mRecipeImage.setImageResource(intent.getIntExtra("imageId", 0));

        setTitle(intent.getStringExtra("name"));
    }
}
