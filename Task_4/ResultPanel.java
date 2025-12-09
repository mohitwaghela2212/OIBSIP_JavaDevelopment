import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class ResultPanel extends JPanel {
    private ExamSystemGUI parent;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JLabel percentageLabel;
    private JLabel autoSubmitHint;

    public ResultPanel(ExamSystemGUI parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        setBackground(Colors.BACKGROUND);
        setupUI();
    }

    private void setupUI() {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        cardPanel.setPreferredSize(new Dimension(500, 450));

        statusLabel = new JLabel("Results");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        autoSubmitHint = new JLabel("");
        autoSubmitHint.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        autoSubmitHint.setForeground(Colors.TEXT_LIGHT);
        autoSubmitHint.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        scoreLabel = new JLabel("Correct: 0 / 0");
        scoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        percentageLabel = new JLabel("Percentage: 0.0%");
        percentageLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        percentageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        StyledButton backButton = new StyledButton("Back to Menu", Colors.PRIMARY);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> parent.showMenuPanel());
        
        StyledButton logoutButton = new StyledButton("Logout", Colors.DANGER);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> parent.logout());

        cardPanel.add(statusLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(autoSubmitHint);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardPanel.add(scoreLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(percentageLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        cardPanel.add(backButton);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(logoutButton);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(cardPanel, gbc);
    }

    public void displayResult(ExamResult result, boolean auto) {
        Color resultColor = result.isPassed() ? Colors.SUCCESS : Colors.DANGER;
        String statusText = result.isPassed() ? "CONGRATULATIONS! (PASSED)" : "FAILED";
        
        statusLabel.setText(statusText);
        
        if (auto) {
            autoSubmitHint.setText("Submitted automatically due to time limit.");
        } else {
            autoSubmitHint.setText("Submitted successfully.");
        }

        statusLabel.setForeground(resultColor.darker());
        scoreLabel.setText(String.format("Correct Answers: %d / %d", result.getCorrectAnswers(), result.getTotalQuestions()));
        percentageLabel.setText(String.format("Final Score: %.2f%%", result.getPercentage()));
    }
}