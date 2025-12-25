import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
interface LibraryItem {
    void displayDetails();
}
abstract class Books implements Libraryitem {
    String title,author,ISBN;

    public Books(String  title, String author, String ISBN ) throws InvalidISBNExceptions {
        if (ISBN.length() < 10){
            throw new InvalidISBNExceptions("ISBN must be at least 10 characters long.");
        }
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }
    public String getTitle(){
        return title;
    }
}
class FictionBook extends Books {
    public FictionBook(String title, String author, String ISBN)throws InvalidISBNExceptions {
        super(title,author,ISBN);
    }
    @Override
    public void displayDetails(){
        System.out.println("Fiction Book - Title: " + title + ", Author: " + author + ", ISBN:" + ISBN);
    }
}
class InvalidISBNException extends Exception {
    public InvalidISBNException(String message){
        super(message);
    }
}

public class LibraryManagement extends JFrame implements ActionListener {
    private JTextField titleField, authorField, isbnField;
    private final JButton addButton;
    private static final ArrayList<Books> bookList = new ArrayList<>();

    public LibraryManagement() {
        setTitle("Library Management System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel titleLevel = new JLabel("Title:");
        titleLevel.setBounds(20, 20, 100, 30);
        add(titleLevel);

        titleField = new JTextField();
        titleField.setBounds(120, 20, 200, 30);
        add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(20, 60, 100, 30);
        add(authorLabel);

        authorField = new JTextField();
        authorField.setBounds(120, 60, 200, 30);
        add(authorField);

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setBounds(20, 100, 100, 30);
        add(isbnLabel);

        isbnField = new JTextField();
        isbnField.setBounds(120, 100, 200, 30);
        add(isbnField);

        addButton = new JButton("Add Book");
        addButton.setBounds(120, 150, 120, 30);
        addButton.addActionListener(this);
        add(addButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        try {
            Books book = new FictionBooks(title, author, isbn);
            bookList.add(book);
            sortAndSaveBooks();
            JOptionPane.showMessageDialog(this, "Book added succesfully!");
        } catch (InvalidISBNExceptions ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "File Error: " + ex.getMessage());
        }
    }

    public static void sortAndSaveBooks() throws IOException {
        bookList.sort(Comparator.comparing(Books::getTitle));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Books book : bookList) {
                writer.write(book.title + ", " + book.author + ", " + book.ISBN);
                writer.newLine();
            }
        }

    }

    private static void loadBooksFromFile() throws IOException {
        File file = new File("books.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(", ");
                try {
                    bookList.add(new FictionBooks(details[0], details[1], details[2]));
                } catch (InvalidISBNExceptions ignored) {

                }
            }

        }
    }

    public static void main(String[] args) {
        try {
            loadBooksFromFile();
        } catch (IOException e) {
            System.out.println("Error! loading books from file.");
        }
        new LibraryManagement();
    }
}
