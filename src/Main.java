import java.util.*;

class Pagina {

    public void loadPage(Scanner scanner){
        System.out.println("Page contents");
    }

    public void writeReview(Scanner scanner) {
        System.out.println("Writes review");
    }
}


class Lijst extends Pagina{
    public void loadPage(Scanner scanner){
        ReviewLoader reviewLoader = new ReviewLoader();
        HashMap<Double, String> reviews = reviewLoader.loadReviews();
        TreeMap<Double, String> tree = new TreeMap<>(Comparator.reverseOrder());
        tree.putAll(reviews);

        String line = "";

        for(String s : tree.values()){
            line += s + "\n";
        }

        System.out.println(line);
    }
}

class Review extends Pagina {

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

            line += ((gameplayScore + graphicsScore + storylineScore) / 3 ) + " ";

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

}

class Menu {

    ArrayList<Pagina> paginas;

    public Menu(ArrayList<Pagina> paginas){
        this.paginas = paginas;
    }

    public String showMenuKeuze(){
        return "1: Zie ranglijst\n2: Vul review in\n3: Ga naar uitverkoop\nVul het nummer in waar u heen wilt navigeren:\n";
    }

    public void handleMenuKeuze(Scanner scanner){
        int input = scanner.nextInt();

        if(input > 0 && input <= 3){
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

        Menu menu = new Menu(paginas);
        System.out.println(menu.showMenuKeuze());
        menu.handleMenuKeuze(scanner);
    }
}