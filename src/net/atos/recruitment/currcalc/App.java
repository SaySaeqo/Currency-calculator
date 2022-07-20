package net.atos.recruitment.currcalc;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class App {

    private JPanel main_pane;
    private JFormattedTextField input;
    private JComboBox currencySelect1;
    private JComboBox currencySelect2;
    private JTextField output;
    private Calculator calculator = new Calculator("data.xml");

    public App() {
        for (String currency : calculator.getAllSupportedCurrencies()) {
            currencySelect1.addItem(currency);
            currencySelect2.addItem(currency);
        }

        currencySelect1.setSelectedItem("USD");
        currencySelect2.setSelectedItem("PLN");

        currencySelect1.addActionListener((e) -> computeOutput());
        currencySelect2.addActionListener((e) -> computeOutput());
        input.addActionListener((e) -> computeOutput());
    }

    public void computeOutput() {
        try {
            double value = Double.parseDouble(input.getText().replace(",", "."));
            value = calculator.calc(
                    currencySelect1.getSelectedItem().toString(),
                    currencySelect2.getSelectedItem().toString(),
                    value
            );
            output.setText(String.valueOf(value));
        } catch (NumberFormatException ex) {
            if (!input.getText().isEmpty()) output.setText("Wrong number format.");
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Kalkulator walut");
        frame.setContentPane(new App().main_pane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        NumberFormat nf = new DecimalFormat("#0.00");
        NumberFormatter nfr = new NumberFormatter(nf);
        nfr.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        input = new JFormattedTextField(nfr);

    }
}
