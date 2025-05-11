import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Act4 {
    public static void main(String[] args) {
        JFrame finestra = new JFrame("Act4");
        finestra.setSize(400, 200);
        finestra.setLayout(null);
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField email = new JTextField("Escriu el teu e-mail:");
        email.setBounds(50, 30, 200, 30);
        finestra.add(email);

        JTextField nom = new JTextField("Escriu el teu nom:");
        nom.setForeground(Color.GRAY);
        nom.setBounds(50, 80, 250, 40);
        finestra.add(nom);

        finestra.setVisible(true);
    }
}