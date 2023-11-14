package main;

import java.io.*;
import java.util.ArrayList;

import gui.GUI;

public class FileIO {
	private static final String FILE_DIRECTORY = "recipe_files/";
	private static GUI gui;
	//creates directory if necessary, and then creates the file to add recipes to, as necessary
	public static void saveRecipeToFile(Recipe recipe) {
		createDirectoryIfNotExists();

		String fileName = FILE_DIRECTORY + recipe.getRecipeName().replaceAll("\\s+", "_") + ".txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write("Recipe Name: " + recipe.getRecipeName() + "\n");
			writer.write("Ingredients: " + String.join(", ", recipe.getIngredients()) + "\n");
			writer.write("Instructions: " + recipe.getInstructions());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//searches for file based on the String for the Recipe name given
	public static String searchRecipeFile(String recipeName) {
		String fileName = FILE_DIRECTORY + recipeName.replaceAll("\\s+", "_") + ".txt";
		File file = new File(fileName);

		if (file.exists()) {
			StringBuilder result = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					result.append(line).append("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result.toString();
		} else {
			return null;
		}
	}
	//setter method sets the GUI reference
	public static void setGUIReference(GUI guiInstance) {
		gui = guiInstance;
	}
	//deletes the file if it exists
	public static boolean deleteRecipeFile(String recipeName) {
		String fileName = FILE_DIRECTORY + recipeName.replaceAll("\\s+", "_") + ".txt";
		File file = new File(fileName);

		if (file.exists()) {
			if (file.delete()) {
				return true;
			}
		}
		return false;
	}
	//if no file directory currently exists, creates on
	private static void createDirectoryIfNotExists() {
		File directory = new File(FILE_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}
}
