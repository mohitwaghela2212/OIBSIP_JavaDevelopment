import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class ProfilePanel extends JPanel {
    private ExamSystemGUI parent;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JLabel messageLabel;

    public ProfilePanel(ExamSystemGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Colors.BACKGROUND);
        setupUI();
    }

    private void setupUI() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("Update Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        StyledButton backButton = new StyledButton("Back to Menu", new Color(107, 114, 128));
        backButton.setPreferredSize(new Dimension(150, 35));
        backButton.addActionListener(e -> parent.showMenuPanel());
        
        StyledButton logoutButton = new StyledButton("Logout", Colors.DANGER);
        logoutButton.setPreferredSize(new Dimension(120, 35));
        logoutButton.addActionListener(e -> parent.logout());
        
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Colors.BACKGROUND);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        
        // --- FIX APPLIED HERE: Removed fixed preferred size for vertical growth ---
        formPanel.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));
        // --- END FIX ---
        
        JLabel icon = new JLabel("👤");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel formTitle = new JLabel("Update Your Information");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        formTitle.setForeground(Colors.TEXT);
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Fields initialization
        nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setMaximumSize(new Dimension(400, 40));
        
        emailField = new JTextField(20);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setMaximumSize(new Dimension(400, 40));
        
        currentPasswordField = new JPasswordField(20);
        currentPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        currentPasswordField.setMaximumSize(new Dimension(400, 40));
        
        newPasswordField = new JPasswordField(20);
        newPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        newPasswordField.setMaximumSize(new Dimension(400, 40));
        
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        StyledButton saveButton = new StyledButton("Save Changes", Colors.PRIMARY);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> updateProfile());
        
        // Assemble form
        User user = parent.getCurrentUser(); // User may be null initially, but loadUserData sets text
        formPanel.add(icon);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(formTitle);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        formPanel.add(createField("Full Name", nameField, "Current: " + (user != null ? user.getName() : "")));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(createField("Email Address", emailField, "Current: " + (user != null ? user.getEmail() : "")));
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        JLabel passwordTitle = new JLabel("Change Password");
        passwordTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordTitle);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        formPanel.add(createField("Current Password", currentPasswordField, ""));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(createField("New Password", newPasswordField, "Leave empty to keep current password"));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(messageLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(saveButton);
        
        contentPanel.add(formPanel);
        
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createField(String label, JComponent field, String hint) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(400, 80));
        
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel hintLabel = new JLabel(hint);
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hintLabel.setForeground(Colors.TEXT_LIGHT);
        hintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(jLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);
        
        if (!hint.isEmpty()) {
            panel.add(Box.createRigidArea(new Dimension(0, 3)));
            panel.add(hintLabel);
        }
        
        return panel;
    }

    public void loadUserData() {
        User user = parent.getCurrentUser();
        if (user != null) {
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
            currentPasswordField.setText("");
            newPasswordField.setText("");
            messageLabel.setText("");
        }
    }

    private void updateProfile() {
        String newName = nameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String currentPass = new String(currentPasswordField.getPassword());
        String newPass = new String(newPasswordField.getPassword());
        
        User user = parent.getCurrentUser();
        UserService userService = parent.getUserService();
        boolean profileUpdated = false;
        boolean passwordUpdated = true;

        // 1. Update Name/Email
        if (!newName.equals(user.getName()) || !newEmail.equals(user.getEmail())) {
            userService.updateProfile(user, newName, newEmail);
            profileUpdated = true;
        }

        // 2. Change Password
        if (!newPass.isEmpty()) {
            if (currentPass.isEmpty()) {
                messageLabel.setText("Enter current password to change it.");
                messageLabel.setForeground(Colors.WARNING);
                return;
            }
            if (!userService.updatePassword(user, currentPass, newPass)) {
                messageLabel.setText("Error: Current password incorrect.");
                messageLabel.setForeground(Colors.DANGER);
                passwordUpdated = false;
            } else {
                passwordUpdated = true;
            }
        }
        
        // 3. Display Result Message
        if (profileUpdated || (passwordUpdated && !newPass.isEmpty())) {
            if (passwordUpdated && !newPass.isEmpty()) {
                messageLabel.setText("Profile and Password updated successfully!");
            } else if (profileUpdated) {
                messageLabel.setText("Profile updated successfully!");
            }
            messageLabel.setForeground(Colors.SUCCESS_DARK);
            currentPasswordField.setText("");
            newPasswordField.setText("");
            parent.updateMenuWelcome(); // Refresh Menu welcome message
        } else if (!newPass.isEmpty() && !passwordUpdated) {
             // Error already set above
        } else {
             messageLabel.setText("No changes detected.");
             messageLabel.setForeground(Colors.TEXT_LIGHT);
        }
    }
}