import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {

    private JButton[][] buttons;
    private JButton quitButton;
    private char currentPlayer;
    private int movesMade;

    public TicTacToeFrame() {
        initializeUI();
        currentPlayer = 'X';
        movesMade = 0;
    }

    private void initializeUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3));

        buttons = new JButton[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].addActionListener(new ButtonClickListener());
                add(buttons[row][col]);
            }
        }

        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        add(quitButton);

        setSize(300, 400);
        setLocationRelativeTo(null);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            int row = -1, col = -1;
            // Find the clicked button's position
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j] == clickedButton) {
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
            if (row == -1 || col == -1) {
                JOptionPane.showMessageDialog(null, "Internal error occurred.");
                return;
            }

            if (clickedButton.getText().isEmpty()) { // Check if the button is not already marked
                clickedButton.setText(Character.toString(currentPlayer));
                movesMade++;
                if (checkForWin(row, col)) {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    resetBoard();
                } else if (movesMade == 9) {
                    JOptionPane.showMessageDialog(null, "It's a tie!");
                    resetBoard();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch player
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move!");
            }
        }
    }

    private boolean checkForWin(int row, int col) {
        String symbol = String.valueOf(currentPlayer);


        if (buttons[row][(col + 1) % 3].getText().equals(symbol) &&
                buttons[row][(col + 2) % 3].getText().equals(symbol)) {
            return true;
        }

        if (buttons[(row + 1) % 3][col].getText().equals(symbol) &&
                buttons[(row + 2) % 3][col].getText().equals(symbol)) {
            return true;
        }

        if (row == col) {
            if (buttons[(row + 1) % 3][(col + 1) % 3].getText().equals(symbol) &&
                    buttons[(row + 2) % 3][(col + 2) % 3].getText().equals(symbol)) {
                return true;
            }
        }
        if (row + col == 2) {
            if (buttons[(row + 1) % 3][(col - 1 + 3) % 3].getText().equals(symbol) &&
                    buttons[(row + 2) % 3][(col - 2 + 3) % 3].getText().equals(symbol)) {
                return true;
            }
        }
        return false;
    }


    private void resetBoard() {
        currentPlayer = 'X';
        movesMade = 0;
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    buttons[row][col].setText("");
                }
            }
        } else {
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToeFrame().setVisible(true);
            }
        });
    }}