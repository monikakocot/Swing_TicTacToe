package com.company.playeVSplayer;

/*
Tic Tac Toe game - version when you can play with another player
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


//JFrame - ready to use Frames; Action Listener - ready for use actions - buttons couses actions

public class TicTacToe extends JFrame implements ActionListener {

    // the list of my buttons

    private int counter = 0;
    private List<JButton> buttons = new ArrayList<>();

    // Main Frame of a Tic Tac Toe game

    public TicTacToe(String title, int width) {
        super(title);
        setSize(width, width);
        setVisible(true);

        for (int i = 1; i <= 9; i++) {
            JButton jButton = new JButton("");
            jButton.addActionListener(this);
/*
Our frame will now be notified each time one of the buttons is pressed and its actionPerformed () method will be called.
The frame is a listener. The listener can also be a Panel or Component.
'this' connect buttons with actions. It is easiest to imagine it as a short conversation:
"You buttons, I know you can be squeezed and let others know about it"
*/
            add(jButton);
            buttons.add(jButton);
        }
        setLayout(new GridLayout(3, 3));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        // for start Swing app
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToe("Tic tac Toe", 500);
            }
        });
    }//

    @Override

    // adding accion for the buttons
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (counter % 2 == 0)
            button.setText("X");
        else
            button.setText("0");
        counter++;
        button.setEnabled(false); // you cannot click twine on the same button

        // to show that sb won

        if (isWinner()) {
            JOptionPane.showMessageDialog(null, "You win!");
        }

        System.out.println("I'm clicked");
    }

    // conditions for winning

    public boolean isWinner() {
        if (isWinner(0, 1, 2)) return true;
        if (isWinner(3, 4, 5)) return true;
        if (isWinner(6, 7, 8)) return true;
        if (isWinner(0, 3, 6)) return true;
        if (isWinner(1, 4, 7)) return true;
        if (isWinner(2, 5, 8)) return true;
        if (isWinner(0, 4, 8)) return true;
        if (isWinner(2, 4, 6)) return true;
        return false;
    }

    // highlight for winning sequence

    public boolean isWinner(int i, int j, int k) {
        if (buttons.get(i).getText().equals(buttons.get(j).getText()) && buttons.get(j).getText().equals(buttons.get(k).getText())
                && !buttons.get(i).getText().equals("")) {

            buttons.get(i).setBackground(Color.green);
            buttons.get(j).setBackground(Color.green);
            buttons.get(k).setBackground(Color.green);

            return true;
        }
        return false;
    }
}