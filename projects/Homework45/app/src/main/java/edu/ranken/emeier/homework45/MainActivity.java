package edu.ranken.emeier.homework45;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Recipe> mRecipeList;
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get widgets;
        mRecyclerView = findViewById(R.id.recyclerview);

        mRecipeList = populateRecipeList();

        mAdapter = new RecipeAdapter(this, mRecipeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_col_count)));
    }

    public ArrayList<Recipe> populateRecipeList() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        // populate list of recipes
        // uses constructor #1
        recipes.add(new Recipe(
                getString(R.string.recipe_1_name),
                getString(R.string.recipe_1_description),
                getString(R.string.recipe_1_steps),
                getString(R.string.recipe_1_ingredients),
                R.drawable.recipe1));
        recipes.add(new Recipe(
                getString(R.string.recipe_2_name),
                getString(R.string.recipe_2_description),
                getString(R.string.recipe_2_steps),
                getString(R.string.recipe_2_ingredients),
                R.drawable.recipe2));
        recipes.add(new Recipe(
                getString(R.string.recipe_3_name),
                getString(R.string.recipe_3_description),
                getString(R.string.recipe_3_steps),
                getString(R.string.recipe_3_ingredients),
                R.drawable.recipe3));
        recipes.add(new Recipe(
                getString(R.string.recipe_4_name),
                getString(R.string.recipe_4_description),
                getString(R.string.recipe_4_steps),
                getString(R.string.recipe_4_ingredients),
                R.drawable.recipe4));

        // populate list of recipe
        // uses constructor #2
        recipes.add(new Recipe(
                getString(R.string.recipe_5_name),
                getString(R.string.recipe_5_description),
                getString(R.string.recipe_5_steps),
                getString(R.string.recipe_5_ingredients)));

        // populate list of recipe
        // uses constructor #3
        recipes.add((new Recipe()));
        recipes.add((new Recipe()));
        recipes.add((new Recipe()));
        recipes.add((new Recipe()));
        recipes.add((new Recipe()));
        recipes.add((new Recipe()));
        recipes.add((new Recipe()));
        recipes.add((new Recipe()));
        return recipes;
    }
}
