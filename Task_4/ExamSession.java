import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ExamSession {
    private User user;
    private List<Question> questions;
    private Map<Integer, Integer> answers = new HashMap<>(); // Key: Question ID, Value: User's Answer (1-4)
    private long startTime;
    private long durationInSeconds;
    private boolean submitted = false;

    public ExamSession(User user, List<Question> questions, long durationInSeconds) {
        this.user = user;
        this.questions = questions;
        this.startTime = System.currentTimeMillis();
        this.durationInSeconds = durationInSeconds;
    }

    public void submitAnswer(int questionId, int answer) {
        if (!submitted) answers.put(questionId, answer);
    }

    public Integer getAnswer(int questionId) { return answers.get(questionId); }
    
    public long getRemainingTime() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        return Math.max(0, durationInSeconds - elapsed);
    }

    public ExamResult submit() {
        if (submitted) throw new IllegalStateException("Exam already submitted");
        submitted = true;
        int correct = 0;
        for (Question q : questions) {
            Integer userAnswer = answers.get(q.getId());
            if (userAnswer != null && q.isCorrect(userAnswer)) correct++;
        }
        return new ExamResult(correct, questions.size());
    }

    public List<Question> getQuestions() { return questions; }
    public boolean isSubmitted() { return submitted; }
    public int getAnsweredCount() { return answers.size(); }
}