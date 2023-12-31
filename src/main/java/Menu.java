import entities.Publisher;
import jakarta.persistence.NoResultException;
import persistence.AuthorDAO;
import persistence.PublisherDAO;
import services.APService;
import services.BookService;
import entities.Author;
import entities.Book;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final APService<Author> authorService = new APService<>(new AuthorDAO(), new Author());
    private static final APService<Publisher> publisherService = new APService<>(new PublisherDAO(), new Publisher());
    private static final BookService bookService = new BookService();
    private static final Scanner sc = new Scanner(System.in);

    private static void printMenu() {
        System.out.println();
        System.out.println("Welcome to the library");
        System.out.println("Please choose a category");
        System.out.println();
        System.out.println("1. Author");
        System.out.println("2. Publisher");
        System.out.println("3. Book");
        System.out.println("4. Print the menu");
        System.out.println("0. Exit");
    }

    private static void printAuthorMenu() {
        System.out.println();
        System.out.println("You have chosen Author, please select an option");
        System.out.println();
        System.out.println("1. Create Author");
        System.out.println("2. Print all Authors");
        System.out.println("3. Search an Author by name");
        System.out.println("4. Modify an Author");
        System.out.println("5. Delete an Author");
        System.out.println("6. Print the menu");
        System.out.println("0. Go back");
    }

    private static void printPublisherMenu() {
        System.out.println();
        System.out.println("You have chosen Publisher, please select an option");
        System.out.println();
        System.out.println("1. Create Publisher");
        System.out.println("2. Print all Publishers");
        System.out.println("3. Search a Publisher by name");
        System.out.println("4. Modify a Publisher");
        System.out.println("5. Delete a Publisher");
        System.out.println("6. Print the menu");
        System.out.println("0. Go back");
    }

    private static void printBookMenu() {
        System.out.println();
        System.out.println("You have chosen Book, please select an option");
        System.out.println();
        System.out.println("1. Create Book");
        System.out.println("2. Print all Books");
        System.out.println("3. Search a Book by property");
        System.out.println("4. Modify a Book");
        System.out.println("5. Delete a Book");
        System.out.println("6. Print the menu");
        System.out.println("0. Go back");
    }

    public static void runMenu() {
        printMenu();
        int op;
        do {
            try {
                op = Integer.parseInt(sc.nextLine());
                if (op < 0 || op > 4) {
                    throw new IllegalArgumentException("Option must be between 0 and 4");
                }
            } catch (IllegalArgumentException | InputMismatchException e) {
                op = -1;
                System.out.println(e.getMessage());
                e.getStackTrace();
            }

            switch (op) {
                case 1 -> runAuthorMenu();
                case 2 -> runPublisherMenu();
                case 3 -> runBookMenu();
                case 4 -> printMenu();
                case 0 -> System.out.println("Bye!");
            }

        } while (op != 0);
    }

    private static void runAuthorMenu() {
        printAuthorMenu();
        int op;
        do {
            try {
                op = Integer.parseInt(sc.nextLine());
                if (op < 0 || op > 6) {
                    throw new IllegalArgumentException("Option must be between 0 and 6");
                }
            } catch (IllegalArgumentException | InputMismatchException e) {
                op = -1;
                System.out.println(e.getMessage());
                e.getStackTrace();
            }

            switch (op) {
                case 1 -> {
                    authorService.create();
                    System.out.println();
                    System.out.println("You're back in the Author's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 2 -> {
                    authorService.printAll();
                    System.out.println();
                    System.out.println("You're back in the Author's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 3 -> {
                    try {
                        System.out.println(authorService.searchByName());
                    } catch (NoResultException ignored) {
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.getStackTrace();
                    }
                    System.out.println();
                    System.out.println("You're back in the Author's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 4 -> {
                    authorService.modify();
                    System.out.println();
                    System.out.println("You're back in the Author's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 5 -> {
                    authorService.deleteByName();
                    System.out.println();
                    System.out.println("You're back in the Author's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 6 -> printAuthorMenu();
                case 0 -> printMenu();
            }

        } while (op != 0);
    }

    private static void runPublisherMenu() {
        printPublisherMenu();
        int op;
        do {
            try {
                op = Integer.parseInt(sc.nextLine());
                if (op < 0 || op > 6) {
                    throw new IllegalArgumentException("Option must be between 0 and 6");
                }
            } catch (IllegalArgumentException | InputMismatchException e) {
                op = -1;
                System.out.println(e.getMessage());
                e.getStackTrace();
            }

            switch (op) {
                case 1 -> {
                    publisherService.create();
                    System.out.println();
                    System.out.println("You're back in the Publisher's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 2 -> {
                    publisherService.printAll();
                    System.out.println();
                    System.out.println("You're back in the Publisher's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 3 -> {
                    try {
                        System.out.println(publisherService.searchByName());
                    } catch (NoResultException ignored) {
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.getStackTrace();
                    }
                    System.out.println();
                    System.out.println("You're back in the Publisher's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 4 -> {
                    publisherService.modify();
                    System.out.println();
                    System.out.println("You're back in the Publisher's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 5 -> {
                    publisherService.deleteByName();
                    System.out.println();
                    System.out.println("You're back in the Publisher's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 6 -> printPublisherMenu();
                case 0 -> printMenu();
            }
        } while (op != 0);
    }

    private static void runBookMenu() {
        printBookMenu();
        int op;
        do {
            try {
                op = Integer.parseInt(sc.nextLine());
                if (op < 0 || op > 6) {
                    throw new IllegalArgumentException("Option must be between 0 and 6");
                }
            } catch (IllegalArgumentException | InputMismatchException e) {
                op = -1;
                System.out.println(e.getMessage());
                e.getStackTrace();
            }

            switch (op) {
                case 1 -> {
                    bookService.createBook();
                    System.out.println();
                    System.out.println("You're back in the Book's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 2 -> {
                    bookService.printAllBooks();
                    System.out.println();
                    System.out.println("You're back in the Book's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 3 -> {
                    try {
                        List<Book> books = bookService.findByProperty();
                        books.forEach(System.out::println);
                    } catch (NoResultException ignored) {
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.getStackTrace();
                    }
                    System.out.println();
                    System.out.println("You're back in the Book's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 4 -> {
                    bookService.modifyBook();
                    System.out.println();
                    System.out.println("You're back in the Book's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 5 -> {
                    bookService.deleteBook();
                    System.out.println();
                    System.out.println("You're back in the Book's Menu");
                    System.out.println("Please choose an option or select 6 to print the menu");
                }
                case 6 -> printBookMenu();
                case 0 -> printMenu();
            }
        } while (op != 0);
    }
}
