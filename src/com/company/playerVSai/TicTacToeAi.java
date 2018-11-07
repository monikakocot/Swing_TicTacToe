package com.company.playerVSai;

/*
Tic Tac Toe game - version when you can play with created ComputerAI

*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeAi extends JFrame implements ActionListener {

    private int counter = 0;
    private List<JButton> buttons = new ArrayList<>();
    private boolean[] availableButtons = new boolean[9]; //list of available buttons(T/F)
    private String[] board = new String[9]; // list type String of buttons

    public TicTacToeAi(String title, int width) {
        super(title); // import from abstract class JFrame
        setSize(width, width); //(method JFrame)
        setVisible(true); //(method JFrame)
        for (int i = 0; i < 9; i++) {
            JButton jButton = new JButton("");
            jButton.addActionListener(this);
            add(jButton);
            buttons.add(jButton);
            availableButtons[i] = true; // at the beggining all button are avaible to click
        }
        setLayout(new GridLayout(3, 3));

        // The starting player's random method

        counter = ((Math.random() < 0.5) ? 0 : 1);
        if (counter == 0) {
            System.out.println("Start X");
        } else {
            System.out.println("Start O");
        }
    }

    // adding accion for the buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (counter % 2 == 0) { // pętla wyświetlająca naprzemiennie X i O
            JButton button = (JButton) e.getSource(); // pokazuje, który obiekt jest wciśnięty
            button.setText("X");
            System.out.println("Clicked X");
            button.setEnabled(false); // you cannot click twine on the same button
        } else {
            for (int i = 0; i < 9; i++) {
                board[i] = buttons.get(i).getText();
            }
            int choose = computerAI();
            buttons.get(choose).setText("O");
            buttons.get(choose).setEnabled(false);
        }
        System.out.println("Movement has been done");


        //method for show the winner

        if (isWinner()) {
            String winner = "";
            if (counter % 2 == 0) // pętla wyświetlająca naprzemiennie X i O
                winner = ("X");
            else
                winner = ("O");
            JOptionPane.showMessageDialog(null, "Game over! The winner is: " + winner);
        }
        counter++;
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
        if (buttons.get(i).getText().equals(buttons.get(j).getText()) && buttons.get(i).getText().equals(buttons.get(k).getText()) && !buttons.get(i).getText().equals("")) {
            buttons.get(i).setBackground(Color.green);
            buttons.get(j).setBackground(Color.green);
            buttons.get(k).setBackground(Color.green);

            return true;
        }
        return false;
    }

    //ComputerAI:

    public int computerAI() {
        int[] weigth = {3000, 2000, 3000, 2000, 4000, 2000, 3000, 2000, 3000};
        for (int i = 0; i < 9; i++) {
            weigth[i] += (Math.random() * 100);
            System.out.print(i + ":" + weigth[i] + ",");
        }
        System.out.println(" Initial random - first random move of the Computer");
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < 8; i++) {
            int[] improve = WinnerAI("X", i);
            if (improve[3] != 1) {
                weigth[improve[0]] += 100000;
                weigth[improve[1]] += 100000;
                weigth[improve[2]] += 100000;
            }
        }
        for (int i = 0; i < 9; i++) {
            System.out.print(i + ":" + weigth[i] + ",");
        }

        System.out.println(" Checking the threat on the X + side where an X  may occur");

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < 8; i++) {
            int[] improve2 = WinnerAI("O", i);
            if (improve2[3] != 1) {
                weigth[improve2[0]] += 1000000;
                weigth[improve2[1]] += 1000000;
                weigth[improve2[2]] += 1000000;
            }
        }
        for (int i = 0; i < 9; i++) {
            System.out.print(i + ":" + weigth[i] + ",");
        }

        System.out.println(" Checking  for a possible win - where can the string O appear");

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < 8; i++) {
            int[] decrease = iCanWinThis("X", i);
            if (decrease[3] != 1) {
                weigth[decrease[0]] += 10000;
                weigth[decrease[1]] += 10000;
                weigth[decrease[2]] += 10000;
            }
        }
        for (int i = 0; i < 9; i++) {
            System.out.print(i + ":" + weigth[i] + ",");
        }


        System.out.println(" Checking potential paths for the X victory ");

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        int max = 0;
        int maxI = 0;
        for (int i = 0; i < 9; i++) {
            if (max < weigth[i] && buttons.get(i).isEnabled() == true) {
                max = weigth[i];
                maxI = i;
            } else if (buttons.get(i).isEnabled() == false){
                weigth[i] = 0000;
            }
        }
        for (int i = 0; i < 9; i++) {
            System.out.print(i + ":" + weigth[i] + ",");
        }
        System.out.println(" After eliminating the occupied buttons");
        return maxI;
        ////////////////////////////////////////////////////////////////////////////////////////////////////
    }


// 8.	Method isWinner AI - SPECYFIC CASES

    public int[] WinnerAI(String player, int n) {
        int[] move = {9, 9, 9, 1};
        switch (n) {
            case 0:
                if (isWinnerAI(0, 1, 2, player)) {
                    move[0] = 0;
                    move[1] = 1;
                    move[2] = 2;
                    move[3] = 0;
                }
                break;
            case 1:
                if (isWinnerAI(3, 4, 5, player)) {
                    move[0] = 3;
                    move[1] = 4;
                    move[2] = 5;
                    move[3] = 0;
                }
                break;
            case 2:
                if (isWinnerAI(6, 7, 8, player)) {
                    move[0] = 6;
                    move[1] = 7;
                    move[2] = 8;
                    move[3] = 0;
                }
                break;
            case 3:
                if (isWinnerAI(0, 3, 6, player)) {
                    move[0] = 0;
                    move[1] = 3;
                    move[2] = 6;
                    move[3] = 0;
                }
                break;
            case 4:
                if (isWinnerAI(1, 4, 7, player)) {
                    move[0] = 1;
                    move[1] = 4;
                    move[2] = 7;
                    move[3] = 0;
                }
                break;
            case 5:
                if (isWinnerAI(2, 5, 8, player)) {
                    move[0] = 2;
                    move[1] = 5;
                    move[2] = 8;
                    move[3] = 0;
                }
                break;
            case 6:
                if (isWinnerAI(0, 4, 8, player)) {
                    move[0] = 0;
                    move[1] = 4;
                    move[2] = 8;
                    move[3] = 0;
                }
                break;
            case 7:
                if (isWinnerAI(2, 4, 6, player)) {
                    move[0] = 2;
                    move[1] = 4;
                    move[2] = 6;
                    move[3] = 0;
                }
                break;
        }
        return move;
    }



    public boolean isWinnerAI(int i, int j, int k, String player) {
        if ((buttons.get(i).getText().equals(buttons.get(j).getText()) && (buttons.get(i).getText().equals(player) && buttons.get(j).getText().equals(player)) && buttons.get(k).isEnabled() == true) ||
                (buttons.get(i).getText().equals(buttons.get(k).getText()) && (buttons.get(i).getText().equals(player) && buttons.get(k).getText().equals(player)) && buttons.get(j).isEnabled() == true) ||
                (buttons.get(k).getText().equals(buttons.get(j).getText()) && (buttons.get(k).getText().equals(player) && buttons.get(j).getText().equals(player))) && buttons.get(i).isEnabled() == true) {
            return true;
        }
        return false;
    }

// Method isXHere


    public boolean isXHere(int i, int j, int k, String iks) {
        if ((buttons.get(i).getText().equals(iks)) || (buttons.get(j).getText().equals(iks)) || (buttons.get(k).getText().equals(iks))) {
            return true;
        }
        return false;
    }

    //  Methof  iCanWinThis:

    public int[] iCanWinThis(String player, int n) {
        int[] move = {9, 9, 9, 1};
        if (n == 0) {
            if (isXHere(0, 1, 2, player)) {
                move[0] = 0;
                move[1] = 1;
                move[2] = 2;
                move[3] = 0;
            }
        } else if (n == 1) {
            if (isXHere(3, 4, 5, player)) {
                move[0] = 3;
                move[1] = 4;
                move[2] = 5;
                move[3] = 0;
            }
        } else if (n == 2) {
            if (isXHere(6, 7, 8, player)) {
                move[0] = 6;
                move[1] = 7;
                move[2] = 8;
                move[3] = 0;
            }
        } else if (n == 3) {
            if (isXHere(0, 3, 6, player)) {
                move[0] = 0;
                move[1] = 3;
                move[2] = 6;
                move[3] = 0;
            }
        } else if (n == 4) {
            if (isXHere(1, 4, 7, player)) {
                move[0] = 1;
                move[1] = 4;
                move[2] = 7;
                move[3] = 0;
            }
        } else if (n == 5) {
            if (isXHere(2, 5, 8, player)) {
                move[0] = 2;
                move[1] = 5;
                move[2] = 8;
                move[3] = 0;
            }
        } else if (n == 6) {
            if (isXHere(0, 4, 8, player)) {
                move[0] = 0;
                move[1] = 4;
                move[2] = 8;
                move[3] = 0;
            }
        } else if (n == 7) {
            if (isXHere(2, 4, 6, player)) {
                move[0] = 2;
                move[1] = 4;
                move[2] = 6;
                move[3] = 0;
            }
        }
        return move;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToeAi("Tic Tac Toe",500);
            }
        });
    }
}

