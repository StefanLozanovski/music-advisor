package advisor;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        if (Arrays.asList(args).contains("-access")) {
            Authorization.AUTH_SERVER = args[Arrays.asList(args).indexOf("-access") + 1];
            Authorization.API_SERVER = args[Arrays.asList(args).indexOf("-resource") + 1];
            Display.objectsPerPage = Integer.parseInt(args[Arrays.asList(args).indexOf("-page") + 1]);
        } else {
            System.out.println("Invalid arguments, try again.");
            System.exit(1);
        }
        Advisor advisor = new Advisor();
        advisor.start();
    }
}