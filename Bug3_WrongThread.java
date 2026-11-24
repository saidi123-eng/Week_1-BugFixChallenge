import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Bug3_WrongThread extends JFrame {

    private JLabel timeLabel;
    private JButton startButton;
    private boolean running = false;

    public Bug3_WrongThread() {
        setTitle("Bug 3 - Wrong Thread");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        timeLabel = new JLabel("Time: ");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        startButton = new JButton("Start Clock");

        startButton.addActionListener(e -> {
            running = true;

            new Thread(() -> {
                while (running) {

                    // FIX: Update the GUI on the EDT
                    SwingUtilities.invokeLater(() -> {
                        timeLabel.setText("Time: " + new Date());
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }).start();
        });

        add(timeLabel);
        add(startButton);

        setSize(350, 150);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bug3_WrongThread().setVisible(true);
        });
    }
}