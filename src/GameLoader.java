
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameLoader {

    public void setGamePrice(ArrayList<String[]> games){
        try {
            //ipv append op false laten en het gehele text bestand herschrijven ipv appenden
            FileWriter writer = new FileWriter("/Users/jefferyyong/IdeaProjects/GoodOlGames/src/games.txt");

            writer.append("Id Naam Genre Prijs Kortingsprijs\n");
            for(String[] s : games){
                String line = "";
                line = s[0] + " " + s[1] + " " + s[2] + " " + s[3] + " " + s[4];
                writer.append(line + "\n");
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeGame(Game g){
        try {
            FileWriter writer = new FileWriter("/Users/jefferyyong/IdeaProjects/GoodOlGames/src/games.txt", true);

            String line = (loadGames().size() + 1) + " " + g.getTitle() + " " + g.getGenre() + " " + g.getPrice() + " " + g.getDiscountPrice();
            writer.append(line + "\n");
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public ArrayList<String[]> loadGames()
    {
        //Games inladen (sorteeroptie / filteroptie later hier inbouwen)
        return this.loadFile("/Users/jefferyyong/IdeaProjects/GoodOlGames/src/games.txt");
    }

    public ArrayList<String[]> loadFile(String fileName)
    {
        String[] parts = new String[4];
        ArrayList<String[]> gameLines = new ArrayList<>();
        try {
            // Bestandspad openen en opslaan om later het bestand te openen
            Path filePath = Paths.get(fileName);

            //Bestand uitlezen
            List<String> lines = Files.readAllLines(filePath);

            // Elke regel toevoegen aan de games-lijst. Eerste overslaan (header)
            String line;
            for (int i = 1; i < lines.size(); i++) {
                line = lines.get(i);
                parts = line.split(" ");

                gameLines.add(parts);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameLines;
    }
}
