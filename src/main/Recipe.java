package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;
    private String recipeName;
    private ArrayList<String> ingredients;
    private String instructions;

    // Constructor
    public Recipe(String recipeName, ArrayList<String> ingredients, String instructions) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Getters
    public String getRecipeName() {
        return recipeName;
    }

    public ArrayList<String> getIngredients() {
        return new ArrayList<>(ingredients); // Defensive copy for mutable list
    }

    public String getInstructions() {
        return instructions;
    }

    // Setters
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = new ArrayList<>(ingredients); // Defensive copy for mutable list
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    // toString method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe Name: ").append(recipeName).append("\n");
        sb.append("Ingredients:\n");
        for (String ingredient : ingredients) {
            sb.append("- ").append(ingredient).append("\n");
        }
        sb.append("Instructions: ").append(instructions).append("\n");
        return sb.toString();
    }

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(getRecipeName(), recipe.getRecipeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRecipeName());
    }
}
