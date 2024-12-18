/*
Ryan Harding, CEN-3024C-14320, 09/08/2024
Software Development 1
Function: Creates a Book object with three required parts: id, title, and author
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private boolean isCheckedOut;

    //constructor
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isCheckedOut = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    /*
    Method: toString
    The purpose of this method is to print out the Book object as a string
    Arguments: id + title + author
    Return: result String
     */
    @Override
    public String toString() {
        String result;
        result = id + ", " + title + ", " + author + "," + (isCheckedOut ? " (Checked Out)" : " (Available)");
        return result;
    }//end toString method

}//end Book Class
