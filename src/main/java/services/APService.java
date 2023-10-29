package services;

import interfaces.APDao;
import interfaces.APObject;
import jakarta.persistence.NoResultException;
import utils.Utils;

import java.util.List;
import java.util.Scanner;

public class APService<T extends APObject> {

    private final APDao<T> DAO;
    private final APObject object;
    private final Scanner sc;

    public APService(APDao<T> dao, APObject object) {
        this.DAO = dao;
        this.object = object;
        this.sc = new Scanner(System.in);
    }

    public void create() {
        try {
            System.out.println("Please enter the " + object.getClass().getSimpleName() + "'s name");
            String name = sc.nextLine();
            Utils.checkEmptyString(name);
            object.setName(name);
            DAO.save((T) object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void printAll() {
        try {
            List<T> entities = DAO.findAll();
            System.out.println("Here's the list of " + object.getClass().getSimpleName() + "s");
            int counter = 1;
            for (T DBObject : entities) {
                System.out.println(DBObject);
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

    public T searchByName() throws Exception {
        System.out.println("Please enter the " + object.getClass().getSimpleName() + "'s name");
        String name = sc.nextLine();
        Utils.checkEmptyString(name);
        return DAO.findByName(name);
    }

    public void modify() {
        try {
            int op;
            do {
                System.out.println("Please choose the " + object.getClass().getSimpleName() + " you want to modify");
                T DBObject = searchByName();
                if (DBObject != null) {
                    System.out.println("You selected:");
                    System.out.println(DBObject);
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

                        List<T> DBObjects = DAO.findAll();
                        List<String> DBObjectNames = DBObjects.stream().map(T::getName).toList();
                        Utils.checkRepeatedValue(DBObjectNames, name);

                        object.setName(name);
                        DAO.edit((T) object);
                        return;
                    }
                } else {
                    System.out.println("The " + object.getClass().getSimpleName() + " isn't present in the database");
                    System.out.println("Do you want to modify a different " + object.getClass().getSimpleName() + "?");
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
                T DBObject = searchByName();
                if (DBObject != null) {
                    System.out.println("You selected:");
                    System.out.println(DBObject);
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
                        DBObject.setActive(false);
                        DAO.edit(DBObject);
                    }
                } else {
                    System.out.println("The " + object.getClass().getSimpleName() + " isn't present in the database");
                    System.out.println("Do you want to delete a different " + object.getClass().getSimpleName() + "?");
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
