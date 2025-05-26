import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class Calculadora_G extends JFrame {
    private JTextField pantalla;              //Mostra el que s'escriu i el resultat
    private String operador = "";             //Guarda l’operador seleccionat (+, -, *, /)
    private double primerValor = 0;           //Guarda el primer valor
    private String cadena_complerta = "";            //Mostra la cadena complerta (ex: 2+3)
    private JTextArea historialArea;          //Àrea de text que mostra totes les operacions fetes
    private StringBuilder historial = new StringBuilder(); // Guarda l’historial en memòria
    private boolean novaOperacio = false;       //Indica si s'ha fet una operació
    private java.util.List<JButton> totsElsBotons = new ArrayList<>();
    private java.util.Map<JButton, Color> colorsOriginals = new HashMap<>();
    private final Random random = new Random();

    public Calculadora_G() {
        setTitle("Calculadora_G");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Pantalla calculadora (operacións)
        pantalla = new JTextField();
        pantalla.setEditable(false);
        pantalla.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        pantalla.setPreferredSize(new Dimension(0, 60));
        add(pantalla, BorderLayout.NORTH); //Posició a dalt de tot

        //Panell de botons
        JPanel panelNumeros = new JPanel(new GridLayout(6, 3, 5, 5));

        //Àrea de text per l’historial
        historialArea = new JTextArea(5, 20);
        historialArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historialArea);
        add(scrollPane, BorderLayout.SOUTH); //Posició a sota de tot

        //Botó per esborrar tot l’historial
        JButton clearHistorial = crearBotoPersonalitzat("CH", Color.PINK, e -> {
            historial.setLength(0);
            historialArea.setText("");
        });
        panelNumeros.add(clearHistorial);

        //Botó esborrar contingut pantalla calculadora
        JButton botoAC = crearBotoPersonalitzat("C", Color.PINK, e -> {
            pantalla.setText("");
            cadena_complerta = "";
            operador = "";
            primerValor = 0;
        });
        panelNumeros.add(botoAC);

        //Botó per esborrar un caràcter de la pantalla
        JButton botoDEL = crearBotoPersonalitzat("DEL", Color.PINK, e -> {
            String text = pantalla.getText();
            if (!text.isEmpty()) {
                pantalla.setText(text.substring(0, text.length() - 1));
            }
        });
        panelNumeros.add(botoDEL);

        // Botó canviar signe +/-
        JButton botoSigne = crearBotoPersonalitzat("+/-", Color.PINK, e -> {
            String text = pantalla.getText();
            if (text.isEmpty()) return;

            //Si comença amb "-", eliminem el signe
            if (text.startsWith("-")) {
                pantalla.setText(text.substring(1));
            } else {
                pantalla.setText("-" + text);
            }
        });
        panelNumeros.add(botoSigne);

        //Botons del 0 al 9
        for (int i = 0; i <= 9; i++) {
            afegir_Boto(panelNumeros, String.valueOf(i));
        }

        //Botons d'operacions bàsiques
        afegir_Boto(panelNumeros, "+");
        afegir_Boto(panelNumeros, "-");
        afegir_Boto(panelNumeros, "*");
        afegir_Boto(panelNumeros, "%");

        //Botó per afegir un punt decimal
        JButton botoPunt = crearBotoPersonalitzat(".", Color.ORANGE, e -> afegir_Punt_Decimal());
        panelNumeros.add(botoPunt);

        //Botó factorial (!)
        JButton botoFactorial = crearBotoPersonalitzat("!", Color.ORANGE, e -> calcularFactorial());
        panelNumeros.add(botoFactorial);

        // Botó d’igual (=)
        JButton botoIgual = crearBotoPersonalitzat("=", Color.ORANGE, e -> calcular());
        panelNumeros.add(botoIgual);

        //Afegir el panell de botons al centre
        add(panelNumeros, BorderLayout.CENTER);

        setVisible(true);
    }

    //Crea i afegeix un botó amb text donat i acció associada
    private void afegir_Boto(JPanel panel, String text) {
        JButton boto = new JButton(text);
        boto.setFont(new Font("Arial", Font.BOLD, 20));
        boto.setPreferredSize(new Dimension(60, 60));
        boto.setBackground(new Color(135, 206, 250));
        Color colorOriginal = boto.getBackground();

        boto.addActionListener(e -> gestionarClic(text));

        // Efecte hover
        boto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {       //Ratolí a sobre
                boto.setBackground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {        //Normal
                boto.setBackground(colorOriginal);
            }
        });

        panel.add(boto);

        //Afegim el botó i el seu color original
        totsElsBotons.add(boto);
        colorsOriginals.put(boto, colorOriginal);
    }

    //Gestiona el click d’un número o operador
    private void gestionarClic(String text) {

        if (novaOperacio && !"/*-+%".contains(text)) {      //Nova operació
            pantalla.setText("");      //Esborra el resultat anterior
            novaOperacio = false;      //Reinicia el boolean
        }

        if ("+-*%".contains(text)) { //Si és operador
            try {
                if (!pantalla.getText().isEmpty()) {
                    primerValor = Double.parseDouble(pantalla.getText());       //Guardem primer valor (número)
                    operador = text;                                            //Guardem operador (càlcul)
                    cadena_complerta = pantalla.getText() + operador;           //Mostrem expressió (operació complerta)
                    pantalla.setText(cadena_complerta);
                }
            } catch (NumberFormatException e) {
                pantalla.setText("ERROR"); //En cas d’error
                playSound("illo.wav");
            }
        } else {
            pantalla.setText(pantalla.getText() + text); //Si és número, concatenar-lo
        }
    }

    //Afegeix un punt decimal si és vàlid fer-ho
    private void afegir_Punt_Decimal() {
        String text = pantalla.getText();

        if (text.isEmpty()) {
            pantalla.setText("0."); //Si està buit, comencem amb "0."
            return;
        }

        if (operador.isEmpty()) { //Si encara no hi ha operador
            if (!text.contains(".")) {
                pantalla.setText(text + ".");
            }
        }
        else {
            //Si ja hi ha operador, només permetem un punt en la segona part
            String[] parts = text.split("\\" + operador);
            if (parts.length == 2 && !parts[1].contains(".")) {
                pantalla.setText(text + ".");
            }
        }
    }

    //Realitza el càlcul de l’operació i actualitza el resultat i historial
    private void calcular() {
        try {
            if (operador.isEmpty() || pantalla.getText().isEmpty())
                return;

            String[] parts = pantalla.getText().split("\\" + operador);
            if (parts.length < 2) return;

            double segonValor = Double.parseDouble(parts[1]);
            double resultat = 0;

            switch (operador) {
                case "+": resultat = primerValor + segonValor; break;
                case "-": resultat = primerValor - segonValor; break;
                case "*": resultat = primerValor * segonValor; break;
                case "%":
                    if (segonValor == 0) {
                        pantalla.setText("ERROR");
                        playSound("illo.wav");
                        return;
                    }
                    resultat = primerValor / segonValor;
                    break;
            }

            // Afegim a l’historial
            String operacioCompleta = primerValor + " " + operador + " " + segonValor + " = " + resultat;
            historial.append(operacioCompleta).append("\n");
            historialArea.setText(historial.toString());

            // Animació tipus màquina tragaperres
            double finalResultat = resultat;
            playSound("tragaperras_sound.wav"); //Reproduir so de la màquina
            Timer timer = new Timer(100, new ActionListener() {
                int comptador = 0;
                Random rand = new Random();
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] simbols = {"7", "🍒", "🔔", "💎", "🍋", "⭐"};
                    pantalla.setText(
                            simbols[rand.nextInt(simbols.length)] +
                                    simbols[rand.nextInt(simbols.length)] +
                                    simbols[rand.nextInt(simbols.length)]
                    );

                    //Canvi aleatori de colors
                    for (JButton boto : totsElsBotons) {
                        boto.setBackground(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                    }

                    comptador++;
                    if (comptador >= 10) {
                        ((Timer) e.getSource()).stop();
                        pantalla.setText(String.valueOf(finalResultat));
                        novaOperacio = true;

                        //Recuperem els colors originals
                        for (JButton boto : totsElsBotons) {
                            boto.setBackground(colorsOriginals.get(boto));
                        }
                    }
                }
            });
            timer.start();

            operador = "";
            cadena_complerta = "";

        } catch (Exception e) {
            pantalla.setText("ERROR");
            playSound("illo.wav");
        }
    }

    private void calcularFactorial() {
        try {
            String text = pantalla.getText();
            if (text.isEmpty()) return;

            int valor = Integer.parseInt(text);  //Ens assegurem que sigui enter

            if (valor < 0) {
                pantalla.setText("ERROR");
                playSound("illo.wav");
                return;
            }

            long resultat = 1;
            for (int i = 2; i <= valor; i++) {
                resultat *= i;
            }

            //Afegim a l’historial
            String operacio = valor + "! = " + resultat;
            historial.append(operacio).append("\n");
            historialArea.setText(historial.toString());

            pantalla.setText(String.valueOf(resultat));

        } catch (Exception e) {
            pantalla.setText("ERROR");
            playSound("illo.wav");
        }
    }

    private JButton crearBotoPersonalitzat(String text, Color colorBase, ActionListener accio) {
        JButton boto = new JButton(text);
        boto.setFont(new Font("Arial", Font.BOLD, 16));
        boto.setPreferredSize(new Dimension(60, 60));
        boto.setBackground(colorBase);
        Color colorOriginal = colorBase;

        boto.addActionListener(accio);

        //Efecte hover
        boto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boto.setBackground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boto.setBackground(colorOriginal);
            }
        });

        //Registrar botó
        totsElsBotons.add(boto);
        colorsOriginals.put(boto, colorOriginal);

        return boto;
    }

    public void playSound(String fileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/" + fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("No s'ha pogut reproduir el so: " + fileName);
        }
    }
}
