import java.util.ArrayList;

public class Game {
    ArrayList<Review> reviews;
    private int id;
    private String title;
    private String genre;
    private double price;
    private double discountPrice;

    public Game(String title, String genre, double price, double discountPrice){
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.discountPrice = discountPrice;
        this.reviews = new ArrayList<>();
    }

    public Game(int id, String title, String genre, double price, double discountPrice){
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.discountPrice = discountPrice;
        this.reviews = new ArrayList<>();
    }
    public double getTotalGameScore() {
        double t = 0.0;
        for(Review r : reviews){
            t += r.getTotalScore();
        }
        return t;
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
