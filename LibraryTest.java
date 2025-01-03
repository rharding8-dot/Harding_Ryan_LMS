import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
        // No need to load books from file here, leave it for the specific test cases
    }

    @Test
    void checkOutBook() {
        // Add a book to the library manually for testing
        createTestBookFile("testBooks.txt");
        library.loadBooksFromFile("testBooks.txt");

        library.checkOutBook("SomeBook");
        assertTrue(library.getBooks().get(0).isCheckedOut(), "The book should be checked out.");
    }

    @Test
    void checkInBook() {
        // Add a book manually for testing
        createTestBookFile("testBooks.txt");
        library.loadBooksFromFile("testBooks.txt");

        library.checkOutBook("SomeBook");
        library.checkInBook("SomeBook");
        assertFalse(library.getBooks().get(0).isCheckedOut(), "The book should be checked in.");
    }

    @Test
    void loadBooksFromFile() {
        // Creates a sample file to test loading books
        String fileName = "testBooks.txt";
        createTestBookFile(fileName);

        library.loadBooksFromFile(fileName);
        assertEquals(2, library.getBooks().size(), "There should be 2 books loaded.");
    }

    @Test
    void removeBook() {

        createTestBookFile("testBooks.txt");
        library.loadBooksFromFile("testBooks.txt");

        library.removeBook(1);
        assertEquals(1, library.getBooks().size(), "There should only be 1 book remaining.");
    }

    // Helps create the test book file
    private void createTestBookFile(String fileName) {
        try (FileWriter writer = new FileWriter(new File(fileName))) {
            writer.write("1,SomeBook,SomeAuthor\n");
            writer.write("2,AnotherBook,AnotherAuthor\n");
        } catch (IOException e) {
            System.err.println("Error creating test book file: " + e.getMessage());
        }
    }
}
