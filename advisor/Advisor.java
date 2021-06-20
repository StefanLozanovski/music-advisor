package advisor;

import java.util.Scanner;

public class Advisor {

    Content content = new Content();
    Display printPage = new Display(Display.objectsPerPage);
    Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            String[] userInput = sc.nextLine().split(" ");
            if ("exit".equals(userInput[0])) {
                System.out.println("---GOODBYE!---" );
                return;
            } else if ("auth".equals(userInput[0]) || Authorization.isAuthorized) {
                menu(userInput);
            } else {
                System.out.println("Please, provide access for application.");
            }
        }
    }

    public void menu(String[] userInput) {
        switch (userInput[0]) {
            case "auth":
                Authorization.setAuth();
                break;
            case "new":
                printPage.printContent(content.getNewReleases());
                break;
            case "featured":
                printPage.printContent(content.getFeatured());
                break;
            case "categories":
                printPage.printContent(content.getCategories());
                break;
            case "playlists":
                StringBuilder category = new StringBuilder();
                for (int i = 1; i < userInput.length; i++) {
                    category.append(userInput[i]).append(" ");
                }
                printPage.printContent(content.getPlaylists(category.toString().trim()));
                break;
            case "next":
                printPage.printNext();
                break;
            case "prev":
                printPage.printPrev();
                break;
            case "exit":
                System.out.println("---GOODBYE!---");
                return;
            default:
                System.out.println("Unexpected value, try again.");
                break;
        }
    }
}