
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Puzzle extends Frame implements ActionListener {
    Button[] buttons = new Button[9];
    Button startButton, restartButton;
    boolean solvingStarted = false;
    String playerName = "Player1";

    Puzzle() {
        super("8 Puzzle Game");
        setLayout(null);

        int x = 50, y = 100, size = 40, gap = 50;
        for (int i = 0; i < 9; i++) {
            buttons[i] = new Button(i < 8 ? String.valueOf(i + 1) : "");
            buttons[i].setBounds(x + (i % 3) * gap, y + (i / 3) * gap, size, size);
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }

        startButton = new Button("Start Solving");
        startButton.setBounds(50, 280, 100, 30);
        startButton.addActionListener(e -> {
            solvingStarted = true;
            startButton.setEnabled(false);
            restartButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Start solving the puzzle!");
        });
        add(startButton);

        restartButton = new Button("Restart");
        restartButton.setBounds(160, 280, 60, 30);
        restartButton.setEnabled(false);
        restartButton.addActionListener(e -> restartGame());
        add(restartButton);

        PlayerDB.initializePlayer(playerName);

        setSize(300, 360);
        setVisible(true);
    }

    /**
     * 
     */
    public void restartGame() {
        List<String> nums = new ArrayList<>();
        for (int i = 1; i <= 8; i++) nums.add(String.valueOf(i));
        nums.add("");
        Collections.shuffle(nums);
        for (int i = 0; i < 9; i++) {
            buttons[i].setLabel(nums.get(i));
        }
        solvingStarted = false;
        startButton.setEnabled(true);
        restartButton.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        Button clicked = (Button) e.getSource();
        int i = Arrays.asList(buttons).indexOf(clicked);
        int[][] neighbors = {
            {1, 3}, {0, 2, 4}, {1, 5},
            {0, 4, 6}, {1, 3, 5, 7}, {2, 4, 8},
            {3, 7}, {4, 6, 8}, {5, 7}
        };

        for (int n : neighbors[i]) {
            if (buttons[n].getLabel().equals("")) {
                buttons[n].setLabel(clicked.getLabel());
                clicked.setLabel("");
                break;
            }
        }

        if (solvingStarted && isSolved()) {
            JOptionPane.showMessageDialog(this, "Congratulations! You won.");
            PlayerDB.incrementSolveCount(playerName);
            int count = PlayerDB.getSolveCount(playerName);
            JOptionPane.showMessageDialog(this, "You have solved " + count + " puzzles.");
            solvingStarted = false;
            startButton.setEnabled(true);
            restartButton.setEnabled(false);
        }
    }

    private boolean isSolved() {
        for (int i = 0; i < 8; i++) {
            if (!buttons[i].getLabel().equals(String.valueOf(i + 1))) return false;
        }
        return buttons[8].getLabel().equals("");
    }

    public static void main(String[] args) {
        new Puzzle();
    }
}
