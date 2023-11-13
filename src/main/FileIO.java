package main;

import java.io.*;

public class FileIO {
    private static final String DIRECTORY = "recipes";

    public FileIO() {
        // Check if the directory exists, if not, create it
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void writeFile(Recipe recipe) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DIRECTORY + "/" + recipe.getRecipeName() + ".ser"))) {
            oos.writeObject(recipe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Recipe readFile(String recipeName) {
        Recipe recipe = null;
        File file = new File(DIRECTORY + "/" + recipeName + ".ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                recipe = (Recipe) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return recipe;
    }

    public void deleteFile(String recipeName) {
        File file = new File(DIRECTORY + "/" + recipeName + ".ser");
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println("Failed to delete the recipe: " + recipeName);
            }
        }
    }
}

