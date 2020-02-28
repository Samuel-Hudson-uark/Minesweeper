package Main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Minesweeper {
	
	public static int SIZE = 20;
	public static char[] answerChar;
	public static JPanel panel = new JPanel(new GridLayout(SIZE, SIZE));
	public static JButton[] buttons = new JButton[SIZE*SIZE];
	
	public static boolean[] MakeRandomBoard(double difficulty) {
		boolean[] board = new boolean[SIZE*SIZE];
		int randomPos;
		if(difficulty >= 1 || difficulty < 0)
			difficulty = 0.5;
		for(int i = 0; i < (SIZE*SIZE)*difficulty; i++) {
			randomPos = (int)(Math.random()*(SIZE*SIZE));
			//System.out.println(randomPos);
			if(!board[randomPos])
				board[randomPos] = true;
			else
				i--;
		}
		return board;
	}
	
	public static char[] MakeAnswerArray(boolean[] ans) {
		char[] array = new char[SIZE*SIZE];
		int x, y;
		for(int i = 0; i < SIZE*SIZE; i++) {
			if(ans[i])
				array[i] = 'X';
			else {
				x = i%SIZE;
				y = i/SIZE;
				char value = '0';
				for(int j = y-1; j <= y+1; j++) {
					//JOptionPane.showMessageDialog(null, "y="+j);
					if(j >= 0 && j < SIZE) {
						for(int k = x-1; k <= x+1; k++) {
							//JOptionPane.showMessageDialog(null, "x="+k);
							if(k >= 0 && k < SIZE) {
								//JOptionPane.showMessageDialog(null, j*SIZE+k);
								if(ans[j*SIZE+k])
									value++;
							}
						}
					}
				}
				array[i] = value;
			}
		}
		return array;
	}
	
	public static void ButtonLogic(int choice) {
		if(buttons[choice].isContentAreaFilled()) {
			if(answerChar[choice] == '0') {
				buttons[choice].setContentAreaFilled(false);
				int x = choice%SIZE;
				int y = choice/SIZE;
				for(int j = y-1; j <= y+1; j++) {
					if(j >= 0 && j < SIZE) {
						for(int k = x-1; k <= x+1; k++) {
							if(k >= 0 && k < SIZE) {
								ButtonLogic(j*SIZE+k);
							}
						}
					}
				}
			}
			else if(answerChar[choice] == 'X') {
				JOptionPane.showMessageDialog(null, "You hit a mine! You lose.");
				System.exit(0);
			}
			else {
				buttons[choice].setText(String.valueOf(answerChar[choice]));
			}
		}
	}
	
	public static ActionListener buttonPress = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int choice = Integer.parseInt(e.getActionCommand());
			ButtonLogic(choice);
		}
	};
	
	public static void main(String[] args) {
		int i;
		double difficulty = 0.5;
		boolean[] answerBool = MakeRandomBoard(difficulty);
		answerChar = MakeAnswerArray(answerBool);
		JFrame frame = new JFrame("Minesweeper");
		String desc = " ";
		for(i = 0; i < (buttons.length); i++) {
			buttons[i] = new JButton(desc);
			buttons[i].setActionCommand(Integer.toString(i));
			buttons[i].addActionListener(buttonPress);
			panel.add(buttons[i]);
		}
		frame.setSize(600, 600);
		panel.setSize(frame.getSize());
		panel.setBackground(Color.GRAY);
		frame.add(panel);
		frame.setVisible(true);
	}
}
