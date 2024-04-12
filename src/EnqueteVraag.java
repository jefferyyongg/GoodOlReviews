import java.util.ArrayList;
import java.util.Scanner;

public class EnqueteVraag {
    protected String vraag;
    protected String[] antwoorden;
    protected EnqueteVraag dependency;
    protected ArrayList<String> dependency_antwoorden;
    protected String antwoordGegeven;


    public EnqueteVraag(String vraag)
    {
        this.vraag = vraag;
    }

    public EnqueteVraag(String vraag, String[] antwoorden)
    {
        this.vraag = vraag;
        this.antwoorden = antwoorden;
    }

    public EnqueteVraag(String vraag, EnqueteVraag dependency, ArrayList<String> dependency_antwoorden)
    {
        this.vraag = vraag;
        this.dependency = dependency;
        this.dependency_antwoorden = dependency_antwoorden;
    }

    public EnqueteVraag(String vraag, String[] antwoorden, EnqueteVraag dependency, ArrayList<String> dependency_antwoorden)
    {
        this.vraag = vraag;
        this.antwoorden = antwoorden;
        this.dependency = dependency;
        this.dependency_antwoorden = dependency_antwoorden;
    }

    public void setAntwoordGegeven(String antwoordGegeven)
    {
        this.antwoordGegeven = antwoordGegeven;
    }

    public String getAntwoordGegeven()
    {
        return this.antwoordGegeven;
    }

    public String stelVraag() {
        String antwoordMogelijkheden = "\n";
        int index = 1;
        for(String antwoord : this.antwoorden)
        {
            antwoordMogelijkheden += index + ". " + antwoord + "\n";
            index++;
        }

        return this.vraag + antwoordMogelijkheden;
    }


    public boolean wachtOpAntwoord()
    {
        Scanner scanner = new Scanner(System.in);
        int antwoord = scanner.nextInt();
        scanner.nextLine();
        if(antwoord < 0 || antwoord > this.antwoorden.length){
            System.out.println("Kies een geldig antwoord!");
            return false;
        }
        this.antwoordGegeven = String.valueOf(antwoord);
        return true;
    }

    public String getVraag()
    {
        return this.vraag;
    }

    public String formatAntwoordGegeven()
    {
        return this.antwoorden[Integer.parseInt(this.antwoordGegeven) - 1];
    }
}
