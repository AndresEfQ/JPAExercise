package services;

import entities.Book;
import entities.Author;
import entities.Publisher;
import enums.BookProperty;
import jakarta.persistence.NoResultException;
import persistence.AuthorDAO;
import persistence.BookDAO;
import persistence.PublisherDAO;
import utils.Utils;

import java.util.List;
import java.util.Scanner;

public class BookService {

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final PublisherDAO publisherDAO;
    private final Scanner sc;

    public BookService() {
        bookDAO = new BookDAO();
        authorDAO = new AuthorDAO();
        publisherDAO = new PublisherDAO();
        sc = new Scanner(System.in);
    }

    public void createBook() {
        try {
            List<Book> currentBooks = bookDAO.findAll();

            System.out.println("Please enter the Book's data");

            List<String> isbnValues = currentBooks.stream().map((book) -> book.getIsbn().toString()).toList();
            System.out.print("ISBN: ");
            String aux = sc.nextLine();
            Utils.checkEmptyString(aux);
            Utils.checkRepeatedValue(isbnValues, aux);
            Long isbn = Long.parseLong(aux);

            List<String> titles = currentBooks.stream().map(Book::getTitle).toList();
            System.out.print("Title: ");
            String title = sc.nextLine();
            Utils.checkEmptyString(title);
            Utils.checkRepeatedValue(titles, title);

            System.out.print("Year: ");
            int year = Integer.parseInt(sc.nextLine());

            System.out.print("How many instances of this book are in the library? ");
            int instances = Integer.parseInt(sc.nextLine());

            System.out.print("Author: ");
            Author author = authorDAO.findByName(sc.nextLine());

            System.out.print("Publisher: ");
            Publisher publisher = publisherDAO.findByName(sc.nextLine());

            Book book = new Book(isbn, title, year, instances, author, publisher);
            bookDAO.save(book);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void printAllBooks() {
        try {
            List<Book> books = bookDAO.findAll();
            int counter = 1;
            for (Book book : books) {
                System.out.println(book);
                counter++;
                if (counter > 100) {
                    System.out.println("...");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public List<Book> findByProperty() {
        List<Book> books;

        System.out.println("Please choose a property to look for in the book");
        System.out.println();
        for (int i = 0; i < 5; i++) {
            BookProperty aux = BookProperty.values()[i];
            System.out.println(aux.getVal() + ". By " + aux.getPrettyName());
        }

        int op;
        do {
            op = Integer.parseInt(sc.nextLine());
            if (op < 1 || op > 5) {
                System.out.println("Option must be between 1 and 5");
            }
        } while (op < 1 || op > 5);

        BookProperty prop = BookProperty.values()[op - 1];

        System.out.println("Please insert the " + prop.getPrettyName() + " you want to search for");
        String value = sc.nextLine();

        try {
            books = bookDAO.findByProperty(prop.getQueryName(), value);
            if (books.isEmpty()) {
                throw new NoResultException("Couldn't find any book with the given property");
            }
            return books;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return null;
    }

    public void modifyBook() {
        System.out.println("Please choose the book you want to modify");
        List<Book> books = findByProperty();
        if (books == null) {
            return;
        }
        Book selectedBook;
        if (books.size() > 1) {
            System.out.println("These books where found, please choose one:");
            int counter = 1;
            for (Book book : books) {
                System.out.println(counter + ". " + book);
                counter++;
            }
            int op;
            do {
                op = Integer.parseInt(sc.nextLine());
                if (op < 1 || op > books.size()) {
                    System.out.println("Invalid option, please try again");
                }
            } while (op < 1 || op > books.size());
            selectedBook = books.get(op - 1);
        } else {
            selectedBook = books.get(0);
        }
        System.out.println("You selected " + selectedBook);
        System.out.println("Do you want to modify it? (Y/n)");
        char confirm;
        String confirmString = sc.nextLine();
        if (confirmString.isEmpty()) {
            confirm = 'y';
        } else {
            confirm = confirmString.charAt(0);
        }
        if (confirm == 'n' || confirm == 'N') {
            System.out.println("Modification canceled");
            return;
        }
        try {
            do {
                System.out.println("Please select the property you want to change");
                System.out.println();
                System.out.println("1. ISBN");
                System.out.println("2. Title");
                System.out.println("3. Year");
                System.out.println("4. Author");
                System.out.println("5. Publisher");

                int op;
                do {
                    op = Integer.parseInt(sc.nextLine());
                    if (op < 1 || op > 5) {
                        System.out.println("Option must be between 1 and 5");
                    }
                } while (op < 1 || op > 5);

                System.out.println("Please choose the new value");
                String newVal = sc.nextLine();
                Utils.checkEmptyString(newVal);

                List<Book> currentBooks = bookDAO.findAll();

                switch (op) {
                    case 1 -> {
                        List<String> isbnValues = currentBooks.stream().map((book) -> book.getIsbn().toString()).toList();
                        Utils.checkRepeatedValue(isbnValues, newVal);
                        selectedBook.setIsbn(Long.parseLong(newVal));
                    }
                    case 2 -> {
                        List<String> titles = currentBooks.stream().map(Book::getTitle).toList();
                        Utils.checkRepeatedValue(titles, newVal);
                        selectedBook.setTitle(newVal);
                    }
                    case 3 -> selectedBook.setYear(Integer.parseInt(newVal));
                    case 4 -> {
                        Author author = authorDAO.findByName(newVal);
                        selectedBook.setAuthor(author);
                    }
                    case 5 -> {
                        Publisher publisher = publisherDAO.findByName(newVal);
                        selectedBook.setPublisher(publisher);
                    }
                }
                System.out.println("Do you want to change a different property? (y/N)");
                confirmString = sc.nextLine();
                if (confirmString.isEmpty()) {
                    confirm = 'n';
                } else {
                    confirm = confirmString.charAt(0);
                }
            } while (confirm == 'y');

            bookDAO.edit(selectedBook);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void deleteBook() {
        System.out.println("Please choose the book you want to delete");
        List<Book> books = findByProperty();
        if (books == null) {
            return;
        }
        Book selectedBook;
        if (books.size() > 1) {
            System.out.println("These books where found, please choose one:");
            int counter = 1;
            for (Book book : books) {
                System.out.println(counter + ". " + book);
                counter++;
            }
            int op;
            do {
                op = Integer.parseInt(sc.nextLine());
                if (op < 1 || op > books.size()) {
                    System.out.println("Invalid option, please try again");
                }
            } while (op < 1 || op > books.size());
            selectedBook = books.get(op - 1);
        } else {
            selectedBook = books.get(0);
        }
        System.out.println("You selected " + selectedBook);
        System.out.println("Do you want to delete it? (Y/n)");
        String confirmString = sc.nextLine();
        char confirm;
        if (confirmString.isEmpty()) {
            confirm = 'y';
        } else {
            confirm = sc.nextLine().charAt(0);
        }
        if (confirm == 'n' || confirm == 'N') {
            System.out.println("Modification canceled");
            return;
        }
        selectedBook.setActive(false);
        try {
            bookDAO.edit(selectedBook);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }
}
