import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class InputResources {
    public static HashMap<String, String> loadResources(String path) throws IOException {

        HashMap<String, String > result = new HashMap<>();
        ArrayList<String> fileNames = getFileNames(path);

        for (String name:fileNames){
            File readFile = new File(path + "/" + name);
            Scanner fileStream = new Scanner(readFile);
            StringBuilder fileContent = new StringBuilder();
            while (fileStream.hasNextLine()){
                fileContent.append(fileStream.nextLine());
            }
            fileStream.close();
            result.put(name, fileContent.toString().toLowerCase());
        }

        return result;

    }

    private static ArrayList<String> getFileNames(String path) {

        ArrayList<String> results = new ArrayList<>();
        File folder = new File(path);
        File[] files = folder.listFiles();

        assert files != null;
        for (File file:files){
            if (file.isFile()){
                results.add(file.getName());
            }
        }

        return results;
    }

}
