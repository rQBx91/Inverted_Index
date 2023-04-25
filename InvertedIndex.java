import java.io.*;
import java.util.*;



public class InvertedIndex {

    private HashMap<String, PostingList> table = new HashMap<>();

    public void add(Document document) {
        String delim = "\s\n\r\t\"\\.'!~`.?@#$&,()_^*:;<>+=-%“—0123456789/[]{}";
        String message =  document.getDocBody();
        StringTokenizer st = new StringTokenizer(message, delim);
        ArrayList<String> tokens = new ArrayList<>();
        while(st.hasMoreTokens()){
            tokens.add(st.nextToken());
        }
        Set<String> distinctTokens = new HashSet<>(tokens);

        for (String token : distinctTokens) {
            if (token.equals("")) continue;
            table.putIfAbsent(token, new PostingList());
            table.get(token).add(document.getDocId());
        }

        for (PostingList list : table.values()) {
            list.sort();
        }

    }

    public PostingList get(String token) {
        return table.get(token);
    }

    public boolean saveIndex(String path) throws IOException {
        File file = new File(path);
        if(file.exists()){
            System.out.println("can not save to specified path: such file exists.");
            return false;
        }

        BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
        for (Map.Entry<String, PostingList> entry : table.entrySet()) {
            buffer.write(entry.getKey() + ":" + entry.getValue());
            buffer.newLine();
        }
        buffer.close();
        return true;
    }

    public boolean loadIndex(String path) throws IOException {
        File file = new File(path);
        BufferedReader buffer = new BufferedReader(new FileReader(file));

        String line ;
        HashMap<String, PostingList> map = new HashMap<>();

        while ((line = buffer.readLine()) != null) {

            // split the line by :
            String[] parts = line.split(":");

            // first part is name, second is number
            String key = parts[0].trim();
            String[] values = parts[1].split("\\s+");
            for(int i = 0; i < values.length; i++){
                values[i] = values[i].replaceAll("[^0-9]", "");
            }

            PostingList post = new PostingList();
            for (String value : values) {
                if(value.equals("")) continue;
                post.add(Integer.parseInt(value));
            }

             if (!key.equals(""))
                map.put(key, post);
        }

        table = map;
        return true;

    }

}
