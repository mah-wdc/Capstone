
package main;

import javax.swing.SwingUtilities;

import gui.GUI;

/**
 * The RecipeLibrary class serves as the entry point for the recipe library
 * application.
 */
public class RecipeLibrary {

	/**
	 * The main method, which is the starting point of the application. It uses
	 * SwingUtilities.invokeLater to ensure that the GUI is created on the Event
	 * Dispatch Thread.
	 * 
	 * @param args Command-line arguments (not used in this application)
	 */
	public static void main(String[] args) {

		// Use SwingUtilities.invokeLater to run GUI initialization on the Event
		// Dispatch Thread
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI gui = new GUI();
				FileIO.setGUIReference(gui);
				gui.showUserSelection();
			}
		});

	}

}
