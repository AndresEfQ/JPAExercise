package services;

import entities.Author;
import jakarta.persistence.NoResultException;
import persistence.AuthorDAO;
import utils.Utils;

import java.util.List;
import java.util.Scanner;

public class AuthorService {

    private BookService bookService;
    private PublisherService publisherService;
    private final AuthorDAO DAO;
    private final Scanner sc;

    public AuthorService() {
        this.DAO = new AuthorDAO();
        this.sc = new Scanner(System.in);
    }

    public void setServices(BookService bookService, PublisherService publisherService) {
        this.bookService = bookService;
        this.publisherService = publisherService;
    }

    public void createAuthor() {
        try {
            System.out.println("Please enter the Author's name");
            String name = sc.nextLine();
            Utils.checkEmptyString(name);
            Author author = new Author();
            author.setName(name);
            DAO.save(author);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void printAllAuthors() {
        try {
            List<Author> authors = DAO.findAll();
            System.out.println("Here's the list of Authors");
            int counter = 1;
            for (Author author : authors) {
                System.out.println(counter + ". " + author);
                counter++;
                if (counter > 100) {
                    System.out.println("...");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Author searchByName() throws Exception {
        try {
            System.out.println("Please enter the Author's name");
            String name = sc.nextLine();
            Utils.checkEmptyString(name);
            return DAO.findByName(name);
        } catch (NoResultException e) {
            return null;
        }

    }

    public void modifyAuthor() {
        try {
            int op;
            do {
                System.out.println("Please choose the Author you want to modify");
                Author author = searchByName();
                if (author != null) {
                    System.out.println("You selected:");
                    System.out.println(author);
                    System.out.println("Do you want to modify it?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    System.out.println("0. Cancel");
                    do {
                        op = Integer.parseInt(sc.nextLine());
                        if (op < 0 || op > 2) {
                            System.out.println("Invalid option, please choose 1, 2 or 0");
                        }
                    } while (op < 0 || op > 2);

                    if (op == 1) {
                        System.out.println("Please choose a new name");
                        String name = sc.nextLine();
                        Utils.checkEmptyString(name);

                        List<Author> authors = DAO.findAll();
                        List<String> authorNames = authors.stream().map(Author::getName).toList();
                        Utils.checkRepeatedValue(authorNames, name);

                        author.setName(name);
                        DAO.edit(author);
                        return;
                    }
                } else {
                    System.out.println("The Author isn't present in the database");
                    System.out.println("Do you want to modify a different Author?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    System.out.println("0. Cancel");
                    do {
                        op = Integer.parseInt(sc.nextLine());
                        if (op < 0 || op > 2) {
                            System.out.println("Invalid option, please choose 1, 2 or 0");
                        }
                    } while (op < 0 || op > 2);
                    if (op == 2) {
                        op = 0;
                    }
                }
            } while (op != 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void deleteByName() {
        try {
            int op;
            do {
                Author author = searchByName();
                if (author != null) {
                    System.out.println("You selected " + author);
                    System.out.println("Do you want to delete it?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    System.out.println("0. Cancel");
                    do {
                        op = Integer.parseInt(sc.nextLine());
                        if (op < 0 || op > 2) {
                            System.out.println("Invalid option, please choose 1, 2 or 0");
                        }
                    } while (op < 0 || op > 2);

                    if (op == 1) {

                        DAO.edit(author);
                    }
                } else {
                    System.out.println("The Author isn't present in the database");
                    System.out.println("Do you want to delete a different Author?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    System.out.println("0. Cancel");
                    do {
                        op = Integer.parseInt(sc.nextLine());
                        if (op < 0 || op > 2) {
                            System.out.println("Invalid option, please choose 1, 2 or 0");
                        }
                    } while (op < 0 || op > 2);
                    if (op == 2) {
                        op = 0;
                    }
                }
            } while (op != 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }
}
