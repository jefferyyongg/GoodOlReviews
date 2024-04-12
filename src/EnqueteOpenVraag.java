import java.util.ArrayList;
import java.util.Scanner;

public class EnqueteOpenVraag extends EnqueteVraag{
    public EnqueteOpenVraag(String vraag, EnqueteVraag dependency, ArrayList<String> dependency_antwoorden)
    {
        super(vraag, dependency, dependency_antwoorden);
    }

    public EnqueteOpenVraag(String vraag)
    {
        super(vraag);
    }

    @Override
    public String stelVraag()
    {
        return this.vraag;
    }

    @Override
    public boolean wachtOpAntwoord()
    {
        Scanner scanner = new Scanner(System.in);
        String antwoordGegeven = scanner.nextLine();
        this.antwoordGegeven = antwoordGegeven;
        System.out.println("\n");
        return true;
    }

    @Override
    public String formatAntwoordGegeven()
    {
        return this.getAntwoordGegeven();
    }
}
