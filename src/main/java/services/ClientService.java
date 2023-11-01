package services;

import entities.Author;
import entities.Book;
import entities.Client;
import entities.Publisher;
import enums.ClientProperty;
import jakarta.persistence.NoResultException;
import persistence.ClientDAO;
import utils.Utils;

import java.util.List;
import java.util.Scanner;

public class ClientService {

    private final ClientDAO clientDAO;

    private final Scanner sc;

    public ClientService() {
        clientDAO = new ClientDAO();
        sc = new Scanner(System.in);
    }

    public void createClient() {
        try {
            List<Client> currentClients = clientDAO.findAll();

            System.out.println("Please enter the Client's data");

            List<String> documents = currentClients.stream().map((client) -> client.getDocument().toString()).toList();
            System.out.print("Document: ");
            String aux = sc.nextLine();
            Utils.checkEmptyString(aux);
            Utils.checkRepeatedValue(documents, aux);
            Long document = Long.parseLong(aux);

            System.out.print("First Name: ");
            String firstName = sc.nextLine();
            Utils.checkEmptyString(firstName);

            System.out.print("Last Name: ");
            String lastName = sc.nextLine();
            Utils.checkEmptyString(lastName);

            System.out.println("Phone: ");
            String phone = sc.nextLine();
            Utils.checkEmptyString(phone);

            Client client = new Client(document, firstName, lastName, phone);
            clientDAO.save(client);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void printAllClients() {
        try {
            List<Client> clients = clientDAO.findAll();
            int counter = 1;
            for (Client client : clients) {
                System.out.println(client);
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

    public List<Client> findByProperty() {
        System.out.println("Please choose a property to look for in the client");
        System.out.println();
        for (int i = 0; i < 4; i++) {
            ClientProperty aux = ClientProperty.values()[i];
            System.out.println(aux.getVal() + ". By " + aux.getPrettyName());
        }

        int op;
        do {
            op = Integer.parseInt(sc.nextLine());
            if (op < 1 || op > 4) {
                System.out.println("Option must be between 1 and 4");
            }
        } while (op < 1 || op > 5);

        ClientProperty prop = ClientProperty.values()[op - 1];

        System.out.println("Please insert the " + prop.getPrettyName() + " you want to search for");
        String value = sc.nextLine();

        try {
            List<Client> clients = clientDAO.findByProperty(prop.getQueryName(), value);
            if (clients.isEmpty()) {
                throw new NoResultException("Couldn't find any client with the given property");
            }
            return clients;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return null;
    }

    public void modifyClient() {
        System.out.println("Please choose the client you want to modify");
        List<Client> clients = findByProperty();
        if (clients == null) {
            return;
        }
        Client selectedClient;
        if (clients.size() > 1) {
            System.out.println("These clients where found, please choose one:");
            int counter = 1;
            for (Client client : clients) {
                System.out.println(counter + ". " + client);
                counter++;
            }
            int op;
            do {
                op = Integer.parseInt(sc.nextLine());
                if (op < 1 || op > clients.size()) {
                    System.out.println("Invalid option, please try again");
                }
            } while (op < 1 || op > clients.size());
            selectedClient = clients.get(op - 1);
        } else {
            selectedClient = clients.get(0);
        }
        System.out.println("You selected " + selectedClient);
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
