package services;

import entities.Author;
import entities.Book;
import entities.Publisher;
import enums.BookProperty;
import interfaces.BLCDao;
import interfaces.PropertyEnum;
import jakarta.persistence.NoResultException;
import persistence.AuthorDAO;
import persistence.BookDAO;
import persistence.DAO;
import persistence.PublisherDAO;
import utils.Utils;

import java.util.List;
import java.util.Scanner;

public class BLCService<T> {

    private final BLCDao<T> DAO;
    private final Scanner sc;

    public BLCService(BLCDao<T> DAO) {
        this.DAO = DAO;
        sc = new Scanner(System.in);
    }

    public void printAll(List<T> objects) {
        try {
            int counter = 1;
            for (T object : objects) {
                System.out.println(object);
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

    public List<T> findByProperty (String name, List<PropertyEnum> enumValues, int limit) {

        System.out.println("Please choose a property to look for in the " + name);
        System.out.println();
        for (int i = 0; i < limit; i++) {
            PropertyEnum aux = enumValues.get(i);
            System.out.println(aux.getVal() + ". By " + aux.getPrettyName());
        }

        int op;
        do {
            op = Integer.parseInt(sc.nextLine());
            if (op < 1 || op > limit) {
                System.out.println("Option must be between 1 and " + limit);
            }
        } while (op < 1 || op > 5);

        PropertyEnum prop = enumValues.get(op - 1);

        System.out.println("Please insert the " + prop.getPrettyName() + " you want to search for");
        String value = sc.nextLine();

        try {
            List<T> objects = DAO.findByProperty(prop.getQueryName(), value);
            if (objects.isEmpty()) {
                throw new NoResultException("Couldn't find any book with the given property");
            }
            return objects;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return null;
    }

    public T selectObject (String name, List<PropertyEnum> enumValues, int limit) {
        System.out.println("Please choose the " + name + " you want to modify");
        List<T> objects = findByProperty(name, enumValues, limit);
        if (objects == null) {
            return null;
        }
        T selectedObject;
        if (objects.size() > 1) {
            System.out.println("These books where found, please choose one:");
            int counter = 1;
            for (T object : objects) {
                System.out.println(counter + ". " + object);
                counter++;
            }
            int op;
            do {
                op = Integer.parseInt(sc.nextLine());
                if (op < 1 || op > objects.size()) {
                    System.out.println("Invalid option, please try again");
                }
            } while (op < 1 || op > objects.size());
            selectedObject = objects.get(op - 1);
        } else {
            selectedObject = objects.get(0);
        }
        System.out.println("You selected " + selectedObject);
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
            return null;
        }

        return selectedObject;
    }

    public void deleteObject(String name, List<PropertyEnum> enumValues, int limit) {
        System.out.println("Please choose the " + name + " you want to delete");
        T object = selectObject(name, enumValues, limit);
        if (object == null) {
            return;
        }
        try {
            DAO.edit(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }
}
