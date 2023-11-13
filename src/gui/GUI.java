package gui;

import java.util.ArrayList;
import java.util.Scanner;
import main.*;

public class GUI {
    private RecipeLibrary library;
    private Scanner scanner;

    public GUI() {
        library = new RecipeLibrary();
        scanner = new Scanner(System.in);
    }

    public void displayLogin() {
        System.out.print("Enter PIN to access the library: ");
        String inputPin = scanner.nextLine();
        if (library.validatePin(inputPin)) {
            displayMainMenu();
        } else {
            System.out.println("Invalid PIN. Access denied.");
        }
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("\nWelcome to the Recipe Library");
            System.out.println("1. Add Recipe");
            System.out.println("2. Search Recipe");
            System.out.println("3. Edit Recipe");
            System.out.println("4. Delete Recipe");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    displayAddRecipeForm();
                    break;
                case 2:
                    displaySearchRecipeForm();
                    break;
                case 3:
                    displayEditRecipeForm();
                    break;
                case 4:
                    displayDeleteRecipeForm();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    public void displayAddRecipeForm() {
        System.out.print("Enter Recipe Name: ");
        String name = scanner.nextLine();

        ArrayList<String> ingredients = new ArrayList<>();
        System.out.println("Enter ingredients (type 'end' to finish):");
        String ingredient;
        while (!(ingredient = scanner.nextLine()).equalsIgnoreCase("end")) {
            ingredients.add(ingredient);
        }

        System.out.println("Enter Instructions:");
        String instructions = scanner.nextLine();

        Recipe recipe = new Recipe(name, ingredients, instructions);
        library.addRecipe(recipe);
        System.out.println("Recipe added successfully!");
    }

    public void displaySearchRecipeForm() {
        System.out.print("Enter Recipe Name to search: ");
        String name = scanner.nextLine();
        Recipe recipe = library.searchForRecipe(name);
        if (recipe != null) {
            displayRecipe(recipe);
        } else {
            System.out.println("Recipe not found.");
        }
    }

    public void displayEditRecipeForm() {
        System.out.print("Enter Recipe Name to edit: ");
        String name = scanner.nextLine();

        // Assuming that to edit a recipe, we first need to remove it and then add a new one
        Recipe existingRecipe = library.searchForRecipe(name);
        if (existingRecipe != null) {
            System.out.println("Editing Recipe: " + name);
            displayAddRecipeForm(); // Reuse the add recipe form for editing purposes
            library.deleteRecipe(name); // Delete after adding the new one to avoid overwriting before confirming
        } else {
            System.out.println("Recipe not found.");
        }
    }

    public void displayDeleteRecipeForm() {
        System.out.print("Enter Recipe Name to delete: ");
        String name = scanner.nextLine();
        library.deleteRecipe(name);
        System.out.println("Recipe deleted successfully.");
    }

    public void displayRecipe(Recipe recipe) {
        System.out.println("\nRecipe Name: " + recipe.getRecipeName());
        System.out.println("Ingredients:");
        for (String ingredient : recipe.getIngredients()) {
            System.out.println("- " + ingredient);
        }
        System.out.println("Instructions: " + recipe.getInstructions());
    }

    public void displayError(String message) {
        System.out.println("Error: " + message);
    }

    // Main method to run the GUI
    public static void main(String[] args) {
        new GUI().displayLogin();
    }
}
