package main;

import javax.swing.SwingUtilities;

import gui.GUI;

public class RecipeLibrary {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUI();
			}
		});
	}
}