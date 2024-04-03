import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class EnqueteLoader {

    private String enqueteFile = "enquete.txt";
    public void writeEnquete(HashMap<String, String> h){
        try {
            FileWriter writer = new FileWriter(enqueteFile, true);
            for(String k : h.keySet()){
                writer.append(k + "\nAntwoord: " + h.get(k) + "\n");
            }
            writer.append("\n");
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
