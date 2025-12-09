import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.EmptyBorder;

public class ExamPanel extends JPanel {
    private ExamSystemGUI parent;
    private ExamSession session;
    private JLabel timerLabel;
    private JLabel questionTextLabel;
    private JPanel optionsPanel;
    private ButtonGroup optionsGroup;
    private JRadioButton[] optionButtons;
    private Timer timer;

    public ExamPanel(ExamSystemGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Colors.BACKGROUND);
        setupUI();
    }

    private void setupUI() {
        // --- Header (Timer) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        timerLabel = new JLabel("Time Remaining: --:--");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        timerLabel.setForeground(Colors.DANGER);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(timerLabel, BorderLayout.CENTER);
        
        // --- Main Content (Question and Options) ---
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        contentPanel.setBackground(Colors.BACKGROUND);

        questionTextLabel = new JLabel("Question: Loading...");
        questionTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        questionTextLabel.setForeground(Colors.TEXT);
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(Colors.CARD);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];

        // Setup permanent listeners (Handles selection for MCQs)
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton("Option " + (i + 1));
            optionButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            optionButtons[i].setBackground(Colors.CARD);
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
            optionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            
            final int answerIndex = i; // 0-based for array
            
            optionButtons[i].addActionListener(e -> {
                // Use invokeLater for thread safety when submitting the answer
                SwingUtilities.invokeLater(() -> {
                    if (session != null) {
                        // Record answer (1-based index: 1, 2, 3, 4)
                        int questionId = session.getQuestions().get(parent.getCurrentQuestionIndex()).getId();
                        session.submitAnswer(questionId, answerIndex + 1);
                    }
                });
            });
        }
        
        contentPanel.add(questionTextLabel, BorderLayout.NORTH);
        contentPanel.add(optionsPanel, BorderLayout.CENTER);
        
        // --- Footer (Navigation and Submit Button) ---
        JPanel footerPanel = new JPanel(new BorderLayout()); // Use BorderLayout for positioning
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Navigation Buttons Panel (Left/Center)
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(Color.WHITE);
        
        StyledButton prevButton = new StyledButton("Previous", new Color(107, 114, 128));
        prevButton.setPreferredSize(new Dimension(120, 45));
        prevButton.addActionListener(e -> navigateQuestion(-1));
        
        StyledButton nextButton = new StyledButton("Next", Colors.PRIMARY);
        nextButton.setPreferredSize(new Dimension(120, 45));
        nextButton.addActionListener(e -> navigateQuestion(1));
        
        navPanel.add(prevButton);
        navPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        navPanel.add(nextButton);
        
        // Submit Button Panel (Right)
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitPanel.setBackground(Color.WHITE);
        
        StyledButton submitButton = new StyledButton("Finish & Submit Exam", Colors.SUCCESS);
        submitButton.setPreferredSize(new Dimension(200, 45));
        submitButton.addActionListener(e -> submitExamConfirmed());
        
        submitPanel.add(submitButton);

        footerPanel.add(navPanel, BorderLayout.WEST); // Add navigation buttons to the left
        footerPanel.add(submitPanel, BorderLayout.EAST); // Keep submit button on the right

        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    public void start(ExamSession session) {
        this.session = session;
        parent.setCurrentQuestionIndex(0); 
        displayQuestion(0);
        startTimer();
    }
    
    /**
     * Displays the question at the given index and restores the user's previous selection.
     */
    public void displayQuestion(int index) {
        if (session == null || index < 0 || index >= session.getQuestions().size()) return;

        Question q = session.getQuestions().get(index);
        parent.setCurrentQuestionIndex(index); // Update controller state
        
        questionTextLabel.setText("Question " + (index + 1) + "/" + session.getQuestions().size() + ": " + q.getQuestion());
        optionsGroup.clearSelection();

        Integer userAnswer = session.getAnswer(q.getId());
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q.getOptions()[i]);
            if (userAnswer != null && userAnswer == (i + 1)) {
                optionButtons[i].setSelected(true);
            }
        }
    }
    
    /**
     * Navigates to the next or previous question.
     * @param direction +1 for next, -1 for previous.
     */
    private void navigateQuestion(int direction) {
        if (session == null) return;
        
        int totalQuestions = session.getQuestions().size();
        int newIndex = parent.getCurrentQuestionIndex() + direction;
        
        // Boundary Check
        if (newIndex >= 0 && newIndex < totalQuestions) {
            parent.setCurrentQuestionIndex(newIndex);
            displayQuestion(newIndex);
        } else if (newIndex < 0) {
            JOptionPane.showMessageDialog(this, "You are already at the first question.", 
                "Navigation Error", JOptionPane.INFORMATION_MESSAGE);
        } else if (newIndex >= totalQuestions) {
            JOptionPane.showMessageDialog(this, "You have reached the last question. Click 'Finish & Submit Exam' to complete.", 
                "Navigation Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void startTimer() {
        if (timer != null) timer.cancel();
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long remaining = session.getRemainingTime();
                
                SwingUtilities.invokeLater(() -> {
                    if (remaining <= 0) {
                        timerLabel.setText("TIME UP! Auto-Submitting...");
                        timer.cancel();
                        submitExam(true); // Auto Submit
                    } else {
                        long minutes = remaining / 60;
                        long seconds = remaining % 60;
                        timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
                        
                        // Visual Warning for last minute
                        if (remaining <= 60) {
                            timerLabel.setForeground(remaining % 2 == 0 ? Colors.WARNING : Colors.DANGER);
                        } else {
                             timerLabel.setForeground(Colors.DANGER.darker());
                        }
                    }
                });
            }
        }, 0, 1000); // 1 second interval
    }
    
    private void submitExamConfirmed() {
        int answered = session.getAnsweredCount();
        int total = session.getQuestions().size();
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            String.format("You have answered %d out of %d questions.\nAre you sure you want to finish the exam?", answered, total), 
            "Confirm Submission", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            submitExam(false);
        }
    }

    private void submitExam(boolean auto) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        
        ExamResult result = session.submit();
        parent.showResultPanel(result, auto);
    }
}