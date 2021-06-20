package advisor;

public class Display {

    static int objectsPerPage = 5;
    int totalPages;
    int currentPage;
    int startObject;
    int endObject;
    String[] output;

    public Display(int onPage) {
        objectsPerPage = onPage;
    }

    void printContent(String data) {
        currentPage = 0;
        startObject = 0;
        endObject = 0;
        output = data.split("\n\n");
        
        if (output.length % objectsPerPage > 0) {
            totalPages = output.length / objectsPerPage + 1;
        } else {
            totalPages = output.length / objectsPerPage;
        }
        currentPage = 1;
        for (int i = 0; i < objectsPerPage; i++) {
            System.out.println(output[i]);
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
    }

    void printNext(){
        if (currentPage + 1 < totalPages) {
            startObject = objectsPerPage * currentPage;
            endObject = startObject + objectsPerPage;
        } else if (currentPage + 1 == totalPages) {
            startObject = objectsPerPage * currentPage;
            if (output.length % objectsPerPage > 0) {
                endObject = startObject + output.length % objectsPerPage;
            } else {
                endObject = startObject + objectsPerPage;
            }
        } else {
            System.out.println("No more pages.");
            return;
        }
        for (int i = startObject ; i < endObject; i++) {
            System.out.println(output[i] + "\n");
        }
        currentPage++;
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
    }

    void printPrev() {
        currentPage--;
        if (currentPage > 0) {
            startObject = startObject - objectsPerPage;
            endObject = startObject + objectsPerPage;
            for (int i = startObject; i < endObject; i++) {
                System.out.println(output[i] + "\n");
            }
            System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
        } else {
            currentPage++;
            System.out.println("No more pages.");
        }
    }
}