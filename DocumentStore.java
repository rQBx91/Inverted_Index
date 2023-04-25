
import java.util.Collection;
import java.util.HashMap;

public class DocumentStore {

    private HashMap<Integer, Document> allDocs = new HashMap<>();

    public void add(Document doc){
        allDocs.put(doc.getDocId(), doc);
    }

    public Document get(int id){
        return allDocs.get(id);
    }

    public Collection<Document> getAll(){
        return allDocs.values();
    }

    public Integer[] getAllIds(){
        Integer[] all = new Integer[allDocs.size()];
        int i = 0;
        for (Integer key:allDocs.keySet()){
            all[i] = key;
            i++;
        }
        return all;
    }

}
