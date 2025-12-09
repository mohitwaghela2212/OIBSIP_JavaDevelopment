import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private ExamSystemGUI parent;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginPanel(ExamSystemGUI parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        setBackground(new Color(79, 70, 229)); 
        setupUI();
    }

    private void setupUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        // BoxLayout with Y_AXIS allows easy vertical stacking
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS)); 
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        JLabel titleLabel = new JLabel("Online Exam Portal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Colors.PRIMARY);
        // Center alignment
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        JLabel subtitleLabel = new JLabel("Sign in to continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Colors.TEXT_LIGHT);
        // Center alignment
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(300, 40));
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(300, 40));
        
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        messageLabel.setForeground(Colors.DANGER);
        // Center alignment
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        StyledButton loginButton = new StyledButton("Sign In", Colors.PRIMARY);
        // Center alignment
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        loginButton.setMaximumSize(new Dimension(300, 45));
        loginButton.addActionListener(e -> login());
        
        // Demo Credentials Hint
        JPanel demoPanel = new JPanel();
        demoPanel.setBackground(new Color(239, 246, 255));
        demoPanel.setLayout(new BoxLayout(demoPanel, BoxLayout.Y_AXIS));
        demoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        demoPanel.setMaximumSize(new Dimension(300, 100));
        // Center alignment for Demo Panel contents
        
        JLabel demoLabel = new JLabel("Demo Credentials:");
        demoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        demoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel userLabel = new JLabel("Username: student1");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel passLabel = new JLabel("Password: pass123");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        demoPanel.add(demoLabel);
        demoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        demoPanel.add(userLabel);
        demoPanel.add(passLabel);
        
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(subtitleLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // --- Added Center-Aligned Containers ---
        cardPanel.add(centerWrapper(createLabeledField("Username", usernameField)));
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(centerWrapper(createLabeledField("Password", passwordField)));
        // --- End Added Center-Aligned Containers ---
        
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(messageLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(loginButton);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        // Center the demo panel
        cardPanel.add(centerWrapper(demoPanel));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(cardPanel, gbc);
        
        passwordField.addActionListener(e -> login());
    }

    // New helper method to wrap a component and center it horizontally
    private JPanel centerWrapper(JComponent component) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(component);
        wrapper.setMaximumSize(new Dimension(wrapper.getMaximumSize().width, component.getPreferredSize().height + 10));
        return wrapper;
    }

    private JPanel createLabeledField(String label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        // Reduced max width on panel to match field max width (300)
        panel.setMaximumSize(new Dimension(300, 70)); 
        
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        // Use CENTER alignment for the label
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        // Use CENTER alignment for the field
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(jLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);
        
        return panel;
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        User user = parent.getUserService().authenticate(username, password);
        if (user != null) {
            parent.setCurrentUser(user);
            usernameField.setText("");
            passwordField.setText("");
            messageLabel.setText("");
            parent.showMenuPanel();
        } else {
            messageLabel.setText("Invalid username or password!");
        }
    }
}