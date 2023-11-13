package main;

import gui.GUI;

public class RecipeLibrary {
    private static final String PIN = "1234"; // Simple PIN for validation

   
	public boolean validatePin(String inputPin) {
        return PIN.equals(inputPin);
    }

    public void addRecipe(Recipe recipe) {
        // Save the recipe using FileIO
        new FileIO().writeFile(recipe);
    }

    public Recipe searchForRecipe(String recipeName) {
        // Search for the recipe using FileIO
        return new FileIO().readFile(recipeName);
    }

    public void editRecipe(String recipeName, Recipe newRecipe) {
        // Edit the recipe by deleting the old one and saving the new one
        new FileIO().deleteFile(recipeName);
        new FileIO().writeFile(newRecipe);
    }

    public void deleteRecipe(String recipeName) {
        // Delete the recipe using FileIO
        new FileIO().deleteFile(recipeName);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.displayLogin();
    }
}
