import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
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
    private JTable table1;

    /**
    Method Name: MainFrame
    Explanation: This constructor initializes the MainFrame GUI and its functions with the LMS
    Arguments: Instance of the library class
    Return Value: None since it's a constructor.
     */
    public MainFrame(Library library) {
        this.library = library;
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        mainPanel.setBackground(Color.DARK_GRAY);


        /**
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

    /**
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


    /**
    Method Name: listBooks
    Explanation: This method displays books that are currently in the MySQL database.
    Arguments: none
    Return Value: Displays information in a GUI table
     */
    private void listBooks() {
        String query = "SELECT * FROM Books";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            //Convert ResultSet to TableModel - Still unsure what this means
            DefaultTableModel tableModel = new DefaultTableModel();
            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }

            table1.setModel(tableModel); //Displays in JTable
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading books.");
        }

    }//end listBooks method


    /**
    Method Name: removeBook
    Explanation: This method prompts the user to enter the ID of the book to remove from the LMS
    Arguments: none
    Return Value: Displays information in a window
     */
    private void removeBook() {
        String input = JOptionPane.showInputDialog(this, "Enter the Title or Barcode of the book to remove:");
        if (input != null && !input.isEmpty()) {
            String query = "DELETE FROM Books WHERE Title = ? OR Barcode = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, input);
                stmt.setString(2, input);
                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Book removed successfully.");
                    listBooks(); //Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, "No matching book found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error removing book.");
            }

        }

    }//end removeBook helper method

    /**
    Method Name: checkOutBook
    Explanation: Allows user to check out a book in the lms which then changes the status of the book
    to unavailable. User must enter the title of the book.
    Arguments: none
    Return Value: Displays information in a window
     */
    private void checkOutBook() {
        String barcode = JOptionPane.showInputDialog(this, "Enter the Barcode of the book to check out:");
        String dueDate = JOptionPane.showInputDialog(this, "Enter the Due Date (YYYY-MM-DD):");

        if (barcode != null && !barcode.isEmpty() && dueDate != null && !dueDate.isEmpty()) {
            String query = "UPDATE Books SET Status = 'checked out', DueDate = ? WHERE Barcode = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, dueDate);
                stmt.setString(2, barcode);
                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Book checked out successfully.");
                    listBooks(); //Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, "No matching book found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error checking out book.");
            }

        }

    }//end checkOutBook helper method

    /**
    Method Name: checkInBook
    Explanation: Allows user to check in a book in the lms which then changes the status of the book
    to available. User must enter the title of the book.
    Arguments: none
    Return Value: Displays information in a window
     */
    private void checkInBook() {
        String barcode = JOptionPane.showInputDialog(this, "Enter the Barcode of the book to check in:");
        if (barcode != null && !barcode.isEmpty()) {
            String query = "UPDATE Books SET Status = 'checked in', DueDate = NULL WHERE Barcode = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, barcode);
                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Book checked in successfully.");
                    listBooks(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, "No matching book found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error checking in book.");
            }

        }

    }//end checkInBook

    /**
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
