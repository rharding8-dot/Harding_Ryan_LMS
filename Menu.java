import java.util.Scanner;
/*
Ryan Harding, CEN-3024C-14320, 09/08/2024
Software Development 1
Function: The Menu Class handles the structure of the menu system
in the console, including prompting the user of the choices.
 */
public class Menu {
    private Library library;
    private Scanner scanner;

    public Menu(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    /*
    Method: displayMenu
    The purpose of this method is to display the menu system in the console
    and handle the users input.
    Arguments: if 1 - list, if 2 - remove, if 3 - exit
    Return: depends on user choice
     */
    public void displayMenu() {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. List all books");
            System.out.println("2. Remove a book");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.listBooks();
                    break;
                case 2:
                    System.out.print("Enter the ID of the book to remove: ");
                    int id = scanner.nextInt();
                    library.removeBook(id);
                    break;
                case 3:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Incorrect option, please choose again.");
            }//end switch

        }//end while

    }//end displayMenu method

    /*
    Method: loadBooks
    The purpose of this method is the scan the books from the given file path
    and loading them into the LMS
    Argument: If file is correctly given, then file is loaded into arraylist
    Return: Uploaded data
     */
    public void loadBooks() {
        System.out.println("Enter the path to the books file: ");
        String filePath = scanner.nextLine();
        library.loadBooksFromFile(filePath);
    }//end loadBooks method

}//end Menu Class
