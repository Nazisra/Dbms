package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class signup extends JFrame implements ActionListener {

    // Fields for username, email, password
    JTextField usernameField, emailField;
    JPasswordField passwordField;

    // Buttons
    JButton signupButton;
    JLabel loginLabel; // We'll use a label to mimic the “Login” button at the bottom

    public signup() {
        // Main gradient panel
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Create vertical gradient from dark purple to a lighter purple
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(49, 35, 96),
                        0, h, new Color(75, 50, 160));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        gradientPanel.setLayout(null);
        setContentPane(gradientPanel);

        // Create a “card” panel in the center
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Paint a rounded rectangle background with a slight shadow
                Graphics2D g2d = (Graphics2D) g.create();
                // Shadow
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 30, 30);
                // Main background
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 30, 30);
                g2d.dispose();
            }
        };
        cardPanel.setLayout(null);
        cardPanel.setOpaque(false); // We'll paint it ourselves
        cardPanel.setBounds(100, 40, 300, 300);
        gradientPanel.add(cardPanel);

        // “Sign Up” title
        JLabel titleLabel = new JLabel("Sign up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setForeground(new Color(51, 51, 51));
        titleLabel.setBounds(0, 20, 300, 30);
        cardPanel.add(titleLabel);

        // Username label & field
        JLabel userLabel = new JLabel("User name");
        userLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        userLabel.setForeground(new Color(51, 51, 51));
        userLabel.setBounds(30, 70, 100, 25);
        cardPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(30, 95, 240, 30);
        usernameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cardPanel.add(usernameField);

        // Email label & field
        JLabel emailLbl = new JLabel("Email");
        emailLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        emailLbl.setForeground(new Color(51, 51, 51));
        emailLbl.setBounds(30, 135, 100, 25);
        cardPanel.add(emailLbl);

        emailField = new JTextField();
        emailField.setBounds(30, 160, 240, 30);
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cardPanel.add(emailField);

        // Password label & field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        passLabel.setForeground(new Color(51, 51, 51));
        passLabel.setBounds(30, 200, 100, 25);
        cardPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(30, 225, 240, 30);
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cardPanel.add(passwordField);

        // Sign Up button
        signupButton = new JButton("Sign up");
        signupButton.setBounds(80, 270, 140, 35);
        signupButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(102, 0, 255));
        signupButton.setFocusPainted(false);
        signupButton.setBorderPainted(false);
        signupButton.addActionListener(this);
        cardPanel.add(signupButton);

        // “Login” label at the bottom of the frame
        loginLabel = new JLabel("Login", SwingConstants.CENTER);
        loginLabel.setOpaque(true);
        loginLabel.setBackground(new Color(102, 0, 255));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        loginLabel.setBounds(200, 370, 100, 30);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Add a mouse listener to handle “click” on the label
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new Login();
                setVisible(false);
            }
        });
        gradientPanel.add(loginLabel);

        // Frame settings
        setTitle("Sign Up");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            try {
                con c = new con();
                String user = usernameField.getText().trim();
                String pass = new String(passwordField.getPassword()).trim();
                String email = emailField.getText().trim();

                if (user.isEmpty() || pass.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fields cannot be empty");
                    return;
                }

                // Insert new user into the login database (Assuming you added an 'email' column)
                String q = "INSERT INTO login (username, password, email) VALUES ('"
                        + user + "', '" + pass + "', '" + email + "')";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "Sign up successful!");

                // Go back to login
                new Login();
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new signup();
    }
}
