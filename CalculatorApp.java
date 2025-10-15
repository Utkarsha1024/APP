import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

/**
 * A GUI Calculator application built using Java Swing.
 * This class handles the user interface and the logic for arithmetic operations.
 */
public class CalculatorApp implements ActionListener {

    // --- Swing Components ---
    JFrame frame;
    JTextField textfield;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[9];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, acButton, percentButton;
    JPanel panel;

    // --- Fonts and Colors ---
    Font myFont = new Font("Arial", Font.BOLD, 30);
    Font textFont = new Font("Arial", Font.PLAIN, 45);
    Color darkGray = new Color(50, 50, 50);
    Color lightGray = new Color(160, 160, 160);
    Color orange = new Color(255, 159, 10);

    // --- Logic Variables ---
    double num1 = 0, num2 = 0, result = 0;
    char operator;

    /**
     * A custom JButton class that is rendered as a circle.
     */
    private static class RoundButton extends JButton {
        public RoundButton(String label) {
            super(label);
            setContentAreaFilled(false); // Make the button transparent
            setFocusPainted(false);      // Remove the focus highlight
            setBorderPainted(false);     // Remove the default border
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isArmed()) {
                g2.setColor(getBackground().darker()); // Color when pressed
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter()); // Color on hover
            } else {
                g2.setColor(getBackground()); // Default color
            }

            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            size.width = size.height = Math.max(size.width, size.height);
            return size;
        }

        @Override
        public boolean contains(int x, int y) {
            // Hit detection for the circular shape
            return new Ellipse2D.Float(0, 0, getWidth(), getHeight()).contains(x, y);
        }
    }


    /**
     * Constructor to set up the calculator GUI and initialize components.
     */
    public CalculatorApp() {

        // --- Frame Setup ---
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.BLACK);

        // --- Textfield (Display) Setup ---
        textfield = new JTextField();
        textfield.setBounds(30, 40, 340, 70);
        textfield.setFont(textFont);
        textfield.setEditable(false);
        textfield.setHorizontalAlignment(JTextField.RIGHT);
        textfield.setBackground(Color.BLACK);
        textfield.setForeground(Color.WHITE);
        textfield.setBorder(BorderFactory.createEmptyBorder());

        // --- Button Initialization ---
        addButton = new RoundButton("+");
        subButton = new RoundButton("-");
        mulButton = new RoundButton("×");
        divButton = new RoundButton("÷");
        decButton = new RoundButton(".");
        equButton = new RoundButton("=");
        delButton = new RoundButton("⌫");
        acButton = new RoundButton("AC");
        percentButton = new RoundButton("%");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = acButton;
        functionButtons[8] = percentButton;

        for (int i = 0; i < 9; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setFocusable(false);
        }

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new RoundButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBackground(darkGray);
            numberButtons[i].setForeground(Color.WHITE);
        }

        // Style special function buttons
        acButton.setBackground(lightGray);
        acButton.setForeground(Color.BLACK);
        delButton.setBackground(lightGray);
        delButton.setForeground(Color.BLACK);
        percentButton.setBackground(lightGray);
        percentButton.setForeground(Color.BLACK);
        decButton.setBackground(darkGray);
        decButton.setForeground(Color.WHITE);

        // Style operator buttons
        addButton.setBackground(orange);
        addButton.setForeground(Color.WHITE);
        subButton.setBackground(orange);
        subButton.setForeground(Color.WHITE);
        mulButton.setBackground(orange);
        mulButton.setForeground(Color.WHITE);
        divButton.setBackground(orange);
        divButton.setForeground(Color.WHITE);
        equButton.setBackground(orange);
        equButton.setForeground(Color.WHITE);


        // --- Panel for Buttons ---
        panel = new JPanel();
        panel.setBounds(30, 130, 340, 370);
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        panel.setBackground(Color.BLACK);

        // --- Adding Buttons to the Panel (Row by Row) ---
        // Row 1
        panel.add(acButton);
        panel.add(delButton);
        panel.add(percentButton);
        panel.add(divButton);
        // Row 2
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        // Row 3
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        // Row 4
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        // Row 5
        panel.add(numberButtons[0]);
        // Make the Zero button span two columns
        // For simplicity, we'll keep the grid layout and add a placeholder.
        // A more complex layout manager would be needed for a true wide button.
        panel.add(new JLabel()); // Empty space
        panel.add(decButton);
        panel.add(equButton);

        // --- Finalizing Frame ---
        frame.add(panel);
        frame.add(textfield);
        frame.setVisible(true);
    }

    /**
     * The main entry point for the application.
     */
    public static void main(String[] args) {
        // Ensure UI updates are on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new CalculatorApp());
    }

    /**
     * Handles action events from the calculator buttons.
     * @param e The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // --- Number Buttons ---
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (textfield.getText().equals("Error")) textfield.setText("");
                textfield.setText(textfield.getText().concat(String.valueOf(i)));
            }
        }
        // --- Decimal Button ---
        if (e.getSource() == decButton) {
            if (textfield.getText().equals("Error")) textfield.setText("");
            if (!textfield.getText().contains(".")) {
                textfield.setText(textfield.getText().concat("."));
            }
        }
        // --- Operator Buttons ---
        if (e.getSource() == addButton || e.getSource() == subButton || e.getSource() == mulButton || e.getSource() == divButton) {
            if (!textfield.getText().isEmpty() && !textfield.getText().equals("Error")) {
                num1 = Double.parseDouble(textfield.getText());
                if (e.getSource() == addButton) operator = '+';
                if (e.getSource() == subButton) operator = '-';
                if (e.getSource() == mulButton) operator = '*';
                if (e.getSource() == divButton) operator = '/';
                textfield.setText("");
            }
        }
        // --- Equals Button ---
        if (e.getSource() == equButton) {
            if (!textfield.getText().isEmpty() && !textfield.getText().equals("Error")) {
                num2 = Double.parseDouble(textfield.getText());
                switch (operator) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '/':
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            textfield.setText("Error");
                            return;
                        }
                        break;
                }
                // Format to avoid long decimal places like .0
                if (result == (long) result) {
                    textfield.setText(String.format("%d", (long)result));
                } else {
                    textfield.setText(String.format("%s", result));
                }
                num1 = result;
            }
        }
        // --- AC (All Clear) Button ---
        if (e.getSource() == acButton) {
            textfield.setText("");
            num1 = 0;
            num2 = 0;
            result = 0;
            operator = '\0';
        }
        // --- Delete Button ---
        if (e.getSource() == delButton) {
            String string = textfield.getText();
            if (!string.isEmpty() && !string.equals("Error")) {
                textfield.setText(string.substring(0, string.length() - 1));
            }
        }
        // --- Percent Button ---
        if (e.getSource() == percentButton) {
            if (!textfield.getText().isEmpty() && !textfield.getText().equals("Error")) {
                double temp = Double.parseDouble(textfield.getText());
                temp /= 100;
                textfield.setText(String.valueOf(temp));
            }
        }
    }
}

