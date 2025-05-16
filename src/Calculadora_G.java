import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class Calculadora_G extends JFrame {

    private JTextField pantalla;
    private String operador = "";
    private double primerValor = 0;
    private String expressio = ""; //Per mostrar l’expressió completa

    public Calculadora_G() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pantalla = new JTextField();
        pantalla.setEditable(false);
        pantalla.setFont(new Font("Arial", Font.PLAIN, 24));
        add(pantalla, BorderLayout.NORTH);

        JPanel panelNumeros = new JPanel(new GridLayout(6, 3, 5, 5)); //Panell de números

        //Botó "AC"
        JButton botoAC = new JButton("AC");
        botoAC.addActionListener(e -> {
            pantalla.setText("");
            expressio = "";
            operador = "";
            primerValor = 0;
        });
        panelNumeros.add(botoAC);

        //Botons del 0 al 9
        for (int i = 0; i <= 9; i++) {
            afegirBoto(panelNumeros, String.valueOf(i));
        }

        //Operacions
        afegirBoto(panelNumeros, "+");
        afegirBoto(panelNumeros, "-");
        afegirBoto(panelNumeros, "*");
        afegirBoto(panelNumeros, "%");
        panelNumeros.add(new JLabel()); //espai buit

        //Botó igual
        JButton igual = new JButton("=");
        igual.addActionListener(e -> calcular());
        panelNumeros.add(igual);

        add(panelNumeros, BorderLayout.CENTER);     //Centrar el panell dels números
        setVisible(true);
    }

    private void afegirBoto(JPanel panel, String text) {        //Fer-los visibles
        JButton boto = new JButton(text);
        boto.addActionListener(e -> gestionarClic(text));
        panel.add(boto);
    }

    private void gestionarClic(String text) {
        if ("+-*%".contains(text)) {
            try {
                if (!pantalla.getText().isEmpty()) {
                    primerValor = Double.parseDouble(pantalla.getText());
                    operador = text;
                    expressio = pantalla.getText() + operador; //guardem l’expressió
                    pantalla.setText(expressio);
                }
            } catch (NumberFormatException e) {
                pantalla.setText("ERROR");
            }
        } else {
            pantalla.setText(pantalla.getText() + text);
        }
    }

    private void calcular() {
        try {
            if (operador.isEmpty() || pantalla.getText().isEmpty())
                return;

            String[] parts = pantalla.getText().split("\\" + operador); //separem la expressió
            if (parts.length < 2) return;

            double segonValor = Double.parseDouble(parts[1]);
            double resultat = 0;

            switch (operador) {     //Realitzar operacions
                case "+": resultat = primerValor + segonValor; break;
                case "-": resultat = primerValor - segonValor; break;
                case "*": resultat = primerValor * segonValor; break;
                case "%":
                    if (segonValor == 0) {
                        pantalla.setText("ERROR");
                        return;
                    }
                    resultat = primerValor / segonValor;
                    break;
            }

            pantalla.setText(String.valueOf(resultat));
            operador = "";
            expressio = "";
        } catch (Exception e) {
            pantalla.setText("ERROR");
        }
    }

    public static void main(String[] args) {
        new Calculadora_G();
    }
}
