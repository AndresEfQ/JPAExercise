package services;

import entities.Book;
import entities.Author;
import entities.Publisher;
import jakarta.persistence.NoResultException;
import persistence.AuthorDAO;
import persistence.BookDAO;
import persistence.PublisherDAO;
import utils.Utils;

import java.util.InputMismatchException;
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

            List<String> isbns = currentBooks.stream().map((book) -> book.getIsbn().toString()).toList();
            System.out.print("ISBN: ");
            String aux = sc.nextLine();
            Utils.checkEmptyString(aux);
            Utils.checkRepeatedValue(isbns, aux);
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

        } catch (NoResultException e) {
            System.out.println("It is not present in the database, please create it first from the proper menu");
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

    public List<Book> findByParameter() {
        List<Book> books = null;

        System.out.println("Please choose how do you wont to search for the book");
        System.out.println();
        System.out.println("1. By ISBN");
        System.out.println("2. By Title");
        System.out.println("3. By Author");
        System.out.println("4. By Publisher");

        int op;
        do {
            op = Integer.parseInt(sc.nextLine());
            if (op < 1 || op > 4) {
                System.out.println("Option must be between 1 and 4");
            }
        } while (op < 1 || op > 4);
        String param;
        switch (op) {
            case 1 -> param = "isbn";
            case 2 -> param = "title";
            case 3 -> param = "author.name";
            case 4 -> param = "publisher.name";
            default -> param = "";
        }

        System.out.println("Please insert the " + param + "you want to search for");
        String value = sc.nextLine();

        try {
            books = bookDAO.findByParameter(param, value);
            if (books.isEmpty()) {
                throw new NoResultException("Couldn't find any book with the given parameter");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return books;
    }

    public void modifyBook() {
        System.out.println("Please choose the book you want to modify");
        List<Book> books = findByParameter();
        Book selectedBook;
        if (books.size() > 1) {
            System.out.println("These books where found, please choose one:");
            int counter = 1;
            for (Book book : books) {
                System.out.println(counter + ". " + book);
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
        char confirm = sc.nextLine().charAt(0);
        if (confirm == 'n' || confirm == 'N') {
            System.out.println("Modification canceled");
            return;
        }

        try {
            confirm = 'n';
            do {
                System.out.println("Please select the parameter you want to change");
                System.out.println();
                System.out.println("1. ISBN");
                System.out.println("2. Title");
                System.out.println("3. Author");
                System.out.println("4. Publisher");

                int op;
                do {
                    op = Integer.parseInt(sc.nextLine());
                    if (op < 1 || op > 4) {
                        System.out.println("Option must be between 1 and 4");
                    }
                } while (op < 1 || op > 4);

                System.out.println("Please choose the new value");
                String newVal = sc.nextLine();
                Utils.checkEmptyString(newVal);


                List<Book> currentBooks = bookDAO.findAll();

                switch (op) {
                    case 1 -> {
                        List<String> isbns = currentBooks.stream().map((book) -> book.getIsbn().toString()).toList();
                        Utils.checkRepeatedValue(isbns, newVal);
                        selectedBook.setIsbn(Long.parseLong(newVal));
                    }
                    case 2 -> {
                        List<String> titles = currentBooks.stream().map(Book::getTitle).toList();
                        Utils.checkRepeatedValue(titles, newVal);
                        selectedBook.setTitle(newVal);
                    }
                    case 3 -> {
                        List<String> authorNames = currentBooks.stream().map((book) -> book.getAuthor().getName()).toList();
                        Utils.checkRepeatedValue(authorNames, newVal);
                        Author author = authorDAO.findByName(newVal);
                        selectedBook.setAuthor(author);
                    }
                    case 4 -> {
                        List<String> publisherNames = currentBooks.stream().map((book) -> book.getPublisher().getName()).toList();
                        Utils.checkRepeatedValue(publisherNames, newVal);
                        Publisher publisher = publisherDAO.findByName(newVal);
                        selectedBook.setPublisher(publisher);
                    }
                }
                System.out.println("Do you want to change a different parameter? (y/N)");
                confirm = sc.nextLine().charAt(0);

            } while (confirm == 'y');

            bookDAO.edit(selectedBook);
        } catch (Exception e) {
        System.out.println(e.getMessage());
        e.getStackTrace();
    }




    }
}

/*
    private Long isbn;
    private String title;
    private Integer year;
    private Integer instances;
    private Integer lendInstances;
    private Integer leftInstances;
    private Boolean isActive;

    System.out.println("1. Create Book");
    System.out.println("2. Print all Books");
    System.out.println("3. Search a Book by parameter");
    System.out.println("4. Modify a Book");
    System.out.println("5. Delete a Book");
 */