import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Act3 extends JFrame {
    public static void main(String[] args) {
        JFrame finestra = new JFrame("Llenguatges");
        finestra.setSize(400, 300);     //Grandària finestra
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setLayout(null);

        JLabel label = new JLabel("Llenguajes:");       //Títol
        label.setBounds(20, 10, 100, 20);
        finestra.add(label);

        //Botons de tipus "JRadioButton"
        JRadioButton java = new JRadioButton("Java");
        JRadioButton php = new JRadioButton("Php");
        JRadioButton cpp = new JRadioButton("C++");

        java.setBounds(20, 35, 100, 20);
        php.setBounds(20, 60, 100, 20);
        cpp.setBounds(20, 85, 100, 20);

        ButtonGroup grup = new ButtonGroup();
        grup.add(java);
        grup.add(php);
        grup.add(cpp);

        finestra.add(java);
        finestra.add(php);
        finestra.add(cpp);

        JButton validar = new JButton("Validar");       //Botó "Validar"
        validar.setBounds(20, 120, 100, 25);
        finestra.add(validar);

        finestra.setVisible(true);
    }
}
