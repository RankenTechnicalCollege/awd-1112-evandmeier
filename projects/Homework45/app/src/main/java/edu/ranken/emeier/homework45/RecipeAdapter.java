package edu.ranken.emeier.homework45;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context mContext;
    private final List<Recipe> mRecipeList;
    private final LayoutInflater mInflater;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        mContext = context;
        mRecipeList = recipes;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        String recipeName = mRecipeList.get(position).getRecipeName();
        String recipeDescription = mRecipeList.get(position).getRecipeDescription();

        holder.mTitle.setText(recipeName);
        holder.mDescription.setText(recipeDescription);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mTitle, mDescription;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.recipe_name);
            mDescription = itemView.findViewById(R.id.recipe_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // open second screen
            Intent intent = new Intent(mContext, RecipeInfoActivity.class);

            int index = getLayoutPosition();
            Recipe recipe = mRecipeList.get(index);
            intent.putExtra("name", recipe.getRecipeName());
            intent.putExtra("desc", recipe.getRecipeDescription());
            intent.putExtra("steps", recipe.getRecipeSteps());
            intent.putExtra("ingredients", recipe.getRecipeIngredients());
            intent.putExtra("imageId", recipe.getImageId());

            mContext.startActivity(intent);
        }
    }

}