package Bodega;

import javax.swing.*;
import java.io.FileNotFoundException;

public class LoginFrame extends JFrame {
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Acceso Administrativo");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel label = new JLabel("Contraseña:");
        label.setBounds(30, 30, 100, 25);
        add(label);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 30, 150, 25);
        add(passwordField);

        loginButton = new JButton("Ingresar");
        loginButton.setBounds(100, 70, 100, 30);
        add(loginButton);

        loginButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            if (password.equals("HOLA1234")) {
                JOptionPane.showMessageDialog(this, "Acceso concedido");
                dispose();
                try {
                    new InventarioFrame().setVisible(true);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}