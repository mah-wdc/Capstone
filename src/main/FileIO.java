package main;

import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import gui.GUI;
import gui.GUI.SuccessMessageType;

public class FileIO {
	// Constant for the directory where recipe files are stored
	private static final String FILE_DIRECTORY = "recipe_files/";
	// Reference to the GUI for displaying messages
	private static GUI gui;

	/*********************************************************************/
	/*********************************************************************/
	/*********************************************************************/
	private static final String USER_FILE = "users.txt";
	private static HashMap<String, String> users = new HashMap<>();

	// Load user information from the user file
	static {
		loadUsers();
	}

	// Load users from the user file
	private static void loadUsers() {
		try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 2) {
					users.put(parts[0], parts[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * // Save user information to the user file private static void saveUsers() {
	 * try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
	 * for (String name : users.keySet()) { writer.write(name + "," +
	 * users.get(name) + "\n"); } } catch (IOException e) { e.printStackTrace(); } }
	 * 
	 * // Add a new user public static void addUser(String name, String password) {
	 * users.put(name, password); saveUsers(); }
	 */

	// Authenticate user
	public static boolean authenticateUser(String name, String password) {
		String storedPassword = users.get(name);
		return storedPassword != null && storedPassword.equals(password);
	}

	public static HashMap<String, String> getUsers() {
		return users;
	}

	/*********************************************************************/
	/*********************************************************************/
	/*********************************************************************/

	// Saves a recipe to a file
	public static void saveRecipeToFile(Recipe recipe, String currentUsername) {
		// Ensure the directory exists
		createDirectoryIfNotExists();

		// Create a file name based on the recipe name
		String recipeName = recipe.getRecipeName();
		String fileName = FILE_DIRECTORY + recipeName.replaceAll("\\s+", "_") + ".txt";

		// Check if a recipe with the same name already exists
		if (recipeExists(recipeName)) {
			gui.showErrorMessage("Error: A recipe with the name '" + recipeName
					+ "' already exists. Please choose a different name.");
			return;
		}

		// Create timestamp for the recipe
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String timestamp = dateFormat.format(new Date());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			// Write recipe details to the file
			writer.write("Recipe Name: " + recipe.getRecipeName() + "\n");
			writer.write("Ingredients: " + String.join(", ", recipe.getIngredients()) + "\n");
			writer.write("Instructions: " + recipe.getInstructions());
			writer.write("\n\nTimestamp: " + timestamp);
			// Write the current username to the file
			writer.write("\nLatest User to Edit: " + currentUsername + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Checks if a recipe with the given name already exists
	public static boolean recipeExists(String recipeName) {
		String fileName = FILE_DIRECTORY + recipeName.replaceAll("\\s+", "_") + ".txt";
		File file = new File(fileName);
		return file.exists();
	}

	// Searches for a file based on the recipe name
	public static String searchRecipeFile(String recipeName) {
		String fileName = FILE_DIRECTORY + recipeName.replaceAll("\\s+", "_") + ".txt";
		File file = new File(fileName);

		if (file.exists()) {
			// Read the contents of the file and return as a string
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
			return null; // Return null if the file does not exist
		}
	}

	// Sets the GUI reference for displaying messages
	public static void setGUIReference(GUI guiInstance) {
		gui = guiInstance;
	}

	// Deletes the file if it exists
	public static boolean deleteRecipeFile(String recipeName) {
		String fileName = FILE_DIRECTORY + recipeName.replaceAll("\\s+", "_") + ".txt";
		File file = new File(fileName);

		if (file.exists()) {
			// Attempt to delete the file and return true if successful
			if (file.delete()) {
				return true;
			}
		}
		return false; // Return false if the file does not exist or deletion fails
	}

	// Creates the directory if it does not exist
	private static void createDirectoryIfNotExists() {
		File directory = new File(FILE_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	// Gets the number of recipes in the directory
	public static int getNumberOfRecipes() {
		File directory = new File(FILE_DIRECTORY);
		if (directory.exists() && directory.isDirectory()) {
			// Return the number of files in the directory
			return directory.listFiles().length;
		} else {
			return 0; // Return 0 if the directory does not exist or is not a directory
		}
	}
}
