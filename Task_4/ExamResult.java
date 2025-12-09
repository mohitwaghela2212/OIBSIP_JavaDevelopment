public class ExamResult {
    private int correctAnswers;
    private int totalQuestions;
    private double percentage;
    private boolean passed;

    public ExamResult(int correctAnswers, int totalQuestions) {
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.percentage = (double) correctAnswers / totalQuestions * 100;
        this.passed = percentage >= 40.0;
    }

    public int getCorrectAnswers() { return correctAnswers; }
    public int getTotalQuestions() { return totalQuestions; }
    public double getPercentage() { return percentage; }
    public boolean isPassed() { return passed; }
}