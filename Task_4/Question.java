public class Question {
    private int id;
    private String question;
    private String[] options;
    private int correctAnswer; // 1-based index (1, 2, 3, or 4)

    public Question(int id, String question, String[] options, int correctAnswer) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public int getId() { return id; }
    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
    public boolean isCorrect(int answer) { return answer == correctAnswer; }
}