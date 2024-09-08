import java.io.*;
import java.util.ArrayList;
/*
Ryan Harding, CEN-3024C-14320, 09/08/2024
Software Development 1
Function: The Library Class handles the arraylist of the program,
loads books into the list, removes them via id#, and prints out
the list in the console.
 */
public class Library {
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    /*
    Method: loadBooksFromFile
    The purpose of this method is to add books from a .txt file
    Arguments: Checks to see if file is in the correct format
    Return value: Uploaded files or error message
     */
    public void loadBooksFromFile(String fileName) {

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                int id = Integer.parseInt(details[0].trim());
                String title = details[1].trim();
                String author = details[2].trim();
                books.add(new Book(id, title, author));
            }
            System.out.println("Books successfully loaded from file.");
        } catch (IOException e) {
            System.out.println("Error with loading file: " + e.getMessage());
        }//trycatch

    }//end method loadBooksFromFile

    /*
    Method: removeBook
    The purpose of this method is to remove books in LMS via given id number
    Argument: Removes if gets id number
     */
    public void removeBook(int id) {
        books.removeIf(book -> book.getId() == id);
        System.out.println("Book with ID " + id + " removed.");
    }//end removeBook method

    /*
    Method: listBooks
    The purpose of this method is to list all the books in the LMS arraylist
    Argument: If empty then No books in LMS, else print books in list
    Return: No Books or Books in list
     */
    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books are in the library.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }//end listBooks method

}//end Library Class
