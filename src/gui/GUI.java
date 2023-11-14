package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.FileIO;
import main.Recipe;

public class GUI {
	private JFrame frame;
	private JTextField pinField;
	private final String PIN = "1234";

	//constructor for the main GUI, creates frames for the PIN Verification 
	public GUI() {
		frame = new JFrame("PIN Verification");
		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);

		JLabel pinLabel = new JLabel("Enter PIN:");
		pinLabel.setBounds(20, 20, 80, 25);
		frame.add(pinLabel);

		pinField = new JPasswordField();
		pinField.setBounds(100, 20, 150, 25);
		frame.add(pinField);

		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(100, 60, 80, 25);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String enteredPIN = pinField.getText();
				if (enteredPIN.equals(PIN)) {
					showSuccessMessage("PIN entered successfully.", false);
					viewRecipeMenu();
				} else {
					showErrorMessage("Error: Incorrect PIN. Please try again.");
					pinField.setText(""); // Clear the PIN field for another attempt
				}
			}
		});
		frame.add(submitButton);
		FileIO.setGUIReference(this);

		frame.setVisible(true);
	}

	//method for creating the recipe menu for viewing
	//each button is created separately and added with a listener 
	private void viewRecipeMenu() {
		frame.dispose(); // Close the PIN verification window

		JFrame recipeFrame = new JFrame("Recipe Library Options");
		recipeFrame.setSize(400, 200);
		recipeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		recipeFrame.setLayout(null);

		JLabel label = new JLabel("Please select an option for the Recipe Library:");
		label.setBounds(20, 20, 300, 25);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		recipeFrame.add(label);

		JButton addRecipeButton = new JButton("Add a Recipe");
		addRecipeButton.setBounds(20, 60, 150, 25);
		addRecipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddRecipeDialog(false, null);
			}
		});
		recipeFrame.add(addRecipeButton);

		JButton searchRecipeButton = new JButton("Search for a Recipe");
		searchRecipeButton.setBounds(20, 90, 150, 25);
		searchRecipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSearchRecipeDialog();
			}
		});
		recipeFrame.add(searchRecipeButton);

		JButton editRecipeButton = new JButton("Edit a Recipe");
		editRecipeButton.setBounds(200, 60, 150, 25);
		editRecipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEditRecipeDialog();
			}
		});
		recipeFrame.add(editRecipeButton);

		JButton deleteRecipeButton = new JButton("Delete a Recipe");
		deleteRecipeButton.setBounds(200, 90, 150, 25);
		deleteRecipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showDeleteRecipeDialog();
			}
		});
		recipeFrame.add(deleteRecipeButton);

		recipeFrame.setVisible(true);
	}

	//dialog box for creating or updating a recipe
	private void showAddRecipeDialog(boolean isEditing, String recipeNameToEdit) {
		JFrame addRecipeFrame = new JFrame(isEditing ? "Edit Recipe" : "Add a Recipe");
		addRecipeFrame.setSize(500, 400);
		addRecipeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addRecipeFrame.setLayout(null);

		JLabel nameLabel = new JLabel("Recipe Name:");
		nameLabel.setBounds(20, 20, 100, 25);
		addRecipeFrame.add(nameLabel);

		JTextField nameField = new JTextField();
		nameField.setBounds(120, 20, 350, 25);
		addRecipeFrame.add(nameField);

		JLabel ingredientsLabel = new JLabel("Ingredients separated by a comma:");
		ingredientsLabel.setBounds(20, 60, 300, 25);
		addRecipeFrame.add(ingredientsLabel);

		JTextArea ingredientsArea = new JTextArea();
		ingredientsArea.setBounds(20, 90, 450, 80);
		addRecipeFrame.add(ingredientsArea);

		JLabel instructionsLabel = new JLabel("Instructions:");
		instructionsLabel.setBounds(20, 190, 100, 25);
		addRecipeFrame.add(instructionsLabel);

		JTextArea instructionsArea = new JTextArea();
		instructionsArea.setBounds(20, 220, 450, 80);
		addRecipeFrame.add(instructionsArea);

		JButton saveButton = new JButton(isEditing ? "Save Changes" : "Save Recipe");
		saveButton.setBounds(200, 320, 120, 25);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipeName = nameField.getText();
				String ingredientsText = ingredientsArea.getText();
				String instructions = instructionsArea.getText();

				// Split ingredients by comma and create an ArrayList
				String[] ingredientsArray = ingredientsText.split(",");
				ArrayList<String> ingredientsList = new ArrayList<>();
				for (String ingredient : ingredientsArray) {
					ingredientsList.add(ingredient.trim());
				}

				// Create a Recipe object
				Recipe newRecipe = new Recipe(recipeName, ingredientsList, instructions);

				// Save the recipe to a .txt file using FileIO class
				FileIO.saveRecipeToFile(newRecipe);

				addRecipeFrame.dispose(); // Close the add/edit recipe window
				showSuccessMessage(isEditing ? "Recipe Edited Successfully" : "Recipe Added", false);
			}
		});
		addRecipeFrame.add(saveButton);

		// If editing, load data from the existing recipe file
		if (isEditing) {
			showEditRecipeFromFile(nameField, ingredientsArea, instructionsArea, recipeNameToEdit);
		}

		addRecipeFrame.setVisible(true);
	}

	//new GUI created for Searching for recipe
	private void showSearchRecipeDialog() {
		JFrame searchRecipeFrame = new JFrame("Search for a Recipe");
		searchRecipeFrame.setSize(400, 150);
		searchRecipeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		searchRecipeFrame.setLayout(null);

		JLabel nameLabel = new JLabel("Enter Recipe Name:");
		nameLabel.setBounds(20, 20, 150, 25);
		searchRecipeFrame.add(nameLabel);

		JTextField nameField = new JTextField();
		nameField.setBounds(180, 20, 150, 25);
		searchRecipeFrame.add(nameField);

		JButton searchButton = new JButton("Search");
		searchButton.setBounds(150, 60, 100, 25);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipeName = nameField.getText();
				String recipeContent = FileIO.searchRecipeFile(recipeName);

				if (recipeContent != null) {
					showSuccessMessage("Recipe Found:\n" + recipeContent, true);
				} else {
					showErrorMessage("No File Found for Recipe: " + recipeName);
				}

				searchRecipeFrame.dispose();
			}
		});
		searchRecipeFrame.add(searchButton);

		searchRecipeFrame.setVisible(true);
	}
	//new GUI window for editing a recipe
	private void showEditRecipeDialog() {
		JFrame editRecipeFrame = new JFrame("Edit a Recipe");
		editRecipeFrame.setSize(500, 400);
		editRecipeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		editRecipeFrame.setLayout(null);

		JLabel nameLabel = new JLabel("Enter Recipe Name:");
		nameLabel.setBounds(20, 20, 150, 25);
		editRecipeFrame.add(nameLabel);

		JTextField nameField = new JTextField();
		nameField.setBounds(180, 20, 150, 25);
		editRecipeFrame.add(nameField);

		JButton searchButton = new JButton("Search");
		searchButton.setBounds(150, 60, 100, 25);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipeName = nameField.getText();
				String recipeContent = FileIO.searchRecipeFile(recipeName);

				if (recipeContent != null) {
					// Show the add recipe dialog with editing enabled
					showAddRecipeDialog(true, recipeName);
				} else {
					showErrorMessage("No File Found for Recipe: " + recipeName);
				}

				editRecipeFrame.dispose();
			}
		});
		editRecipeFrame.add(searchButton);

		editRecipeFrame.setVisible(true);
	}

	//GUI cretaed for edit recipe content
	@SuppressWarnings("unused")
	private void showEditRecipeContentDialog(String content, String recipeName) {
		JFrame editRecipeContentFrame = new JFrame("Edit Recipe");
		editRecipeContentFrame.setSize(500, 400);
		editRecipeContentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		editRecipeContentFrame.setLayout(null);

		JTextArea contentArea = new JTextArea();
		contentArea.setBounds(20, 20, 450, 250);
		contentArea.setText(content);
		editRecipeContentFrame.add(contentArea);

		JButton saveButton = new JButton("Save Changes");
		saveButton.setBounds(200, 300, 150, 25);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String updatedContent = contentArea.getText();

				// Delete the original file
				FileIO.deleteRecipeFile(recipeName);

				// Save the updated content as a new file
				Recipe updatedRecipe = Recipe.parseRecipeFromString(updatedContent);
				FileIO.saveRecipeToFile(updatedRecipe);

				editRecipeContentFrame.dispose(); // Close the edit recipe content window
				showSuccessMessage("Recipe Edited Successfully", false);
			}
		});
		editRecipeContentFrame.add(saveButton);

		editRecipeContentFrame.setVisible(true);
	}

	//new GUI window for when a recipe is found during a search function
	public void showSuccessMessage(String message, boolean isRecipeFound) {
		if (isRecipeFound) {
			JTextArea textArea = new JTextArea(message);
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);

			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(500, 300)); // Set the preferred size

			JOptionPane.showMessageDialog(frame, scrollPane, "Recipe Found", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(frame, message);
		}
	}

	//creates new window for when you're showing an error message
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message);
	}

	//pulls data from file that exists to make sure the file populates the name, ingredients, and instructions fields
	private void showEditRecipeFromFile(JTextField nameField, JTextArea ingredientsArea, JTextArea instructionsArea,
			String recipeName) {
		String recipeContent = FileIO.searchRecipeFile(recipeName);
		if (recipeContent != null) {
			// Parse the existing content and pre-fill the fields
			Recipe existingRecipe = Recipe.parseRecipeFromString(recipeContent);
			nameField.setText(existingRecipe.getRecipeName());
			ingredientsArea.setText(String.join(", ", existingRecipe.getIngredients()));
			instructionsArea.setText(existingRecipe.getInstructions());
		}
	}

	//dialog box for deleting recipes includes information for finding recipe and deleting it
	private void showDeleteRecipeDialog() {
		JFrame deleteRecipeFrame = new JFrame("Delete a Recipe");
		deleteRecipeFrame.setSize(400, 150);
		deleteRecipeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		deleteRecipeFrame.setLayout(null);

		JLabel nameLabel = new JLabel("Enter Recipe Name:");
		nameLabel.setBounds(20, 20, 150, 25);
		deleteRecipeFrame.add(nameLabel);

		JTextField nameField = new JTextField();
		nameField.setBounds(180, 20, 150, 25);
		deleteRecipeFrame.add(nameField);

		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(150, 60, 100, 25);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipeName = nameField.getText();
				String recipeContent = FileIO.searchRecipeFile(recipeName);

				if (recipeContent != null) {
					int choice = JOptionPane.showConfirmDialog(deleteRecipeFrame,
							"Are you sure you want to delete the recipe?", "Confirm Deletion",
							JOptionPane.YES_NO_OPTION);
					if (choice == JOptionPane.YES_OPTION) {
						if (FileIO.deleteRecipeFile(recipeName)) {
							showSuccessMessage("Recipe Successfully Deleted", false);
						} else {
							showErrorMessage("Error: Recipe not deleted.");
						}
					}
				} else {
					showErrorMessage("No File Found for Recipe: " + recipeName);
				}

				deleteRecipeFrame.dispose();
			}
		});
		deleteRecipeFrame.add(deleteButton);

		deleteRecipeFrame.setVisible(true);
	}
}
