import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Page{
    public void loadPage(Scanner scanner){
        System.out.println("Loads the current page");
    }

    public String encodeReview(String review)
    {
        String encodedReview = review;
        encodedReview = encodedReview.replace("-", "@");
        encodedReview = encodedReview.replace("_", "-");
        encodedReview = encodedReview.replace(" ", "_");

        return encodedReview;
    }

    public String decodeReview(String encodedReview)
    {
        String decodedReview = encodedReview;

        decodedReview = decodedReview.replace("_", " ");
        decodedReview = decodedReview.replace("-", "_");
        decodedReview = decodedReview.replace("@", "-");

        return  decodedReview;
    }
}
class RankedPage extends Page {
    public void loadPage(Scanner scanner){
        //games inladen van gameloader
        GameLoader gameLoader = new GameLoader();
        //games als string[] in een nieuwe arraylist<String[]> stoppen
        ArrayList<String[]> gamesList = gameLoader.loadGames();

        //maakt nieuwe reviewLoaderOBJECT die REVIEWOBJECT terug geeft
        ReviewLoader reviewLoader = new ReviewLoader();
        //laad reviews in een nieuwe ArrayList<Review> die alleen review objecten aanneemt
        ArrayList<Review> reviews = reviewLoader.loadReviews();

        //maak een nieuwe arraylist die alleen GAME OBJECTEN mag innemen
        ArrayList<Game> rankedList = new ArrayList<>();

        for(String[] s : gamesList){
            //gameList array uitlezen als string en overzetten naar Game Object
            Game game = new Game(Integer.valueOf(s[0]), s[1], s[2], Double.valueOf(s[3]), Double.valueOf(s[4]));

            //door reviews kijken welke bij de current game horen
            for(Review r : reviews){
                //zo ja: voeg de review toe aan de ReviewArray in Game
                if(r.getName().equals(game.getTitle())){
                    game.addReview(r);
                }
            }
            //Game object met reviews toevoegen aan nieuwe array
            rankedList.add(game);
        }

        //Print alleen de games uit en filtert nog niet op totale score (FIX THIS ho)

        for(Game g : rankedList){
            System.out.printf("ID: %d\nTitle: %s\nGenre: %s\nPrice: %.2f\nDiscount: %.2f\n", g.getId(), g.getTitle(), g.getGenre(), g.getPrice(), g.getDiscountPrice());
            System.out.println();
            if(g.getReviews().size() == 0){
                System.out.printf(
                        "┌────────────────────────────────────┐\n" +
                                "│ No Reviews Available               │\n" +
                                "├────────────────────────────────────┤\n" +
                                "│ Game Title: [%s]             \n" +
                                "│                                    │\n" +
                                "│ Sorry, there are no reviews        │\n" +
                                "│ available for this game yet.       │\n" +
                                "│                                    │\n" +
                                "│ Be the first to share your         │\n" +
                                "│ experience and thoughts about      │\n" +
                                "│ this game!                         │\n" +
                                "└────────────────────────────────────┘\n", g.getTitle());
            }
            for(Review r : g.getReviews()){
                System.out.printf(
                        "┌────────────────────────────────────┐\n" +
                        "│ Review van [%s]             \n" +
                        "├────────────────────────────────────┤\n" +
                        "│ Gameplay Score: [%d]/10            │\n" +
                        "│ Graphics Score: [%d]/10            │\n" +
                        "│ Storyline Score: [%d]/10           │\n" +
                        "│                                    │\n" +
                        "│ Totale Score: [%.1f]/10 \n" +
                        "├────────────────────────────────────┤\n" +
                        "│ Toelichting:                       │\n" +
                        "│ [%s]\n" +
                        "└────────────────────────────────────┘\n", r.getName(), r.getGameplayScore(), r.getGraphicsScore(), r.getStorylineScore(), r.getTotalScore(), r.getTekstReview());
                System.out.println();
            }
            System.out.println();
        }
    }
}

class ReviewPage extends Page {
    public void loadPage(Scanner scanner){
        //games inladen van Class GameLoader
        GameLoader gameLoader = new GameLoader();
        //games in een nieuwe arraylist stoppen
        ArrayList<String[]> gamesList = gameLoader.loadGames();

        //user input opslaan
        System.out.println("Voor welke game wilt u een review schrijven?: \n");
        for(String[] s : gamesList){
            System.out.println(s[0] + ". " + s[1]);
        }
        int id = scanner.nextInt() - 1;


        System.out.println("Gameplay Score: \n");
        int gameplayScore = scanner.nextInt();
        System.out.println("Graphics Score: \n");
        int graphicsScore = scanner.nextInt();
        System.out.println("Storyline Score: \n");
        int storylineScore = scanner.nextInt();

        double res = (gameplayScore + graphicsScore + storylineScore) / 3;

        //haalt de invisible nextInt line weg
        scanner.nextLine();

        System.out.println("Beschrijving: ");
        String beschrijving = scanner.nextLine();
        beschrijving = encodeReview(beschrijving);

        //User input opslaan klaarrrrr!

        //alle input in een Review object stoppen
        //koalo nu hebben we een review obect gemaakt van een String
        // maar nu moeten van het object weer terug naar een string dus we hadden het object eig kunnen overslaan
        Review review = new Review(id, gamesList.get(id)[1], gameplayScore, graphicsScore, storylineScore, res, beschrijving);

        //review object aan ReviewLoader geven
        ReviewLoader reviewLoader = new ReviewLoader();
        reviewLoader.writeReview(review);

    }

}

class UitverkoopPage extends Page {
    public void loadPage(Scanner scanner){
        GameLoader gameLoader = new GameLoader();
        ArrayList<String[]> games = gameLoader.loadGames();
        System.out.println("Lijst van afgeprijsde games: \n");
        for(String[] game : games){
            if(Double.valueOf(game[4]) - Double.valueOf(game[3]) == 0.0){
                System.out.printf("%s | Prijs: $%s > Free!%n", game[1], ("\u001B[9m" + game[3] + "\u001B[0m"));
            }
            else if(Double.valueOf(game[4]) < Double.valueOf(game[3]) && Double.valueOf(game[4]) != 0){
                System.out.printf("%s | Prijs: $%s > $%.2f%n", game[1], ("\u001B[9m" + game[3] + "\u001B[0m"), (Double.valueOf(game[3]) - Double.valueOf(game[4])));
            }
        }
    }
}

public class AppMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    RankedPage rankedPage = new RankedPage();
                    rankedPage.loadPage(scanner);
                    break;
                case 2:
                    ReviewPage reviewPage = new ReviewPage();
                    reviewPage.loadPage(scanner);
                    break;
                case 3:
                    UitverkoopPage uitverkoopPage = new UitverkoopPage();
                    uitverkoopPage.loadPage(scanner);
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Sluiten...");
                    return;
                default:
                    System.out.println("Verkeerde keuze. Probeer opnieuw");
                    break;
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Ranglijst");
        System.out.println("2. Review Schrijven");
        System.out.println("3. Uitverkooplijst");
        System.out.println("4. AdminLogin");
        System.out.println("5. Sluiten");
        System.out.print("Kies een optie: \n");
    }
}
