/**
Ryan Harding, CEN-3024C-14320, 09/08/2024
Software Development 1
Function: Main is to initialize the Library and Menu, and run the LMS
Objective: The goal for this program is to have a simple, yet functioning
library management system. It will be console-based and users will be presented
with a menu system. They will at first be given the prompt to upload their .txt file
via the exact file path, then they will be presented with the menu choices.
The choices will include Listing books, Removing books, and Exiting the program.
Users will have to rerun the program to then add more .txt files of books if desired.
 */
public class Main {
    /**
    main method- initializes the Library and Menu
    consoleMenu will ask to load new books and also display menu
     */
    public static void main(String[] args) {

        Library library = new Library();
        Menu consoleMenu = new Menu(library);

        consoleMenu.loadBooks();
        consoleMenu.displayMenu();

    }//end main method

}//end main class