package com.example.recipecorner;

public class RecipeData
{
    String rName, rIngredients, rInstructions;
    boolean rFavourite = false;

    public RecipeData() {}

    public RecipeData(String name, String ingredients, String instructions)
    {
        setrName(name);
        setrIngredients(ingredients);
        setrInstructions(instructions);
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrIngredients() {
        return rIngredients;
    }

    public void setrIngredients(String rIngredients) {
        this.rIngredients = rIngredients;
    }

    public String getrInstructions() {
        return rInstructions;
    }

    public void setrInstructions(String rInstructions) {
        this.rInstructions = rInstructions;
    }

    public boolean isrFavourite() {
        return rFavourite;
    }

    public void setrFavourite(boolean rFavourite) {
        this.rFavourite = rFavourite;
    }

}
