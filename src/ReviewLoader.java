
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReviewLoader {

    private String reviewsFile = "reviews.txt";

    public void deleteReview(ArrayList<Review> reviews){
        try{
            FileWriter writer = new FileWriter(reviewsFile);
            writer.append("Id, Naam, Gameplay, Graphics, Storyline, Totaal, Beschrijving\n");

            for(Review r : reviews){
                writer.append(r.getId() + " " + r.getName() + " " + r.getGameplayScore() + " " + r.getGraphicsScore() + " " + r.getStorylineScore() + " " + r.getTotalScore() + " " + r.getTekstReview() + "\n");
            }

            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void writeReview(Review review){
        try {
            FileWriter writer = new FileWriter(reviewsFile, true);

            //KOALO OBJECT TERUG NAAR KK STRING CONVERTEN LMAOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
            String line = review.getId() + " " + review.getName() + " " + review.getGameplayScore() + " " + review.getGraphicsScore() + " " + review.getStorylineScore() + " " + review.getTotalScore() + " " + review.getTekstReview();
            writer.append(line + "\n");
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Review> loadReviews()
    {
        //Games inladen (sorteeroptie / filteroptie later hier inbouwen)
        return this.loadFile(reviewsFile);
    }

    public ArrayList<Review> loadFile(String fileName)
    {
        String[] parts = new String[7];
        ArrayList<Review> reviews = new ArrayList<>();
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

                Review review = new Review(Integer.valueOf(parts[0]), parts[1], Integer.valueOf(parts[2]), Integer.valueOf(parts[3]), Integer.valueOf(parts[4]), Double.valueOf(parts[5]), parts[6]);
                reviews.add(review);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
