import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExamSystemGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private UserService userService = new UserService();
    private QuestionBank questionBank = new QuestionBank();
    private User currentUser;
    private ExamSession currentSession;
    private int currentQuestionIndex; 

    // Panels 
    private LoginPanel loginPanel;
    private MenuPanel menuPanel;
    private ProfilePanel profilePanel;
    private ExamPanel examPanel;
    private ResultPanel resultPanel;

    public ExamSystemGUI() {
        setTitle("Java Exam Portal");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // Initialize Panels
        loginPanel = new LoginPanel(this);
        menuPanel = new MenuPanel(this); 
        profilePanel = new ProfilePanel(this);
        examPanel = new ExamPanel(this);
        resultPanel = new ResultPanel(this);

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(profilePanel, "Profile");
        mainPanel.add(examPanel, "Exam");
        mainPanel.add(resultPanel, "Result");

        add(mainPanel);
        setVisible(true);
    }
    
    // --- Public Getters and Setters for Controller ---
    public UserService getUserService() { return userService; }
    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User user) { this.currentUser = user; }
    public int getCurrentQuestionIndex() { return currentQuestionIndex; }
    public void setCurrentQuestionIndex(int index) { this.currentQuestionIndex = index; }

    // --- Navigation Methods ---

    public void showMenuPanel() {
        if (currentUser == null) return;
        updateMenuWelcome();
        cardLayout.show(mainPanel, "Menu");
    }
    
    public void showProfilePanel() {
        if (currentUser == null) return;
        profilePanel.loadUserData(); 
        cardLayout.show(mainPanel, "Profile");
    }

    public void startExam() {
        if (currentUser == null) return;
        
        // Duration: 5 minutes (300 seconds)
        long examDurationSeconds = 300; 

        List<Question> questions = questionBank.getAllQuestions();
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions loaded. Cannot start exam.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentSession = new ExamSession(currentUser, questions, examDurationSeconds);
        examPanel.start(currentSession);
        cardLayout.show(mainPanel, "Exam");
    }
    
    public void showResultPanel(ExamResult result, boolean auto) {
        currentSession = null; 
        resultPanel.displayResult(result, auto);
        cardLayout.show(mainPanel, "Result");
    }

    public void logout() {
        currentSession = null; 
        currentUser = null;
        cardLayout.show(mainPanel, "Login");
    }
    
    public void updateMenuWelcome() {
        menuPanel.setupUI(); 
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(ExamSystemGUI::new);
    }
}