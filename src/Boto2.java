import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Boto2 extends JFrame {
    public Boto2() {
        setTitle("El mejor titulo");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); // Per permetre posició manual
        getContentPane().setBackground(new Color(240, 240, 240)); // Color suau

        afegirBotons();
    }

    private void afegirBotons() {
        // Primer botó
        JButton boto1 = new JButton("Click");
        boto1.setBounds(130, 50, 120, 40);
        boto1.setForeground(Color.BLUE);
        boto1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        add(boto1);

        // Segon botó amb icona
        ImageIcon icona = new ImageIcon("C:\\Users\\Usuario\\Desktop\\ma.avif"); // Canvia-ho pel teu path!
        JButton boto2 = new JButton("Clica aquí", icona);
        boto2.setBounds(115, 120, 160, 50);
        boto2.setForeground(Color.WHITE);
        boto2.setBackground(Color.RED);
        boto2.setOpaque(true);
        boto2.setBorderPainted(false);
        add(boto2);
    }
}
