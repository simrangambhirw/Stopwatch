package timeclock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerApp {
    private JFrame frame;
    private JTextField timeInput;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JLabel timeDisplay;
    private Thread timerThread;
    private TimerLogic timerLogic;

    public TimerApp() {
        frame = new JFrame("Countdown Timer");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        timeInput = new JTextField(10);
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");
        timeDisplay = new JLabel("00:00");

        startButton.addActionListener(new StartButtonListener());
        stopButton.addActionListener(new StopButtonListener());
        resetButton.addActionListener(new ResetButtonListener());

        frame.add(new JLabel("Enter time (in seconds):"));
        frame.add(timeInput);
        frame.add(startButton);
        frame.add(stopButton);
        frame.add(resetButton);
        frame.add(timeDisplay);

        frame.setVisible(true);
    }

    class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int seconds = Integer.parseInt(timeInput.getText());
                if (seconds <= 0) {
                    throw new NumberFormatException();
                }
                timerLogic = new TimerLogic(seconds, TimerApp.this);
                timerThread = new Thread(timerLogic);
                timerThread.start();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid positive number for the timer.");
            }
        }
    }

    class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timerLogic != null) {
                timerLogic.stopTimer();
            }
        }
    }

    class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timerLogic != null) {
                timerLogic.stopTimer();
            }
            timeInput.setText("");
            timeDisplay.setText("00:00");
        }
    }

    public void updateTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        timeDisplay.setText(String.format("%02d:%02d", min, sec));
    }

    public void timerFinished() {
        JOptionPane.showMessageDialog(frame, "Time's up!");
    }

    public void timerInterrupted() {
        JOptionPane.showMessageDialog(frame, "Timer was stopped.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimerApp());
    }
}
