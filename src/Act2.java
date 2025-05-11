import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Act2 extends JFrame {
    public Act2() {
        setTitle("Act2");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        this.getContentPane().setBackground(Color.blue);        //Fons de color blau

        afegirBotons();
    }

    private void afegirBotons() {
        //1er botó amb text
        JButton boto1 = new JButton("Click");
        boto1.setBounds(130, 50, 120, 40);
        boto1.setForeground(Color.BLUE);
        boto1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        add(boto1);

        //2n botó amb imatge
        ImageIcon icona = new ImageIcon("C:\\Users\\Usuario\\IdeaProjects\\calculadora\\src\\boton.png");       //He intentat agregar-lo com a icona
        JButton boto2 = new JButton(icona);
        boto2.setBounds(115, 120, icona.getIconWidth(), icona.getIconHeight());

        add(boto2);
    }

    public static void main(String[] args) {
        new Finestra().setVisible(true);
    }
}
