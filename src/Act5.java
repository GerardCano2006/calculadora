import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Act5 {
    public static void main(String[] args) {
        JFrame finestra = new JFrame("Act5");
        finestra.setSize(400, 200);
        finestra.setLayout(null);
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel resultat = new JLabel("Programming language Selected: Java");        //Etiqueta del resultat
        resultat.setBounds(50, 20, 300, 30);
        finestra.add(resultat);

        //ComboBox amb les opcións
        JComboBox combo = new JComboBox<>(new String[]{"Java", "C++", "C#", "Java", "php"});
        combo.setBounds(50, 60, 150, 30);
        combo.setSelectedItem("Java");  //Opció per defecte
        finestra.add(combo);

        JButton boto = new JButton("Show");     //Botó per mostrar
        boto.setBounds(210, 60, 80, 30);
        finestra.add(boto);

        boto.addActionListener(new ActionListener() {       //ActionListener pel botó
            public void actionPerformed(ActionEvent e) {
                String seleccio = (String)combo.getSelectedItem();
                resultat.setText("Programming language Selected: " + seleccio);
            }
        });

        finestra.setVisible(true);
    }
}