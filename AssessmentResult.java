public class AssessmentResult {

    private int score;
    private String category;

    public AssessmentResult(int score, String category) {
        this.score = score;
        this.category = category;
    }

    public int getScore() {
        return score;
    }

    public String getCategory() {
        return category;
    }
}