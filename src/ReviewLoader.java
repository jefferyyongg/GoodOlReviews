import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class ReviewLoader {

    public void writeReview(String line){
        try {
            FileWriter writer = new FileWriter("/Users/jefferyyong/IdeaProjects/GoodOlGames/src/reviews.txt", true);
            writer.append(" \n" + line);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public HashMap<Double, String> loadReviews(){
        return loadFile("/Users/jefferyyong/IdeaProjects/GoodOlGames/src/reviews.txt");
    }

    public HashMap<Double, String> loadFile(String filePath){
        String[] parts = new String[7];
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

        GameLoader gameLoader = new GameLoader();
        ArrayList<String[]> games = gameLoader.loadGames();
        HashMap<Double, String> hash = new HashMap<>();

        for(int i = 0; i < games.size(); i++){
            double res = 0;
            for(int j = 0; j < gameLines.size(); j++){
                if(gameLines.get(j)[0].equals(games.get(i)[0])){
                    res += Integer.valueOf(gameLines.get(j)[5]);
                }
            }
            hash.put(res, games.get(i)[1]);
        }

        return hash;
    }
}
