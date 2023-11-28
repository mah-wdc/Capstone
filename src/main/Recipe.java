package main;

import java.util.ArrayList;

public class Recipe {
	private String recipeName;
	private ArrayList<String> ingredients;
	private String instructions;

	// Constructor for the class, setting the name, ingredients, and instructions
	public Recipe(String recipeName, ArrayList<String> ingredients, String instructions) {
		this.recipeName = recipeName;
		this.ingredients = new ArrayList<>(ingredients);
		this.instructions = instructions;
	}

	// Getter for recipe name
	public String getRecipeName() {
		return recipeName;
	}

	// Setter for recipe name
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	// Getter for ingredients list
	public ArrayList<String> getIngredients() {
		return new ArrayList<>(ingredients);
	}

	// Setter for ingredients list
	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = new ArrayList<>(ingredients);
	}

	// Getter for instructions string
	public String getInstructions() {
		return instructions;
	}

	// Setter for instructions string
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	// toString overridden to accommodate the creation of the recipe name,
	// ingredients, and instructions
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Recipe Name: ").append(recipeName).append("\n");
		stringBuilder.append("Ingredients: ").append(String.join(", ", ingredients)).append("\n");
		stringBuilder.append("Instructions: ").append(instructions);
		return stringBuilder.toString();
	}

	// Parses the string from the file, separates the three components of the string
	// into separate components
	public static Recipe parseRecipeFromString(String recipeString) {
		// Split the input string by newline character to get different fields
		String[] fields = recipeString.split("\n");

		// Extract values from fields
		String recipeName = fields[0].substring(fields[0].indexOf(":") + 1).trim();
		String ingredientsString = fields[1].substring(fields[1].indexOf(":") + 1).trim();
		String instructions = fields[2].substring(fields[2].indexOf(":") + 1).trim();

		// Split ingredients string into a list
		String[] ingredientsArray = ingredientsString.split(",");
		ArrayList<String> ingredientsList = new ArrayList<>();
		for (String ingredient : ingredientsArray) {
			ingredientsList.add(ingredient.trim());
		}

		// Create and return a new Recipe object
		return new Recipe(recipeName, ingredientsList, instructions);
	}
}
