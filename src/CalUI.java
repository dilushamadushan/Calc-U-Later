
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalUI extends JFrame {
    private JPanel mainPane;
    private JButton a1Button,
            buttonMulti, a2Button, a3Button,
            a4Button, a5Button, a6Button,
            buttonDivid, a7Button, a8Button,
            a9Button, buttonSub, dotBtn,
            a0Button, buttonEquel, buttonAdd;
    private JTextField value;
    private JButton cButton;
    private JPanel conPane;

    private String currentInput = "";
    private double firstValue = 0;
    private String operator = "";
    private boolean newInput = true;

    public CalUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Calculator");

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        conPane.setPreferredSize(new Dimension(280, 360));
        mainPanel.add(conPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        value.setHorizontalAlignment(JTextField.RIGHT);
        value.setEditable(false);
        value.setFont(new Font("Arial", Font.BOLD, 20));
        value.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        Font btnFont = new Font("Arial", Font.BOLD, 18);
        Color numberColor = new Color(60, 60, 60);
        Color operatorColor = new Color(0, 120, 215);
        Color specialColor = new Color(200, 80, 80);

        JButton[] numberBtn = {
                a0Button, a1Button, a2Button, a3Button, a4Button,
                a5Button, a6Button, a7Button, a8Button, a9Button, dotBtn
        };

        for (JButton btn : numberBtn) {
            btn.setFont(btnFont);
            btn.setBackground(Color.white);
            btn.setForeground(numberColor);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }

        JButton[] operatorButtons = {buttonAdd, buttonSub, buttonMulti, buttonDivid};
        for (JButton button : operatorButtons) {
            button.setFont(btnFont);
            button.setBackground(new Color(230, 240, 255));
            button.setForeground(operatorColor);
            button.setFocusPainted(false);
        }

        cButton.setFont(btnFont);
        cButton.setBackground(new Color(255, 230, 230));
        cButton.setForeground(specialColor);

        buttonEquel.setFont(btnFont);
        buttonEquel.setBackground(new Color(0, 120, 215));
        buttonEquel.setForeground(Color.WHITE);

        a0Button.addActionListener(e -> appendToValue("0"));
        a1Button.addActionListener(e -> appendToValue("1"));
        a2Button.addActionListener(e -> appendToValue("2"));
        a3Button.addActionListener(e -> appendToValue("3"));
        a4Button.addActionListener(e -> appendToValue("4"));
        a5Button.addActionListener(e -> appendToValue("5"));
        a6Button.addActionListener(e -> appendToValue("6"));
        a7Button.addActionListener(e -> appendToValue("7"));
        a8Button.addActionListener(e -> appendToValue("8"));
        a9Button.addActionListener(e -> appendToValue("9"));

        buttonAdd.addActionListener(e -> setOperator("+"));
        buttonSub.addActionListener(e -> setOperator("-"));
        buttonMulti.addActionListener(e -> setOperator("*"));
        buttonDivid.addActionListener(e -> setOperator("/"));
        dotBtn.addActionListener(e -> appendDecimal());

        buttonEquel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                call();
            }
        });
        cButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }

    private void appendDecimal() {
        if (currentInput.isEmpty()) {
            currentInput = "0.";
        } else if (currentInput.contains(".")) {
            return;
        } else {
            currentInput += ".";
        }

        value.setText(currentInput);
        newInput = false;
    }

    private void setOperator(String operator) {
        if (!currentInput.isEmpty()) {
            try {
                firstValue = Double.parseDouble(currentInput);
                this.operator = operator;
                currentInput = "";
                newInput = true;
                value.setText(formatNumber(firstValue) + " " + operator);
            } catch (Exception ex) {
                value.setText("Invalid Input");
                currentInput = "";
                operator = "";
            }
        }
    }

    private void call() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondNum = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstValue + secondNum;
                    break;
                case "-":
                    result = firstValue - secondNum;
                    break;
                case "*":
                    result = firstValue * secondNum;
                    break;
                case "/":
                    if (secondNum == 0) {
                        value.setText("Cannot divide by zero");
                        return;
                    } else {
                        result = firstValue / secondNum;
                    }
                    break;
                default:
            }
            value.setText(Double.toString(result));
            currentInput = String.valueOf(result);
            operator = "";
            newInput = true;
        }
    }

    private void appendToValue(String text) {
        currentInput += text;
        value.setText(currentInput);
    }

    private void clear() {
        value.setText("");
        currentInput = "";
        operator = "";
        firstValue = 0;
    }

    private String formatNumber(double num) {
        if (num == (long) num) {
            return String.format("%d", (long) num);
        }
        return String.format("%s", num);
    }
}
