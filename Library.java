import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
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
    Method: checkOutBook
    The purpose of this method is to check to see if a book and its associated
    id is already checked out. If not it will check out the book.
    Arguments: If the title of the book is already checked out, then notify user.
    If book isn't checked out, then check out the book.
    Return value: IF-ELSE result with checked out status. Also by
    default the book list will be reprinted with the updated checked In/Out status after.
     */
    public void checkOutBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isCheckedOut()) {
                    System.out.println("Book with the title: " + title + " is already checked out.\n");
                }else{
                    book.setCheckedOut(true);
                    System.out.println("Book with the title: " + title + " has been checked out.\n" +
                            "It will be due 4 week from: " + LocalDateTime.now());
                }
                listBooks();
                return;
            }
        }
        System.out.println("No book with the title: " + title + " is found.");
    }
    /*
    Method: checkInBook
    The purpose of this method is to check in a book that has already been checked out.
    If the book is already checked in, then it will notify the user.
    Arguments: Unless the desired book was never checked out to begin with, the book will be check-in.
    It will notify the user if the book was never checked out.
    Return value: IF-ELSE result with checked In status. Also by
    default the book list will be reprinted with the updated checked In/Out status after.
     */
    public void checkInBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (!book.isCheckedOut()) {
                    System.out.println("Book with the title: " + title + " is not checked out.\n");
                } else {
                    book.setCheckedOut(false);
                    System.out.println("Book with the title " + title + " has been checked in.\n");
                }
                listBooks();
                return;
            }
        }
        System.out.println("No book with the title " + title + " found.");
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


    // Getter method for the books
    public ArrayList<Book> getBooks() {
        return books;
    }


}//end Library Class
