import java.text.DecimalFormat;
import java.lang.Math;
import java.util.*;

class Pagina {

    public void loadPage(Scanner scanner){
        System.out.println("Load page content with user input");
    }

    public void writeReview(Scanner scanner) {
        System.out.println("Writes review");
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


class Lijst extends Pagina{
    public void loadPage(Scanner scanner){
        GameLoader gameLoader = new GameLoader();
        ArrayList<String[]> games = gameLoader.loadGames();

        ReviewLoader reviewLoader = new ReviewLoader();
        HashMap<Double, String> reviews = reviewLoader.loadReviews();
        TreeMap<Double, String> tree = new TreeMap<>(Comparator.reverseOrder());
        tree.putAll(reviews);

        String line = "";
        int rank = 1;

        for(String s : tree.values()){
            for(String[] game : games){
                if(game[1].equals(s)){
                    line +=  rank  + ". " + s + " | Prijs: $" + game[3] + "\n";
                }
            }
            rank++;
        }

        System.out.println(line);
    }
}

class Review extends Pagina {

    public void loadPage (Scanner scanner){
        GameLoader gameLoader = new GameLoader();
        ArrayList<String[]> games = gameLoader.loadGames();

        System.out.println("Welke game wilt u reviewen?: \n");
        for(String[] game : games){
            System.out.println(game[0] + ". " + game[1]);
        }
        int input = scanner.nextInt();
        int gameplayScore = 0, graphicsScore = 0, storylineScore = 0;
        String line = "";
        line += input + " ";

        if(input > 0 && input <= games.size()){
            System.out.println("Review van [" + games.get(input - 1)[1] + "]");
            line += games.get(input - 1)[1] + " ";
            System.out.println("Gameplay Score:");
            gameplayScore = scanner.nextInt();
            line += gameplayScore  + " ";
            System.out.println("Graphics Score:");
            graphicsScore = scanner.nextInt();
            line += graphicsScore  + " ";
            System.out.println("StoryLine Score:");
            storylineScore = scanner.nextInt();
            line += storylineScore + " ";

            line += ((gameplayScore + graphicsScore + storylineScore) / 3) + " ";

            String beschrijving;
            System.out.printf("Beschrijving: ");
            //gets rid of int Line
            scanner.nextLine();
            beschrijving = scanner.nextLine();

            beschrijving = this.encodeReview(beschrijving);
            line += beschrijving;

            System.out.println("Review Voltooid!");

            ReviewLoader reviewLoader = new ReviewLoader();
            reviewLoader.writeReview(line);
        } else {
            System.out.println("Ongeldig Nummer");
        }
    }
}

class Uitverkoop extends Pagina {

    public void loadPage(Scanner scanner){
        GameLoader gameLoader = new GameLoader();
        ArrayList<String[]> games = gameLoader.loadGames();
        System.out.println("Lijst van afgeprijsde games: \n");
        String originalText = "";
        String strikethroughText = "\u001B[9m" + originalText + "\u001B[0m";
        for(String[] game : games){
            if(Double.valueOf(game[4]) < Double.valueOf(game[3])){
                System.out.printf("Game: %s | Prijs: $%s > $%.2f%n", game[1], ("\u001B[9m" + game[3] + "\u001B[0m"), (Double.valueOf(game[3]) - Double.valueOf(game[4])));
            }
        }
    }
}

class AddGame extends Pagina{
    public void loadPage(Scanner scanner){
        //gets rid of fake lineeee
        scanner.nextLine();
        System.out.println("Game Titel: ");
        String titel = scanner.nextLine();
        titel = this.encodeReview(titel);
        System.out.println("Genre: ");
        String genre = scanner.nextLine();
        System.out.println("Prijs: ");
        double prijs = Double.valueOf(scanner.nextLine());
        System.out.println("Korting: ");
        double korting = Double.valueOf(scanner.nextLine());

        GameLoader gameLoader = new GameLoader();
        ArrayList<String[]> games = gameLoader.loadGames();

        String line = "";
        line += games.size() + 1 + " ";
        line += titel + " ";
        line += genre + " ";
        line += prijs + " ";
        line += korting;

        gameLoader.addGame(line);

        System.out.println("Game Toegevoegd!");
    }
}

class Menu {

    ArrayList<Pagina> paginas;

    public Menu(ArrayList<Pagina> paginas){
        this.paginas = paginas;
    }

    public String showMenuKeuze(){
        return "1: Zie ranglijst\n2: Vul review in\n3: Ga naar uitverkoop\n4: Game toevoegen\nVul het nummer in waar u heen wilt navigeren:\n";
    }

    public void handleMenuKeuze(Scanner scanner){
        int input = scanner.nextInt();

        if(input > 0 && input <= 4){
            Pagina pagina = paginas.get(input - 1);
            pagina.loadPage(scanner);
        } else {
            System.out.println("Ongeldig Paginanummer");
        }
    }

}

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ArrayList<Pagina> paginas = new ArrayList<>();
        paginas.add(new Lijst());
        paginas.add(new Review());
        paginas.add(new Uitverkoop());
        paginas.add(new AddGame());

        Menu menu = new Menu(paginas);
        System.out.println(menu.showMenuKeuze());
        menu.handleMenuKeuze(scanner);
    }
}