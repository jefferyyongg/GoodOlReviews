import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class EnqueteLoader {

    private String enqueteFile = "enquete.txt";
    public void writeEnquete(Enquete enquete){
        try {
            FileWriter writer = new FileWriter(enqueteFile, true);
            for(EnqueteVraag ev : enquete.getVragen()){
                if(ev.getAntwoordGegeven() != null) {
                    writer.append(ev.getVraag() + " : " + ev.formatAntwoordGegeven() + "\n");
                }
            }
            writer.append("\n");
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
