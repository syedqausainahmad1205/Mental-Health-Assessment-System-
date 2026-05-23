public class History {

    private String date;
    private int score;
    private String category;

    public History(String date, int score, String category) {
        this.date = date;
        this.score = score;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

    public String getCategory() {
        return category;
    }
}