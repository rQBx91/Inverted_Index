import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        HashMap<String, String > resources = InputResources.loadResources("./resources/books/");

        System.out.println("Loaded resources from specified path.");

        DocumentStore store = new DocumentStore();
        InvertedIndex index = new InvertedIndex();

        for (String name : resources.keySet()) {
            Document document = new Document(name,resources.get(name));
            store.add(document);
            index.add(document);
        }
        System.out.println("Created index and doc-store from resources.");

        if(index.saveIndex("./resources/index.txt"))
            System.out.println("Saved index to file.");

        while (true){
            System.out.print("Enter your query: ");
            Scanner scanner = new Scanner(System.in);
            String query = scanner.nextLine().toLowerCase();
            if (query.equals("exit")){
                return;
            }
            try {
                PostingList list = index.get(query);
                for (Integer docId : list.getDocIds()) {
                    System.out.println(store.get(docId));
                }
                System.out.println();
            }
            catch (NullPointerException ex) {
                System.out.println("Couldn't find any result.");
            }
        }


    }
}
