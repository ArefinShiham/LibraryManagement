interface Libraryitem {
    void displayDetails();
}
abstract class Book implements Libraryitem {
    String title,author,ISBN;

    public Book(String  title, String author,String ISBN ) throws InvalidISBNExceptions {
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
class FictionBooks extends Books {
    public FictionBooks(String title, String author, String ISBN)throws InvalidISBNExceptions {
        super(title,author,ISBN);
    }
    @Override
    public void displayDetails(){
        System.out.println("Fiction Book - Title: " + title + ", Author: " + author + ", ISBN:" + ISBN);
    }
}
class InvalidISBNExceptions extends Exception {
    public InvalidISBNExceptions(String message){
        super(message);
    }
}
