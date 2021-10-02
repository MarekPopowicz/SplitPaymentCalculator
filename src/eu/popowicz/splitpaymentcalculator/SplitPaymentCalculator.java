package eu.popowicz.splitpaymentcalculator;

public class SplitPaymentCalculator {
    public static void main(String[] args) {
        SplitPaymentCalculatorUI spc = new SplitPaymentCalculatorUI();
        spc.setLookAndFeel();
        spc.setResizable(false);
        spc.setVisible(true);
        spc.setPosition();
        spc.pack();
    }
}
