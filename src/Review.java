
public class Review {
    private int id;
    private String name;
    private int gameplayScore ;

    private int graphicsScore ;
    private int storylineScore ;
    private double totalScore;
    private String tekstReview;

    public Review(int id, String name, int gameplayScore, int graphicsScore, int storylineScore, double totalScore, String tekstReview) {
        this.id = id;
        this.name = name;
        this.gameplayScore = gameplayScore;
        this.graphicsScore = graphicsScore;
        this.storylineScore = storylineScore;
        this.totalScore = totalScore;
        this.tekstReview = tekstReview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameplayScore() {
        return gameplayScore;
    }

    public void setGameplayScore(int gameplayScore) {
        this.gameplayScore = gameplayScore;
    }

    public double getTotalScore(){
        return Double.valueOf(gameplayScore + graphicsScore + storylineScore ) / 3;
    }

    public int getGraphicsScore() {
        return graphicsScore;
    }

    public void setGraphicsScore(int graphicsScore) {
        this.graphicsScore = graphicsScore;
    }

    public int getStorylineScore() {
        return storylineScore;
    }

    public void setStorylineScore(int storylineScore) {
        this.storylineScore = storylineScore;
    }

    public String getTekstReview() {
        return tekstReview;
    }

    public void setTekstReview(String tekstReview) {
        this.tekstReview = tekstReview;
    }
}


