import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Ryan Harding, CEN-3024C-14320, 11/03/2024
MainFrame.java
Explanation: MainFrame.java takes care of the layout functions within MainFrame.form
- So in other words this class deals with the commands and instructions for the GUI
Objective: This class will allow the GUI to function as intended.
 */

public class MainFrame extends JFrame {
    private Library library;
    private JButton listBooksButton;
    private JButton removeBookButton;
    private JButton checkOutBookButton;
    private JButton checkInBookButton;
    private JButton exitButton;
    private JPanel mainPanel;
    private JButton uploadBooksButton;

    /*
    Method Name: MainFrame
    Explanation: This constructor initializes the MainFrame GUI and its functions with the LMS
    Arguments: Instance of the library class
    Return Value: None since it's a constructor.
     */
    public MainFrame(Library library) {
        this.library = library;
        setTitle("Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);


        /*
        Method Name: actionPerformed
        Explanation: Below are the following EVENT LISTENERS for each button in the GUI
        Argument: When button is clicked, a following helper method will enable
        Return Value: none
         */
        uploadBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadBooks();
            }
        });
        //Event Listener
        listBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listBooks();
            }
        });
        //Event Listener
        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });
        //Event Listener
        checkOutBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkOutBook();
            }
        });
        //Event Listener
        checkInBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkInBook();
            }
        });
        //Event Listener
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }//end MainFrame method

    /*
    Method Name: uploadBooks
    Explanation: This method opens a file chooser dialog to allow the user to
    look for their .txt file to upload. It then calls the "loadBooksFromFile" method
    from the Library class to upload the books into the LMS instance.
    Arguments: None
    Return Value: Displays information in a window
     */
    private void uploadBooks() {
        //Opens a file chooser dialog box
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Book List File");

        //Limits file chooser to only .txt files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            //Get file's absolute path
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            //Load books using the Library loadBooksFromFile method
            library.loadBooksFromFile(filePath);

            //Notify user if the file has been selected and uploaded successfully or not
            JOptionPane.showMessageDialog(this, "Books uploaded successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "No file selected.");
        }

    }//end uploadBooks helper method


    /*
    Method Name: listBooks
    Explanation: This method displays books that are currently in the lms instance.
    Arguments: none
    Return Value: Displays information in a window
     */
    private void listBooks() {
        StringBuilder bookList = new StringBuilder();
        for (Book book : library.getBooks()) {
            bookList.append(book.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, bookList.toString(), "List of Books", JOptionPane.INFORMATION_MESSAGE);
    }//end listBooks helper method

    /*
    Method Name: removeBook
    Explanation: This method prompts the user to enter the ID of the book to remove from the LMS
    Arguments: none
    Return Value: Displays information in a window
     */
    private void removeBook() {
        String idString = JOptionPane.showInputDialog(this, "Enter the ID of the book to remove:");
        if (idString != null && !idString.isEmpty()) {
            int id = Integer.parseInt(idString);
            library.removeBook(id);
            JOptionPane.showMessageDialog(this, "Book with ID " + id + " removed.");
        }
    }//end removeBook helper method

    /*
    Method Name: checkOutBook
    Explanation: Allows user to check out a book in the lms which then changes the status of the book
    to unavailable. User must enter the title of the book.
    Arguments: none
    Return Value: Displays information in a window
     */
    private void checkOutBook() {
        String title = JOptionPane.showInputDialog(this, "Enter the title of the book to check out:");
        if (title != null && !title.isEmpty()) {
            library.checkOutBook(title);
        }
    }//end checkOutBook helper method

    /*
    Method Name: checkInBook
    Explanation: Allows user to check in a book in the lms which then changes the status of the book
    to available. User must enter the title of the book.
    Arguments: none
    Return Value: Displays information in a window
     */
    private void checkInBook() {
        String title = JOptionPane.showInputDialog(this, "Enter the title of the book to check in:");
        if (title != null && !title.isEmpty()) {
            library.checkInBook(title);
        }
    }//end checkInBook

    /*
    Method Name: main
    Explanation: This method initializes the MainFrame.java file
    and creates an instance of the library and mainFrame.
     */
    public static void main(String[] args) {
        Library library = new Library();
        MainFrame mainFrame = new MainFrame(library);
        mainFrame.setVisible(true);
    }//end main method

}//end MainFrame.java class