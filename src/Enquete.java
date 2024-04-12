import java.util.*;

public class Enquete {
    private ArrayList<EnqueteVraag> vragen = new ArrayList<EnqueteVraag>();
    private EnqueteLoader enqueteLoader = new EnqueteLoader();

    public void loadVragen()
    {
        EnqueteMeerkeuzeVraag vraag1 = new EnqueteMeerkeuzeVraag("Hoe vaak bezoekt u onze winkel (of website)?", new String[]{"Dagelijks", "Wekelijks", "Maandelijks"});

        //Vraag 2 heeft een dependency
        ArrayList<String> vraag2_depAnswers = new ArrayList<String>();
        vraag2_depAnswers.add("Dagelijks");
        vraag2_depAnswers.add("Wekelijks");
        EnqueteOpenVraag vraag2 = new EnqueteOpenVraag("Heeft u suggesties voor het verbeteren van onze winkel/website?", vraag1, vraag2_depAnswers);

        EnqueteMeerkeuzeVraag vraag3 = new EnqueteMeerkeuzeVraag("Welk type games koopt u meestal bij Good ol’ Games?", new String[]{"RPG", "Puzzel", "Strategie", "Avontuur", "Actie"});

        //Vraag 4 heeft een dependency
        ArrayList<String> vraag4_depAnswers = new ArrayList<String>();
        vraag4_depAnswers.add("RPG");
        vraag4_depAnswers.add("Puzzel");
        vraag4_depAnswers.add("Strategie");
        EnqueteMeerkeuzeVraag vraag4 = new EnqueteMeerkeuzeVraag("Wat is voor u het belangrijkste criterium bij het kiezen van een retro-game?", new String[]{"Gameplay", "Graphics", "StoryLine", "Prijs", "Aanbevelingen/Ranglijst"}, vraag3, vraag4_depAnswers);

        //Vraag 5 heeft een dependency
        ArrayList<String> vraag5_depAnswers = new ArrayList<String>();
        vraag5_depAnswers.add("Gameplay");
        vraag5_depAnswers.add("Grapics");
        vraag5_depAnswers.add("StoryLine");
        vraag5_depAnswers.add("Aanbevelingen/Ranglijst");
        EnqueteMeerkeuzeVraag vraag5 = new EnqueteMeerkeuzeVraag("Hoe belangrijk is de prijs van een game bij uw beslissing om te kopen?", new String[]{"Zeer onbelangrijk", "Onbelangrijk", "Neutraal", "Belangrijk", "Zeer belangrijk"}, vraag4, vraag5_depAnswers);

        EnqueteMeerkeuzeVraag vraag6 = new EnqueteMeerkeuzeVraag("Hoe waarschijnlijk is het dat u Good ol’ Games zou aanbevelen aan vrienden of familie?", new String[]{"Zeer onwaarschijnlijk", "Onwaarschijnlijk", "Neutraal", "Waarschijnlijk", "Zeer waarschijnlijk"});

        EnqueteMeerkeuzeVraag vraag7 = new EnqueteMeerkeuzeVraag("Hoe tevreden bent u over de algemene sfeer en ervaring van de Good ol’ Games winkel/website?", new String[]{"Zeer ontevreden", "Ontevreden", "Neutraal", "Tevreden", "Zeer tevreden"});

        this.vragen.add(vraag1);
        this.vragen.add(vraag2);
        this.vragen.add(vraag3);
        this.vragen.add(vraag4);
        this.vragen.add(vraag5);
        this.vragen.add(vraag6);
        this.vragen.add(vraag7);

    }

    public void stelVragen()
    {
        for(EnqueteVraag vraag : this.vragen)
        {
            if(this.checkForDependency(vraag)) {
                System.out.println(vraag.stelVraag());
                if (!vraag.wachtOpAntwoord()) {
                    vraag.wachtOpAntwoord();
                }
            }
        }
        schrijfAntwoorden();
    }

    public ArrayList<EnqueteVraag> getVragen()
    {
        return this.vragen;
    }

    public boolean checkForDependency(EnqueteVraag vraag)
    {
        if(vraag.dependency != null)
        {
            String dep_answer = vraag.dependency.getAntwoordGegeven();

            if(vraag.dependency_antwoorden.contains(vraag.dependency.formatAntwoordGegeven()))
            {
                return true;
            }
            else
            {
                return false;
            }
        }


        return true;
    }

    public void schrijfAntwoorden()
    {
        enqueteLoader.writeEnquete(this);
    }
}
