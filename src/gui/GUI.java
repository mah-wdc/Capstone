package gui;

/*
*   File: GUI.java
*   Authors: Matthew Homan, Bethany Van Waes, and Michael Rach
*   Date: 12/12/2023
*   Purpose: This file creates and maintains the GUI for the application. 
*   Methods are created for different functionality and adding buttons, text
*   areas, and text fields.
*/


import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import main.FileIO;
import main.Recipe;
import gui.MessageType.SuccessMessageType;

public class GUI {
	private JFrame frame;
	private JFrame recipeFrame;
	private JLabel dateLabel;
	private JLabel timeLabel;
	private String selectedUser;

	// Constructor for the main GUI
	public GUI() {
		FileIO.setGUIReference(this);

		frame = new JFrame();

	}

	/******************************************************************************************************/
	/******************************************************************************************************/
	// Method to show user selection window
	public void showUserSelection() {
		//creates the window and sets the size and visibility
		JFrame userSelectionFrame = new JFrame("Select User");
		userSelectionFrame.setSize(400, 300);
		userSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userSelectionFrame.setLayout(null);

		// Create a clickable list of users with the users.txt file
		HashMap<String, String> users = FileIO.getUsers();
		String[] userList = users.keySet().toArray(new String[0]);
		JList<String> userJList = new JList<>(userList);
		JScrollPane scrollPane = new JScrollPane(userJList);
		scrollPane.setBounds(20, 20, 350, 200);
		userSelectionFrame.add(scrollPane);

		//JButton used for submitting the user selection
		JButton selectButton = new JButton("Select User");
		selectButton.setBounds(150, 240, 100, 25);
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//checks that a user is selected and prevents moving forward without that
				selectedUser = userJList.getSelectedValue();
				if (selectedUser != null) {
					showPasswordWindow();
					userSelectionFrame.dispose();
				} else {
					showErrorMessage("Please select a user.");
				}
			}
		});
		// Set the default button for the Enter key
		userSelectionFrame.getRootPane().setDefaultButton(selectButton);
		userSelectionFrame.add(selectButton);

		userSelectionFrame.setLocationRelativeTo(null);
		userSelectionFrame.setVisible(true);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/
	// Method to show the password window
	private void showPasswordWindow() {
		//creates window information and layout
		JFrame passwordFrame = new JFrame("Enter Password");
		passwordFrame.setSize(350, 150);
		passwordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		passwordFrame.setLayout(null);

		//label for prompting user to enter informaiton
		JLabel passwordLabel = new JLabel("Enter Password for " + selectedUser + ":");
		passwordLabel.setBounds(50, 20, 300, 25);
		passwordFrame.add(passwordLabel);

		//password field for the user to enter information
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(50, 50, 250, 25);
		passwordFrame.add(passwordField);

		//button to submit information from the user password
		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(100, 90, 100, 25);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//information entered is used to determine if the password is entered correctly
				String enteredPassword = new String(passwordField.getPassword());
				//FileIO checks to make sure the password is correct
				if (FileIO.authenticateUser(selectedUser, enteredPassword)) {
					showSuccessMessage("Password entered successfully.", false, SuccessMessageType.GENERIC);
					viewRecipeMenu();
					passwordFrame.dispose();
				} else {
					showErrorMessage("Error: Incorrect password. Please try again.");
					// Allow the user to enter the password again
					passwordField.setText(""); // Clear the password field for another attempt
					passwordField.requestFocus(); // Set focus back to the password field

				}
			}
		});
		// Set the default button for the Enter key
		passwordFrame.getRootPane().setDefaultButton(submitButton);
		passwordFrame.add(submitButton);

		passwordFrame.setLocationRelativeTo(null);
		passwordFrame.setVisible(true);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// Method for creating the recipe menu for viewing
	// Each button is created separately and added with a listener
	private void viewRecipeMenu() {
		frame.dispose(); // Close the PIN verification window

		disposeCurrentFrame(); // Dispose of the current frame

		//frame created with size and layout
		recipeFrame = new JFrame("Recipe Library Options");
		recipeFrame.setSize(400, 250);
		recipeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		recipeFrame.setLayout(null);

		//creates welcome label and adds to layout
		JLabel welcomeLabel = new JLabel("Welcome to the Recipe Library.");
		welcomeLabel.setBounds(20, 10, 330, 25);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recipeFrame.add(welcomeLabel);

		//creates counter and count label 
		int count = FileIO.getNumberOfRecipes();
		JLabel recipeCountLabel;

		//count label is created based on the number of recipes there are
		if (count == 0) {
			recipeCountLabel = new JLabel("The library is empty.");
		} else if (count == 1) {
			recipeCountLabel = new JLabel("There is 1 recipe in the library.");
		} else {
			recipeCountLabel = new JLabel("There are " + count + " recipes in the library.");
		}

		//adds label to the frame including its position
		recipeCountLabel.setBounds(20, 25, 330, 25);
		recipeCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recipeFrame.add(recipeCountLabel);

		//adds label for options, including placement
		JLabel label = new JLabel("Please select an option:");
		label.setBounds(20, 40, 330, 25);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		recipeFrame.add(label);

		//creates button and functionality for adding a recipe
		JButton addRecipeButton = new JButton("Add a Recipe");
		addRecipeButton.setBounds(20, 70, 150, 25);
		addRecipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//calls method for adding recipe
				showAddRecipeDialog(false, null, selectedUser);
			}
		});
		recipeFrame.add(addRecipeButton);

		//creates button and functionality for searching for recipe
		JButton searchRecipeButton = new JButton("Search for a Recipe");
		searchRecipeButton.setBounds(20, 100, 150, 25);
		searchRecipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//calls method to search for recipe
				showSearchRecipeDialog();
			}
		});
		recipeFrame.add(searchRecipeButton);

		//creates button and functionality for editing the recipe
		JButton editRecipeButton = new JButton("Edit a Recipe");
		editRecipeButton.setBounds(200, 70, 150, 25);
		editRecipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//calls function for editing recipe
				showEditRecipeDialog();
			}
		});
		recipeFrame.add(editRecipeButton);

		//creates button and functionality for deleting a recipe
		JButton deleteRecipeButton = new JButton("Delete a Recipe");
		deleteRecipeButton.setBounds(200, 100, 150, 25);
		deleteRecipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//calls function for creating recipe
				showDeleteRecipeDialog();
			}
		});
		recipeFrame.add(deleteRecipeButton);

		// Create and add the date label
		dateLabel = new JLabel();
		dateLabel.setBounds(20, 130, 330, 25);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recipeFrame.add(dateLabel);

		// Create and add the time label
		timeLabel = new JLabel();
		timeLabel.setBounds(20, 160, 330, 25);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recipeFrame.add(timeLabel);

		// Update time label using Timer
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//calls time function
				updateTimeLabel();
			}
		});
		timer.start();

		// Center the JFrame on the screen
		recipeFrame.setLocationRelativeTo(null);
		recipeFrame.setVisible(true);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// Update time label with the current time
	private void updateTimeLabel() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

		String currentDate = dateFormat.format(new Date());
		String currentTime = timeFormat.format(new Date());

		dateLabel.setText("Current Date: " + currentDate);
		timeLabel.setText("Current Time: " + currentTime);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// Dialog box for creating or updating a recipe
	private void showAddRecipeDialog(boolean isEditing, String recipeNameToEdit, String selectedUser) {
		disposeCurrentFrame(); // Dispose of the current frame

		//create frame and layout
		JFrame addRecipeFrame = new JFrame(isEditing ? "Edit Recipe" : "Add a Recipe");
		addRecipeFrame.setSize(500, 400);
		addRecipeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addRecipeFrame.setLayout(null);

		//create label for recipe name textfield
		JLabel nameLabel = new JLabel("Recipe Name:");
		nameLabel.setBounds(20, 20, 100, 25);
		addRecipeFrame.add(nameLabel);

		//create field for recipe name to be entered
		JTextField nameField = new JTextField();
		nameField.setBounds(120, 20, 350, 25);
		addRecipeFrame.add(nameField);

		//create and place label for the ingredients
		JLabel ingredientsLabel = new JLabel("Ingredients separated by a comma:");
		ingredientsLabel.setBounds(20, 60, 300, 25);
		addRecipeFrame.add(ingredientsLabel);

		//create and place TextArea for ingredients, including creating ability for text wrapping
		JTextArea ingredientsArea = new JTextArea();
		ingredientsArea.setBounds(20, 90, 450, 80);
		ingredientsArea.setLineWrap(true);
		ingredientsArea.setWrapStyleWord(true);
		addRecipeFrame.add(ingredientsArea);

		//create and place Instructions label
		JLabel instructionsLabel = new JLabel("Instructions:");
		instructionsLabel.setBounds(20, 190, 100, 25);
		addRecipeFrame.add(instructionsLabel);

		//create and place TextArea for Instructions, including text wrapping
		JTextArea instructionsArea = new JTextArea();
		instructionsArea.setBounds(20, 220, 450, 80);
		instructionsArea.setLineWrap(true);
		instructionsArea.setWrapStyleWord(true);
		addRecipeFrame.add(instructionsArea);

		//creates and places button as well as adds functionality for saving the recipe
		JButton saveButton = new JButton(isEditing ? "Save Changes" : "Save Recipe");
		saveButton.setBounds(200, 320, 120, 25);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipeName = nameField.getText();
				String ingredientsText = ingredientsArea.getText();
				String instructions = instructionsArea.getText();

				//checks recipe to see if it is blank or contains only spaces
				if (recipeName.trim().isEmpty()) {
	               			showErrorMessage("Error: Recipe name cannot be blank or contain only spaces.");
	                		return;
	           		}

				// Split ingredients by comma and create an ArrayList
				String[] ingredientsArray = ingredientsText.split(",");
				ArrayList<String> ingredientsList = new ArrayList<>();
				for (String ingredient : ingredientsArray) {
					ingredientsList.add(ingredient.trim());
				}

				// Check if a recipe with the same name already exists
				if (isEditing || !FileIO.recipeExists(recipeName)) {
					// Delete the original file if editing
					if (isEditing) {
						FileIO.deleteRecipeFile(recipeNameToEdit);
					}

					// Create a Recipe object
					Recipe newRecipe = new Recipe(recipeName, ingredientsList, instructions);

					// Save the recipe to a .txt file using FileIO class
					FileIO.saveRecipeToFile(newRecipe, selectedUser);

					addRecipeFrame.dispose(); // Close the add/edit recipe window
					showSuccessMessage(isEditing ? "Recipe Edited Successfully" : "Recipe Added", false,
							SuccessMessageType.RECIPE_ADDED);
				} else {
					showErrorMessage("Error: A recipe with the name '" + recipeName
							+ "' already exists. Please choose a different name.");
				}

			}
		});
		addRecipeFrame.add(saveButton);

		// If editing, load data from the existing recipe file
		if (isEditing) {
			showEditRecipeFromFile(nameField, ingredientsArea, instructionsArea, recipeNameToEdit);
		}

		addRecipeFrame.setLocationRelativeTo(null);
		addRecipeFrame.setVisible(true);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// New GUI created for Searching for recipe
	private void showSearchRecipeDialog() {
		disposeCurrentFrame(); // Dispose of the current frame

		//creates frame and makes it visible
		JFrame searchRecipeFrame = new JFrame("Search for a Recipe");
		searchRecipeFrame.setSize(400, 150);
		searchRecipeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		searchRecipeFrame.setLayout(null);

		//creates label for name
		JLabel nameLabel = new JLabel("Enter Recipe Name:");
		nameLabel.setBounds(20, 20, 150, 25);
		searchRecipeFrame.add(nameLabel);

		//creates field for name 
		JTextField nameField = new JTextField();
		nameField.setBounds(140, 20, 250, 25);
		searchRecipeFrame.add(nameField);

		//creates button for searching according to text field
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(150, 60, 100, 25);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipeName = nameField.getText();
				String recipeContent = FileIO.searchRecipeFile(recipeName);
				//tests to see if recipe content can be found
				if (recipeContent != null) {
					showSuccessMessage("Recipe Found:\n" + recipeContent, true, SuccessMessageType.GENERIC);
				} else {
					showErrorMessage("No File Found for Recipe: " + recipeName);
				}

				searchRecipeFrame.dispose();
				viewRecipeMenu();
			}
		});
		searchRecipeFrame.add(searchButton);

		searchRecipeFrame.setLocationRelativeTo(null);
		searchRecipeFrame.setVisible(true);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// New GUI window for editing a recipe
	private void showEditRecipeDialog() {
		disposeCurrentFrame(); // Dispose of the current frame

		//creates frame for editing recipe
		JFrame editRecipeFrame = new JFrame("Edit a Recipe");
		editRecipeFrame.setSize(500, 400);
		editRecipeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		editRecipeFrame.setLayout(null);

		//creates and places label for recipe name
		JLabel nameLabel = new JLabel("Enter Recipe Name:");
		nameLabel.setBounds(20, 20, 150, 25);
		editRecipeFrame.add(nameLabel);

		//creates and places field for recipe name
		JTextField nameField = new JTextField();
		nameField.setBounds(140, 20, 250, 25);
		editRecipeFrame.add(nameField);

		//creates and places button for searching for recipe
		//and adds on the functionality
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(150, 60, 100, 25);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipeName = nameField.getText();
				String recipeContent = FileIO.searchRecipeFile(recipeName);
				//checks if recipe exists and provides results to user
				if (recipeContent != null) {
					// Show the add recipe dialog with editing enabled
					showAddRecipeDialog(true, recipeName, selectedUser);
				} else {
					showErrorMessage("No File Found for Recipe: " + recipeName);
					viewRecipeMenu();
				}

				editRecipeFrame.dispose();
			}
		});
		editRecipeFrame.add(searchButton);

		editRecipeFrame.setLocationRelativeTo(null);
		editRecipeFrame.setVisible(true);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// GUI created for edit recipe content
	private void showEditRecipeContentDialog(String content, String recipeName) {
		disposeCurrentFrame(); // Dispose of the current frame

		//creates and sizes frame for editing recipe
		JFrame editRecipeContentFrame = new JFrame("Edit Recipe");
		editRecipeContentFrame.setSize(500, 400);
		editRecipeContentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		editRecipeContentFrame.setLayout(null);

		//adds content area for editing the recipe
		JTextArea contentArea = new JTextArea();
		contentArea.setBounds(20, 20, 450, 250);
		contentArea.setText(content);
		editRecipeContentFrame.add(contentArea);

		//adds save button and functionality
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
				FileIO.saveRecipeToFile(updatedRecipe, selectedUser);

				editRecipeContentFrame.dispose(); // Close the edit recipe content window
				showSuccessMessage("Recipe Edited Successfully", false, SuccessMessageType.GENERIC);
			}
		});
		editRecipeContentFrame.add(saveButton);

		editRecipeContentFrame.setLocationRelativeTo(null);
		editRecipeContentFrame.setVisible(true);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// Pulls data from file that exists to make sure the file populates the name,
	// ingredients, and instructions fields
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

	/******************************************************************************************************/
	/******************************************************************************************************/

	// Dialog box for deleting recipes includes information for finding recipe and
	// deleting it
	private void showDeleteRecipeDialog() {

		//creates window for deleting recipe
		JFrame deleteRecipeFrame = new JFrame("Delete a Recipe");
		deleteRecipeFrame.setSize(400, 150);
		deleteRecipeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		deleteRecipeFrame.setLayout(null);

		//creates and places label for recipe name
		JLabel nameLabel = new JLabel("Enter Recipe Name:");
		nameLabel.setBounds(20, 20, 150, 25);
		deleteRecipeFrame.add(nameLabel);

		//creates and places text field for recipe name to be entered by the user
		JTextField nameField = new JTextField();
		nameField.setBounds(140, 20, 250, 25);
		deleteRecipeFrame.add(nameField);

		//creates and places button for deleting recipe
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(150, 60, 100, 25);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipeName = nameField.getText();
				String recipeContent = FileIO.searchRecipeFile(recipeName);

				//checks to make sure the recipe exists
				if (recipeContent != null) {
					//requests user confirm deletion
					int choice = JOptionPane.showConfirmDialog(deleteRecipeFrame,
							"Are you sure you want to delete the recipe?", "Confirm Deletion",
							JOptionPane.YES_NO_OPTION);
					if (choice == JOptionPane.YES_OPTION) {
						//deletes recipe and creates window to show when the recipe is deleted 
						if (FileIO.deleteRecipeFile(recipeName)) {
							showSuccessMessage("Recipe Successfully Deleted", false, SuccessMessageType.RECIPE_DELETED);
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

		deleteRecipeFrame.setLocationRelativeTo(null);
		deleteRecipeFrame.setVisible(true);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// New GUI window for when a recipe is found during a search function
	public void showSuccessMessage(String message, boolean isRecipeFound, SuccessMessageType messageType) {
		//checks if recipe is found
		if (isRecipeFound) {
			//creates text area for without the ability to edit 
			JTextArea textArea = new JTextArea(message);
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);

			//create ability to scroll for larger windows of information
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(500, 300)); // Set the preferred size

			JOptionPane.showMessageDialog(frame, scrollPane, "Recipe Found", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(frame, message);
		}

		// Check the type of success message and relaunch the Recipe menu accordingly
		/*
		 * if (messageType == SuccessMessageType.RECIPE_ADDED || messageType ==
		 * SuccessMessageType.RECIPE_DELETED) { viewRecipeMenu(); }
		 */
		viewRecipeMenu();
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// Creates new window for when you're showing an error message
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message);
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// Helper method to update the recipe count label
	private void updateRecipeCountLabel() {
		int count = FileIO.getNumberOfRecipes();
		JLabel recipeCountLabel;

		//checks count to update label with number of recipes in library
		if (count == 0) {
			recipeCountLabel = new JLabel("The library is empty.");
		} else if (count == 1) {
			recipeCountLabel = new JLabel("There is 1 recipe in the library.");
		} else {
			recipeCountLabel = new JLabel("There are " + count + " recipes in the library.");
		}

		recipeCountLabel.setBounds(20, 25, 330, 25);
		recipeCountLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Find and replace the existing label in the JFrame
		Container container = recipeFrame.getContentPane();
		for (Component component : container.getComponents()) {
			if (component instanceof JLabel && ((JLabel) component).getText().contains("recipes in the library")) {
				container.remove(component);
				container.add(recipeCountLabel);
				break;
			}
		}

		// Repaint the JFrame to reflect the changes
		recipeFrame.revalidate();
		recipeFrame.repaint();
	}

	/******************************************************************************************************/
	/******************************************************************************************************/

	// New method for Phase 3 to ensure windows don't stack
	private void disposeCurrentFrame() {
		//checks frame and then disposes of it
		if (frame != null) {
			frame.dispose();
		}
		if (recipeFrame != null) {
			recipeFrame.dispose();
		}
	}

}
