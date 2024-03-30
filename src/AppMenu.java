import java.util.*;

import static java.util.Map.entry;

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

        //voegt alle reviews toe aan de juiste assigned game
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

        //uhhh weet niet precies hoe dit werkt maar het sort de arraylist op basis van een object Field, in dit geval TotalGameScore(https://www.bezkoder.com/java-sort-arraylist-of-objects/)
        Collections.sort(rankedList, Comparator.comparing(Game::getTotalGameScore));
        //sorteert op laagste boven dus hier reverse ik de list
        Collections.reverse(rankedList);

        //loops door filtered list en doet wat simple system.out.printf's
        for(Game g : rankedList){
            System.out.printf("ID: %d\nTitle: %s\nGenre: %s\nTotal Score: %.2f\nPrice: %.2f\nDiscount: %.2f\n", g.getId(), g.getTitle(), g.getGenre(), g.getTotalGameScore(), g.getPrice(), g.getDiscountPrice());
            System.out.println();
            //print "no reviews available" als er geen reviews zijn gevonden
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
        int id = scanner.nextInt();


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

        //Enquete functionaliteit
        //vragen of de gebruiker enquete wilt invullen
        System.out.println("Enquête invullen?(y/n): \n");
        String enqueteKeuze = scanner.nextLine();
        //enquete vragen toevoegen met bijhorende antwoorden in een hashmap
        HashMap<String, String[]> enqueteVragen = new HashMap<>();
        enqueteVragen.put("Hoe vaak bezoekt u onze winkel (of website)?", new String[]{"Dagelijks", "Wekelijks", "Maandelijks"});
        enqueteVragen.put("Welk type games koopt u meestal bij Good ol’ Games?", new String[]{"RPG", "Puzzel", "Strategie", "Avontuur", "Actie"});
        enqueteVragen.put("Wat is voor u het belangrijkste criterium bij het kiezen van een retro-game?", new String[]{"Gameplay", "Graphics", "StoryLine", "Prijs", "Aanbevelingen/Ranglijst"});
        enqueteVragen.put("Hoe waarschijnlijk is het dat u Good ol’ Games zou aanbevelen aan vrienden of familie?", new String[]{"Zeer onwaarschijnlijk", "Onwaarschijnlijk", "Neutraal", "Waarschijnlijk", "Zeer waarschijnlijk"});
        enqueteVragen.put("Heeft u suggesties voor het verbeteren van onze winkel/website?", new String[]{"Open Vraag: \n"});
        enqueteVragen.put("Hoe belangrijk is de prijs van een game bij uw beslissing om te kopen?", new String[]{"Zeer onbelangrijk", "Onbelangrijk", "Neutraal", "Belangrijk", "Zeer belangrijk"});
        enqueteVragen.put("Hoe tevreden bent u over de algemene sfeer en ervaring van de Good ol’ Games winkel/website?", new String[]{"Zeer ontevreden", "Ontevreden", "Neutraal", "Tevreden", "Zeer tevreden"});

        //hashmap om vragen met bijhorende antwoorden op te slaan
        HashMap<String, String> enqueteAntwoorden = new HashMap<>();
        //while loop om enquete vragen te stellen
        while(true){
            if(enqueteKeuze.equals("y")){
                //door enquetevragen hashmap loopen en elke vraag met bijhorende antwoorden terug geven
                for(String k : enqueteVragen.keySet()){
                    System.out.println(k);
                    for(String v : enqueteVragen.get(k)){
                        System.out.println(v);
                    }
                    String input = scanner.nextLine();
                    //vraag met user input opslaan in EnqueteAntwoorden
                    enqueteAntwoorden.put(k, input);
                }
                //EnqueteAntwoorden doorgeven na de loop
                EnqueteLoader enqueteLoader = new EnqueteLoader();
                enqueteLoader.writeEnquete(enqueteAntwoorden);
                System.out.println("Enquête Voltooid!");
                break;
            } else if (enqueteKeuze.equals("n")){
                break;
            } else {
                System.out.println("Ongeldige Keuze");
            }
        }

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

class AdminPage extends Page {
    public void loadPage(Scanner scanner){
        System.out.println("Enter Username:\n");
        String user = scanner.nextLine();
        System.out.println("Enter Password:\n");
        String pass = scanner.nextLine();

        //import gameloader voor functionaliteiten je snapt het wel
        GameLoader gameLoader = new GameLoader();
        //dit snap je wel
        if(user.equals("Admin") && pass.equals("Admin")){
            System.out.println("Admin Menu:\n1. Game toevoegen\n2. Game verwijderen\n3. Prijzen aanpassen");
            String adminKeuze = scanner.nextLine();
            if(adminKeuze.equals("1")){
                System.out.println("Game Title: \n");
                String title = scanner.nextLine();
                title = encodeReview(title);
                System.out.println("Genre: \n");
                String genre = scanner.nextLine();
                System.out.println("Prijs: \n");
                String prijs = scanner.nextLine();
                System.out.println("Kortingsprijs: \n");
                String korting = scanner.nextLine();
                //user input pakken en in een nieuwe game object stoppen
                Game g = new Game(title, genre, Double.valueOf(prijs), Double.valueOf(korting));
                //die game object doorgeven aan gameloader object die het vervolgend append aan het games.txt text bestand
                gameLoader.writeGame(g);
            } else if(adminKeuze.equals("2")){
                ArrayList<String[]> games = gameLoader.loadGames();
                System.out.println("Welke game wilt u verwijderen?");
                for(String[] s : games){
                    System.out.printf("%s. %s\n", s[0], s[1]);
                }
                String verwijderInput = scanner.nextLine();
                //verwijderd game met index de user input
                games.remove(Integer.valueOf(verwijderInput) - 1);
                //geeft games lijst door aan gameloader
                gameLoader.removeGame(games);
            } else if(adminKeuze.equals("3")){
                //game list pakken uit gameloader
                ArrayList<String[]> games = gameLoader.loadGames();
                //user input pakken bla bla bla...
                System.out.println("Welke game prijs wilt u aanpassen?:\n");
                String input = scanner.nextLine();
                //games showen plus ID(index) voor de user om te selecteren
                for(String[] s : games){
                    System.out.printf("%s. %s Prijs: %s\n", s[0], s[1], s[3]);
                }
                //user input voor nieuwe prijs zZzZzZzZzZz....
                System.out.println("Nieuwe Prijs?: \n");
                String newPriceInput = scanner.nextLine();
                for(String[] s : games){
                    //prijs veranderen in games lijst
                    if(s[0].equals(input)){
                        s[3] = newPriceInput;
                    }
                }
                //lijst doorgeven je weet het at this point wel doei 👋👋👋
                gameLoader.setGamePrice(games);
            }else {
                System.out.println("Ongeldige login gegevens.");
            }
        } else {
            System.out.println("Ongeldige login gegevens.");
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
                    AdminPage adminPage = new AdminPage();
                    adminPage.loadPage(scanner);
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
