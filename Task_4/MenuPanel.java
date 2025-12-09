import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class MenuPanel extends JPanel {
    private ExamSystemGUI parent;
    private JLabel welcomeLabel;

    public MenuPanel(ExamSystemGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Colors.BACKGROUND);
        setupUI();
    }

    public void setupUI() {
        this.removeAll(); 
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("Exam Portal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Colors.TEXT);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        
        // Get user safely
        User user = parent.getCurrentUser();
        String name = user != null ? user.getName() : "Guest";
        welcomeLabel = new JLabel("Welcome, " + name);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(Colors.TEXT_LIGHT);
        
        StyledButton logoutButton = new StyledButton("Logout", Colors.DANGER);
        logoutButton.setPreferredSize(new Dimension(120, 35));
        logoutButton.addActionListener(e -> parent.logout());
        
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        rightPanel.add(logoutButton);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Colors.BACKGROUND);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Colors.BACKGROUND);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        JLabel mainTitle = new JLabel("What would you like to do?");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        mainTitle.setForeground(Colors.TEXT);
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Choose an option to continue");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(Colors.TEXT_LIGHT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(mainTitle);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        titlePanel.add(subtitle);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(titlePanel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        contentPanel.add(createExamCard(), gbc);
        
        gbc.gridx = 1;
        contentPanel.add(createProfileCard(), gbc);
        
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        
        this.revalidate(); 
        this.repaint();
    }

    private JPanel createExamCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        card.setPreferredSize(new Dimension(350, 400));
        
        JLabel icon = new JLabel("📚");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Start Exam");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Colors.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        infoPanel.add(createInfoRow("Total Questions:", "10"));
        infoPanel.add(createInfoRow("Duration:", "5 minutes (30 sec/Q for demo)"));
        infoPanel.add(createInfoRow("Total Marks:", "10"));
        infoPanel.add(createInfoRow("Passing Marks:", "4 (40%)"));
        
        StyledButton startButton = new StyledButton("Start Exam Now", Colors.SUCCESS);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> parent.startExam());
        
        card.add(icon);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(infoPanel);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(startButton);
        
        return card;
    }

    private JPanel createProfileCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        card.setPreferredSize(new Dimension(350, 400));
        
        JLabel icon = new JLabel("👤");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Update Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Colors.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        // FIX: Safely retrieve user info to avoid NullPointerException at startup
        User user = parent.getCurrentUser();
        
        String currentName = (user != null) ? user.getName() : "N/A";
        String currentEmail = (user != null) ? user.getEmail() : "N/A";
        String status = (user != null) ? "Active" : "Inactive";
        
        infoPanel.add(createInfoRow("Current Name:", currentName));
        infoPanel.add(createInfoRow("Current Email:", currentEmail));
        infoPanel.add(createInfoRow("Account Status:", status));
        
        StyledButton updateButton = new StyledButton("Update Profile", Colors.PRIMARY);
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateButton.addActionListener(e -> parent.showProfilePanel());
        
        card.add(icon);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(infoPanel);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(updateButton);
        
        return card;
    }

    private JPanel createInfoRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        row.setMaximumSize(new Dimension(400, 35));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelComp.setForeground(Colors.TEXT_LIGHT);
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.BOLD, 13));
        valueComp.setForeground(Colors.TEXT);
        
        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.EAST);
        
        return row;
    }
}