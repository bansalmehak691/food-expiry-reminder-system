import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class FoodExpiryReminder extends Frame implements ActionListener {

    Label l1, l2, resultLabel;
    TextField t1, t2;
    Button check, reset;
    TextArea display;

    FoodExpiryReminder() {

        setLayout(new FlowLayout());

        // Input Panel
        Panel inputPanel = new Panel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));

        l1 = new Label("Food Item:");
        t1 = new TextField();

        l2 = new Label("Expiry Date (YYYY-MM-DD):");
        t2 = new TextField();

        inputPanel.add(l1);
        inputPanel.add(t1);
        inputPanel.add(l2);
        inputPanel.add(t2);

        // Button Panel
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());

        check = new Button("Check");
        reset = new Button("Reset");

        buttonPanel.add(check);
        buttonPanel.add(reset);

        // RESULT PANEL (Improved Box)
        Panel resultPanel = new Panel();
        resultPanel.setLayout(new GridLayout(2, 1));
        resultPanel.setBackground(Color.LIGHT_GRAY);

        // 🔥 Increased size
        resultPanel.setPreferredSize(new Dimension(450, 90));

        Label title = new Label("Result:");
        
        resultLabel = new Label("No result yet");
        resultLabel.setAlignment(Label.CENTER); // center text
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14)); // bold text

        resultPanel.add(title);
        resultPanel.add(resultLabel);

        // TextArea
        display = new TextArea(6, 40);

        // Add components
        add(inputPanel);
        add(buttonPanel);
        add(resultPanel);
        add(display);

        // Events
        check.addActionListener(this);

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t1.setText("");
                t2.setText("");
                resultLabel.setText("No result yet");
                display.setText("");
            }
        });

        setSize(520, 420);
        setTitle("Food Expiry Reminder");
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String food = t1.getText();
            LocalDate today = LocalDate.now();
            LocalDate expiry = LocalDate.parse(t2.getText());

            String status = "";

            if (expiry.isBefore(today)) {
                status = food + " is EXPIRED ❌";
                resultLabel.setForeground(Color.RED);
            }
            else if (expiry.isEqual(today) || expiry.isEqual(today.plusDays(1))) {
                status = food + " is near expiry ⚠️";
                resultLabel.setForeground(Color.ORANGE);
            }
            else {
                status = food + " is fresh ✅";
                resultLabel.setForeground(Color.GREEN);
            }

            resultLabel.setText(status);
            display.append(status + "\n");

            showPopup(status);

        } catch (Exception ex) {
            showPopup("Invalid Date Format! Use YYYY-MM-DD");
        }
    }

    void showPopup(String message) {
        Dialog d = new Dialog(this, "Alert", true);
        d.setLayout(new FlowLayout());

        Label msg = new Label(message);
        Button ok = new Button("OK");

        ok.addActionListener(e -> d.setVisible(false));

        d.add(msg);
        d.add(ok);

        d.setSize(250, 150);
        d.setVisible(true);
    }

    public static void main(String[] args) {
        new FoodExpiryReminder();
    }
}