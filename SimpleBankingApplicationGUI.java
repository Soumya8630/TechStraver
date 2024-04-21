import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SimpleBankingApplicationGUI extends JFrame implements ActionListener {
    private Map<String, Double> balances = new HashMap<>();
    private JTextField nameField;
    private JTextField accountField;
    private JTextField amountField;
    private JTextArea outputArea;

    public SimpleBankingApplicationGUI() {
        setTitle("Simple Banking Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(10);
        JLabel accountLabel = new JLabel("Account Number:");
        accountField = new JTextField(10);
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(accountLabel);
        inputPanel.add(accountField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(depositButton);
        inputPanel.add(withdrawButton);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Deposit":
                deposit();
                break;
            case "Withdraw":
                withdraw();
                break;
        }
    }

    private void deposit() {
        String name = nameField.getText();
        String accountNumber = accountField.getText();
        double amount = Double.parseDouble(amountField.getText());
        
        if (amount > 0) {
            balances.putIfAbsent(name, 0.0); // Initialize balance if not exists
            double currentBalance = balances.get(name);
            currentBalance += amount;
            balances.put(name, currentBalance);
            updateOutput("Deposit successful for " + name + " (Account: " + accountNumber + "). Current balance: " + currentBalance);
        } else {
            updateOutput("Invalid amount. Please enter a positive value.");
        }
        clearFields();
    }

    private void withdraw() {
        String name = nameField.getText();
        String accountNumber = accountField.getText();
        double amount = Double.parseDouble(amountField.getText());
        
        if (balances.containsKey(name)) {
            if (accountNumber.equals(getAccountNumber(name))) {
                double currentBalance = balances.get(name);
                if (amount > 0 && amount <= currentBalance) {
                    currentBalance -= amount;
                    balances.put(name, currentBalance);
                    updateOutput("Withdrawal successful for " + name + " (Account: " + accountNumber + "). Current balance: " + currentBalance);
                } else if (amount > currentBalance) {
                    updateOutput("Insufficient funds. Withdrawal amount exceeds balance.");
                } else {
                    updateOutput("Invalid amount. Please enter a positive value.");
                }
            } else {
                updateOutput("Wrong account number for " + name);
            }
        } else {
            updateOutput("Account not found for name: " + name);
        }
        clearFields();
    }
    private String getAccountNumber(String name) {
        String inputAccountNumber = JOptionPane.showInputDialog("please Re-enter account number for " + name + ":");
        // Simulated database lookup
        Map<String, String> accountNumbers = new HashMap<>();
        // Populate the map with sample data (you can replace this with your actual data)
        accountNumbers.put("Soumodip", "123456");
        accountNumbers.put("Soumya", "654321");
        accountNumbers.put("Sharayu", "987654");
        accountNumbers.put("Somen", "451275");
        accountNumbers.put("Ankit", "582464");
        accountNumbers.put("Bansh", "753159");
        accountNumbers.put("chirag", "159357");
        // Check if the entered account number matches the one associated with the given name
        String storedAccountNumber = accountNumbers.getOrDefault(name, "");
        if (storedAccountNumber.equals(inputAccountNumber)) {
            return inputAccountNumber;
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect account number for " + name + ". Please try again.");
            return null; // Return null if the account number is incorrect
        }
    }
    

    private void updateOutput(String message) {
        outputArea.append(message + "\n");
    }

    private void clearFields() {
        nameField.setText("");
        accountField.setText("");
        amountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleBankingApplicationGUI::new);
    }
}
