package eu.popowicz.splitpaymentcalculator;


import com.sun.istack.internal.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplitPaymentCalculatorUI extends JFrame {
    private Container cntKontener;
    private JPanel pnlButtons;
    private JPanel pnlVat;
    private JPanel pnlQuote;
    private JPanel pnlCountDirection;
    private JPanel pnlResults;
    private JButton btnCount;
    private JButton btnClear;
    private JButton btnSaveToFile;
    private JLabel lblQuote;
    private JLabel lblVat;
    private JLabel lblVatValue;
    private JLabel lblResultDesc;
    private JTextArea txtResult;
    private JTextField txtQuote;
    private JRadioButton rbBruttoToNetto;
    private JRadioButton rbNettoToBrutto;
    private JSlider sldVat;
    private int vat;

    private final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public SplitPaymentCalculatorUI() {
        super("Kalkulator P\u0142atno\u015bci Podzielonej");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponent();
    }

    public void setPosition() {
        int frameWidth = this.getSize().width;
        int frameHeight = this.getSize().height;
        this.setLocation((screenWidth - frameWidth) / 3, (screenHeight - frameHeight) / 3);
    }

    private void initComponent() {
        cntKontener = this.getContentPane();
        cntKontener.setLayout(new BoxLayout(cntKontener, BoxLayout.Y_AXIS));

        pnlQuote = new JPanel();
        pnlQuote.setBorder(BorderFactory.createEtchedBorder(1));
        pnlQuote.setLayout(new FlowLayout());
        lblQuote = new JLabel("Podaj kwot\u0119 z\u0142: ");
        txtQuote = new JTextField(15);
        pnlQuote.add(lblQuote);
        pnlQuote.add(txtQuote);
        cntKontener.add(pnlQuote);

        pnlVat = new JPanel();
        pnlVat.setLayout(new FlowLayout());
        sldVat = new JSlider(0, 30, 23);
        vat = sldVat.getValue();
        sldVat.addChangeListener(new listener());
        sldVat.setMinorTickSpacing(2);
        sldVat.setMajorTickSpacing(10);
        sldVat.setPaintTicks(true);
        sldVat.setPaintLabels(true);
        lblVat = new JLabel("Ustaw % Vat: ");
        lblVatValue = new JLabel(vat + " %");
        lblVatValue.setOpaque(true);
        lblVatValue.setBackground(Color.orange);
        lblVatValue.setPreferredSize(new Dimension(50, 30));
        lblVatValue.setHorizontalAlignment(SwingConstants.CENTER);

        pnlVat.add(lblVat);
        pnlVat.add(sldVat);
        pnlVat.add(lblVatValue);
        cntKontener.add(pnlVat);

        rbBruttoToNetto = new JRadioButton("Brutto na netto", true);
        rbNettoToBrutto = new JRadioButton("Netto na brutto");
        ButtonGroup bgrCountDirections = new ButtonGroup();
        bgrCountDirections.add(rbBruttoToNetto);
        bgrCountDirections.add(rbNettoToBrutto);

        pnlCountDirection = new JPanel();
        pnlCountDirection.setBorder(BorderFactory.createEtchedBorder(1));
        pnlCountDirection.setLayout(new FlowLayout());
        pnlCountDirection.add(rbBruttoToNetto);
        pnlCountDirection.add(rbNettoToBrutto);
        cntKontener.add(pnlCountDirection);

        pnlResults = new JPanel();
        lblResultDesc = new JLabel("Wynik:");
        txtResult = new JTextArea(3, 25);
        pnlResults.add(lblResultDesc);
        txtResult.setEnabled(false);
        txtResult.setFont(new Font("Tahoma",Font.PLAIN,11 ));
        pnlResults.add(txtResult);
        pnlResults.setLayout(new FlowLayout());
        cntKontener.add(pnlResults);

        btnCount = new JButton("Oblicz");
        btnCount.addActionListener(new btnCountListener());
        btnCount.setPreferredSize(new Dimension(150, 30));
        btnClear = new JButton("Wyczy\u015b\u0107");
        btnClear.addActionListener(new btnClearListener());
        btnClear.setPreferredSize(new Dimension(150, 30));
        btnSaveToFile = new JButton("Zapisz do pliku");

        /*btnSaveToFile.addActionListener(new btnSaveToFileListener());
        btnSaveToFile.setPreferredSize(new Dimension(120, 30));*/
        pnlButtons = new JPanel();
        pnlButtons.setBorder(BorderFactory.createEtchedBorder(1));
        pnlButtons.setLayout(new FlowLayout());
        pnlButtons.add(btnCount);
        pnlButtons.add(btnClear);
        //pnlButtons.add(btnSaveToFile);
        cntKontener.add(pnlButtons);

        JLabel footer = new JLabel("(c) 2020 Marek Popowicz");
        footer.setFont(new Font("Tahoma",Font.PLAIN,11 ));
        add(footer).setPreferredSize(new Dimension(150,20));
    }

    public void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception exc) {
            System.err.println("Nie potrafię wczytać systemowego wyglądu: " + exc);
        }
    }

    private class listener implements ChangeListener {
        @Override
        public void stateChanged(@NotNull ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();
            if (!slider.getValueIsAdjusting()) {
                vat = (int) slider.getValue();
                lblVatValue.setText(vat + " %");
            }
        }
    }

    private class btnCountListener extends Component implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                if (rbBruttoToNetto.isSelected()) {
                    txtResult.setText(new FromBruttoToNetto(validateQuote(txtQuote.getText()), vat).getResults());
                }
                if (rbNettoToBrutto.isSelected()) {
                    txtResult.setText(new FromNettoToBrutto(validateQuote(txtQuote.getText()), vat).getResults());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Niew\u0142a\u015bciwy format danych.\nPodaj dodatni\u0105 warto\u015b\u0107 liczbow\u0105.","Informacja", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    @NotNull
    private String validateQuote(@NotNull String quote){
        if (quote.contains(","))
            quote = quote.replace(",",".");
        return quote;
    }

    private class btnClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            txtQuote.setText("");
            txtResult.setText("");
        }
    }

   /* private class btnSaveToFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            }
        }
    }*/
}
