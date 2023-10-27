package services;

import entities.Publisher;
import jakarta.persistence.NoResultException;
import persistence.PublisherDAO;
import utils.Utils;

import java.util.List;
import java.util.Scanner;

public class PublisherService {
    private BookService bookService;
    private APService APService;
    private final PublisherDAO DAO;
    private final Scanner sc;

    public PublisherService() {
        this.DAO = new PublisherDAO();
        this.sc = new Scanner(System.in);
    }

    public void setServices(BookService bookService, APService APService) {
        this.bookService = bookService;
        this.APService = APService;
    }

    public void createPublisher() {
        try {
            System.out.println("Please enter the Publisher's name");
            String name = sc.nextLine();
            Utils.checkEmptyString(name);
            Publisher publisher = new Publisher();
            publisher.setName(name);
            DAO.save(publisher);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void printAllPublishers() {
        try {
            List<Publisher> publishers = DAO.findAll();
            System.out.println("Here's the list of Publishers");
            int counter = 1;
            for (Publisher publisher : publishers) {
                System.out.println(counter + ". " + publisher);
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

    public Publisher searchByName() throws Exception {
        try {
            System.out.println("Please enter the Publisher's name");
            String name = sc.nextLine();
            Utils.checkEmptyString(name);
            return DAO.findByName(name);
        } catch (NoResultException e) {
            return null;
        }

    }

    public void modifyPublisher() {
        try {
            int op;
            do {
                System.out.println("Please choose the Publisher you want to modify");
                Publisher publisher = searchByName();
                if (publisher != null) {
                    System.out.println("You selected:");
                    System.out.println(publisher);
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

                        List<Publisher> publishers = DAO.findAll();
                        List<String> publisherNames = publishers.stream().map(Publisher::getName).toList();
                        Utils.checkRepeatedValue(publisherNames, name);

                        publisher.setName(name);
                        DAO.edit(publisher);
                        return;
                    }
                } else {
                    System.out.println("The Publisher isn't present in the database");
                    System.out.println("Do you want to modify a different Publisher?");
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
                Publisher publisher = searchByName();
                if (publisher != null) {
                    System.out.println("You selected " + publisher);
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

                        DAO.edit(publisher);
                    }
                } else {
                    System.out.println("The Publisher isn't present in the database");
                    System.out.println("Do you want to delete a different Publisher?");
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
