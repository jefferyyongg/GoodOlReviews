import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class GameLoader {
    public ArrayList<String[]> loadGames(){
        return loadFile("/Users/jefferyyong/IdeaProjects/GoodOlGames/src/games.txt");
    }

    public ArrayList<String[]> loadFile(String filePath){
        String[] parts = new String[5];
        ArrayList<String[]> gameLines = new ArrayList<>();

        try{
            Path path = Paths.get(filePath);

            List<String> lines = Files.readAllLines(path);

            String line;

            for(int i = 1; i < lines.size(); i++){
                parts = lines.get(i).split(" ");

                gameLines.add(parts);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return gameLines;
    }
}
