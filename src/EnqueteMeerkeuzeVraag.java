import java.util.ArrayList;

public class EnqueteMeerkeuzeVraag extends EnqueteVraag{
    public EnqueteMeerkeuzeVraag(String vraag, String[] antwoorden, EnqueteVraag dependency, ArrayList<String> dependency_antwoorden)
    {
        super(vraag, antwoorden, dependency, dependency_antwoorden);
    }

    public EnqueteMeerkeuzeVraag(String vraag, String[] antwoorden)
    {
        super(vraag, antwoorden);
    }
}
