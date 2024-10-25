package org.example.front_client_exam_1.remote_client;

import org.example.front_client_exam_1.model.CD;
import org.example.front_client_exam_1.model.EjbStateful;
import org.example.front_client_exam_1.model.EjbStateless;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class remote_EJB_client {
    private static EjbStateless ejbStateless;
    private static EjbStateful ejbStateful;

    public static void main(String[] args) {
        try {
            // Initialize JNDI context and lookup EJBs
            Context context = new InitialContext(getJNDIProperties());
            ejbStateless = (EjbStateless) context.lookup("ejb:/hello_world_ejb_2/EjbStatelessBean!org.example.hello_world_ejb_2.beans.EjbStateless");
            ejbStateful = (EjbStateful) context.lookup("ejb:/hello_world_ejb_2/EjbStatefulBean!org.example.hello_world_ejb_2.beans.EjbStateful");

            // Start client interface
            startClientInterface();

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    // JNDI Properties setup
    private static Properties getJNDIProperties() {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        jndiProps.put(Context.SECURITY_PRINCIPAL, "your_username");
        jndiProps.put(Context.SECURITY_CREDENTIALS, "your_password");
        return jndiProps;
    }

    // Client Interface
    private static void startClientInterface() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. View Available CDs");
            System.out.println("2. Borrow a CD");
            System.out.println("3. Return a CD");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> viewAvailableCDs();
                case 2 -> borrowCD(scanner);
                case 3 -> returnCD(scanner);
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("erreur, try again.");
            }
        }
    }

    private static void viewAvailableCDs() {
        List<CD> cds = ejbStateful.getCDs();
        System.out.println("\nCDs available:");
        cds.forEach(cd -> System.out.println(cd.getTitle() + " by " + cd.getArtist() + " - Available: " + cd.isAvailable()));
    }

    private static void borrowCD(Scanner scanner) {
        System.out.print("\nTitre du CD: ");
        String title = scanner.nextLine();
        System.out.print("Artiste: ");
        String artist = scanner.nextLine();
        ejbStateless.pretCD(title, artist);
        System.out.println("CD a emprunter : " + title + " by " + artist);
    }

    private static void returnCD(Scanner scanner) {
        System.out.print("\nTitre du CD: ");
        String title = scanner.nextLine();
        System.out.print("Artiste: ");
        String artist = scanner.nextLine();
        ejbStateless.retourCD(title, artist);
        System.out.println("CD a retourner: " + title + " by " + artist);
    }
}
