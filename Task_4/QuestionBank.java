import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private List<Question> questions = new ArrayList<>();

    public QuestionBank() {
        // Initializing the question bank with 10 questions
        questions.add(new Question(1, "What is the capital of France?",
            new String[]{"London", "Paris", "Berlin", "Madrid"}, 2)); 
        questions.add(new Question(2, "Which planet is known as the Red Planet?",
            new String[]{"Venus", "Mars", "Jupiter", "Saturn"}, 2)); 
        questions.add(new Question(3, "What is 2 + 2?",
            new String[]{"3", "4", "5", "6"}, 2)); 
        questions.add(new Question(4, "Who wrote Romeo and Juliet?",
            new String[]{"Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"}, 2)); 
        questions.add(new Question(5, "What is the largest ocean on Earth?",
            new String[]{"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"}, 4)); 
        questions.add(new Question(6, "The chemical symbol for Gold is:",
            new String[]{"Go", "Gd", "Au", "Ag"}, 3)); 
        questions.add(new Question(7, "Java is an example of which type of programming language?",
            new String[]{"Markup Language", "Assembly Language", "High-Level Language", "Machine Code"}, 3)); 
        questions.add(new Question(8, "In which year did World War II end?",
            new String[]{"1943", "1945", "1944", "1946"}, 2)); 
        questions.add(new Question(9, "What is the square root of 144?",
            new String[]{"10", "11", "12", "13"}, 3)); 
        questions.add(new Question(10, "Which continent is the Sahara Desert located in?",
            new String[]{"Asia", "Africa", "Europe", "South America"}, 2)); 
    }

    public List<Question> getAllQuestions() { return new ArrayList<>(questions); }
    public int getTotalQuestions() { return questions.size(); }
}