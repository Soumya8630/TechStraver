import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private JButton[] numberButtons;
    private JButton[] functionButtons;
    private JButton equalsButton;
    private JButton clearButton;
    private String[] functionLabels = {"+", "-", "*", "/", "sqrt", "sin", "cos", "tan", "log", "ln", "pow","pow2"};

    private double num1, num2, result;
    private String functionLabel;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));

        numberButtons = new JButton[11];
        for (int i = 0; i < 11; i++) {
            numberButtons[i] = new JButton("" + i);
            numberButtons[i].addActionListener(this);
            buttonPanel.add(numberButtons[i]);
        }

        functionButtons = new JButton[functionLabels.length];
        for (int i = 0; i < functionLabels.length; i++) {
            functionButtons[i] = new JButton(functionLabels[i]);
            functionButtons[i].addActionListener(this);
            buttonPanel.add(functionButtons[i]);
        }

        equalsButton = new JButton("=");
        equalsButton.addActionListener(this);
        buttonPanel.add(equalsButton);

        clearButton = new JButton("C");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == clearButton) {
            clearDisplay();
        } else if (source == equalsButton) {
            calculate();
        } else {
            for (int i = 0; i < 11; i++) {
                if (source == numberButtons[i]) {
                    display.setText(display.getText() + i);
                    return;
                }
            }
            for (int i = 0; i < functionLabels.length; i++) {
                if (source == functionButtons[i]) {
                    functionLabel = functionLabels[i];
                    if (functionLabel.equals("sqrt")) {
                        calculate();
                    } else if (functionLabel.equals("sin") || functionLabel.equals("cos") || functionLabel.equals("tan") || functionLabel.equals("log") || functionLabel.equals("ln") || functionLabel.equals("pow") || functionLabel.equals("pow2")) {
                        num1 = Double.parseDouble(display.getText());
                        calculate();
                    } else {
                        if (!display.getText().isEmpty()) {
                            num1 = Double.parseDouble(display.getText());
                            display.setText("");
                        }
                    }
                    return;
                }
            }
        }
    }

    private void calculate() {
        switch (functionLabel) {
            case "+":
            case "-":
            case "*":
            case "/":
                if (!display.getText().isEmpty()) {
                    num2 = Double.parseDouble(display.getText());
                    switch (functionLabel) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                display.setText("Error! Division by zero is not allowed.");
                                return;
                            }
                            break;
                        
                    }
                    display.setText(Double.toString(result));
                }
                break;
            case "sqrt":
                if (!display.getText().isEmpty()) {
                    num1 = Double.parseDouble(display.getText());
                    result = Math.sqrt(num1);
                    display.setText(Double.toString(result));
                }
                break;
            case "sin":
                double num = num1*(Math.PI/180);
                result = Math.sin(num);
                display.setText(Double.toString(result));
                break;
            case "cos":
                double num2 = num1*(Math.PI/180);
                result = Math.cos(num2);
                display.setText(Double.toString(result));
                break;
            case "tan":
                double num3 = num1*(Math.PI/180);
                result = Math.tan(num3);
                display.setText(Double.toString(result));
                break;
            case "log":
                result = Math.log10(num1);
                display.setText(Double.toString(result));
                break;
            case "ln":
                result = Math.log(num1);
                display.setText(Double.toString(result));
                break;
            case "pow":
                if (!display.getText().isEmpty()) {        
                    String userInput = JOptionPane.showInputDialog("Enter the second number:");
                    num2 = Double.parseDouble(userInput);
            
                    // // Prompt the user for the second number
                    // double num4 = Double.parseDouble(userInput);
            
                    // Calculate the result
                    result = Math.pow(num1, num2);
                    display.setText(Double.toString(result));
                }
                break;
            case "pow2":
                if (!display.getText().isEmpty()) {
                    num2 = Double.parseDouble(display.getText());
                    result = Math.pow(num1,2);
                    display.setText(Double.toString(result));
                }
                break;
        }
    }

    private void clearDisplay() {
        display.setText("");
        num1 = 0;
        num2 = 0;
        result = 0;
        functionLabel = "";
    }

    public static void main(String[] args) {
        new ScientificCalculator();
    }
}