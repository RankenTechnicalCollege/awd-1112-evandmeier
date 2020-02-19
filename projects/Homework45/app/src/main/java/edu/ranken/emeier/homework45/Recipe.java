package edu.ranken.emeier.homework45;

public class Recipe {

    private String mRecipeName;
    private String mRecipeDescription;
    private String mRecipeSteps;
    private String mRecipeIngredients;
    private int mImageId;

    // constructor with all custom fields
    public Recipe(
            String recipeName,
            String recipeDescription,
            String recipeSteps,
            String recipeIngredients,
            int imageId) {
        mRecipeName = recipeName;
        mRecipeDescription = recipeDescription;
        mRecipeSteps = recipeSteps;
        mRecipeIngredients = recipeIngredients;
        mImageId = imageId;
    }

    // constructor with an empty image
    public Recipe(
            String recipeName,
            String recipeDescription,
            String recipeSteps,
            String recipeIngredients) {
        this(recipeName, recipeDescription, recipeSteps, recipeIngredients, R.drawable.no_image);
        /*
        mRecipeName = recipeName;
        mRecipeDescription = recipeDescription;
        mRecipeSteps = recipeSteps;
        mRecipeIngredients = recipeIngredients;
        mImageId = R.drawable.no_image;
        */
    }

    // constructor purely for test data
    public Recipe() {
        mRecipeName = "Test Recipe";
        mRecipeDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor" +
                " incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud" +
                " exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure" +
                " dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";
        mRecipeIngredients = "Ingredients\n" +
                "1/4 cup random ingredient\n" +
                "2 tablespoons random ingredient\n" +
                "1/4 teaspoon random ingredient\n" +
                "2 tablespoons random ingredient\n" +
                "4 slices random ingredient\n" +
                "1 random ingredient";
        mRecipeSteps = "Procedure\n" +
                "1: Do first random thing\n" +
                "2: Do second random thing\n" +
                "3: Do third random thing\n" +
                "4: Do fourth random thing\n" +
                "5: Do fifth random thing\n" +
                "6: Do sixth random thing";
        mImageId = R.drawable.no_image;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public void setRecipeName(String name) {
        mRecipeName = name;
    }

    public String getRecipeDescription() {
        return mRecipeDescription;
    }

    public void setRecipeDescription(String desc) {
        mRecipeName = desc;
    }

    public String getRecipeSteps() {
        return mRecipeSteps;
    }

    public void setRecipeSteps(String steps) {
        mRecipeSteps = steps;
    }

    public String getRecipeIngredients() { return mRecipeIngredients; }

    public int getImageId() { return mImageId; }
}
