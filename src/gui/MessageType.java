package gui;

/*
*   File: MessageType.java
*   Authors: Matthew Homan, Bethany Van Waes, and Michael Rach
*   Date: 12/12/2023
*   Purpose: This file creates the MessageType for the success types. 
*/

public class MessageType {
    public enum SuccessMessageType {
        GENERIC,           // For general success messages
        RECIPE_ADDED,      // For success messages related to adding recipes
        RECIPE_DELETED     // For success messages related to deleting recipes
    }
}
