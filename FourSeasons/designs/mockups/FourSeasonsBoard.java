package mockups;

import java.awt.BorderLayout	;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class FourSeasonsBoard extends JFrame {

	private JPanel contentPane;

	/**	
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FourSeasonsBoard frame = new FourSeasonsBoard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FourSeasonsBoard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 703);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel stock = new JPanel();
		stock.setBounds(30, 200, 128, 176);
		contentPane.add(stock);
		
		JPanel waste = new JPanel();
		waste.setBounds(30, 447, 128, 176);
		contentPane.add(waste);
		
		JPanel clubs = new JPanel();
		clubs.setBounds(306, 447, 128, 176);
		contentPane.add(clubs);
		
		JPanel cross5 = new JPanel();
		cross5.setBounds(595, 419, 128, 176);
		contentPane.add(cross5);
		
		JPanel diamonds = new JPanel();
		diamonds.setBounds(882, 447, 128, 176);
		contentPane.add(diamonds);
		
		JPanel cross3 = new JPanel();
		cross3.setBounds(595, 243, 128, 176);
		contentPane.add(cross3);
		
		JPanel cross1 = new JPanel();
		cross1.setBounds(595, 67, 128, 176);
		contentPane.add(cross1);
		
		JPanel hearts = new JPanel();
		hearts.setBounds(306, 30, 128, 176);
		contentPane.add(hearts);
		
		JPanel spades = new JPanel();
		spades.setBounds(882, 30, 128, 176);
		contentPane.add(spades);
		
		JPanel cross4 = new JPanel();
		cross4.setBounds(723, 243, 128, 176);
		contentPane.add(cross4);
		
		JPanel cross2 = new JPanel();
		cross2.setBounds(467, 243, 128, 176);
		contentPane.add(cross2);
		
		JLabel lblStock = new JLabel("STOCK");
		lblStock.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblStock.setBounds(30, 173, 56, 16);
		contentPane.add(lblStock);
		
		JLabel lblWaste = new JLabel("WASTE");
		lblWaste.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblWaste.setBounds(30, 418, 56, 16);
		contentPane.add(lblWaste);
		
		JLabel lblFourseasons = new JLabel("FourSeasons");
		lblFourseasons.setFont(new Font("Times New Roman", Font.PLAIN, 48));
		lblFourseasons.setBounds(30, 30, 248, 77);
		contentPane.add(lblFourseasons);
		
		JLabel lblScore = new JLabel("SCORE");
		lblScore.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblScore.setBounds(1120, 30, 56, 16);
		contentPane.add(lblScore);
		
		JLabel lblCredits = new JLabel("@author Tanuj Sane");
		lblCredits.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblCredits.setBounds(30, 94, 213, 16);
		contentPane.add(lblCredits);
		
		JLabel lblClubs = new JLabel("CLUBS");
		lblClubs.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblClubs.setBounds(306, 418, 56, 16);
		contentPane.add(lblClubs);
		
		JLabel lblHearts = new JLabel("HEARTS");
		lblHearts.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblHearts.setBounds(306, 219, 56, 16);
		contentPane.add(lblHearts);
		
		JLabel lblSpades = new JLabel("SPADES");
		lblSpades.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblSpades.setBounds(954, 219, 56, 16);
		contentPane.add(lblSpades);
		
		JLabel lblDiamonds = new JLabel("DIAMONDS");
		lblDiamonds.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblDiamonds.setBounds(935, 418, 75, 16);
		contentPane.add(lblDiamonds);
		
		JLabel lblCardsLeft = new JLabel("CARDS LEFT");
		lblCardsLeft.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblCardsLeft.setBounds(1120, 151, 86, 16);
		contentPane.add(lblCardsLeft);
		
		JLabel lblScoreNum = new JLabel("00");
		lblScoreNum.setFont(new Font("Times New Roman", Font.PLAIN, 48));
		lblScoreNum.setBounds(1120, 55, 69, 70);
		contentPane.add(lblScoreNum);
		
		JLabel lblCardsLeftNum = new JLabel("00");
		lblCardsLeftNum.setFont(new Font("Times New Roman", Font.PLAIN, 48));
		lblCardsLeftNum.setBounds(1120, 172, 69, 70);
		contentPane.add(lblCardsLeftNum);
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnNewGame.setBounds(1120, 559, 111, 25);
		contentPane.add(btnNewGame);
		
		JButton btnRestartGame = new JButton("Restart Game");
		btnRestartGame.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnRestartGame.setBounds(1120, 598, 111, 25);
		contentPane.add(btnRestartGame);
		
		JButton btnUndoMove = new JButton("Undo Move");
		btnUndoMove.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnUndoMove.setBounds(1120, 321, 111, 25);
		contentPane.add(btnUndoMove);
		
		JButton btnRedoMove = new JButton("Redo Move");
		btnRedoMove.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnRedoMove.setBounds(1120, 359, 111, 25);
		contentPane.add(btnRedoMove);
	}
}
