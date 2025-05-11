import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Finestra extends JFrame{
    public Finestra() {
        setSize(500, 500);      //Tamany de la finestra
        setDefaultCloseOperation(EXIT_ON_CLOSE);        //Es tanca quan sortim
        setTitle("Calculadora");        //Afegim títol
        setLocationRelativeTo(null);        //Apareix la finestra al centre
        setResizable(false);        //No es pot canviar el tamay de la finestra
        this.getContentPane().setBackground(Color.blue);        //Fons de color blau

        colocar_boto();
    }

    private void colocar_boto() {
        JButton button1 = new JButton();
        button1.setText("Click Aquí");      //Text del botó
        button1.setBounds(100, 100, 100, 40);       //Tamany botó
        button1.setEnabled(true); //Que estigui actiu
        setLayout(null); //Permetre l'ús de setBounds
        this.add(button1); //Aegir el botó al JFrame
    }
}
