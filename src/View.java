import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

	private Controller controller;
	private Model model;

	private JButton[] buttons = new JButton[9];
	private JButton resetButton;

	private Container contentPane;

	private JLabel player;

	private static final int WIDTH_WINDOW = 200,
		HEIGHT_WINDOW = WIDTH_WINDOW + 50,

		HEIGHT_FIELD_ROW = 45,
		WIDTH_FIELD_COLUMN = 45
		;

	private int currentPlayer = 0;


	View(String title, Controller controller) {
		super(title);
		this.controller = controller;
		this.model = this.controller.getModel();
		this.initView();
	}

	View(Controller controller) {
		super();
		this.controller = controller;
		this.model = this.controller.getModel();
		this.initView();
	}

	public void initView() {
		this.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.contentPane = this.getContentPane();
		this.contentPane.setLayout(null);

		// Labels
		JLabel title = new JLabel("Tic-Tac-Toe");
		title.setBounds(0, 0, WIDTH_WINDOW - 15, 40);
		title.setFont(new Font("Default", Font.PLAIN, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(title);

		this.player = new JLabel((currentPlayer == 0 ? "O" : "X") + "'s turn");
		this.player.setBounds(0, 40, WIDTH_WINDOW - 15, 15);
		this.player.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(this.player);

		this.drawField();

		this.setVisible(true);
	}

	private void drawField() {

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				int index = y * 3 + x;

				JButton button = new JButton("");
				button.setBorderPainted(false);
				button.setFocusPainted(false);
				button.setContentAreaFilled(false);
				button.setBounds(x * WIDTH_FIELD_COLUMN + 25, 60 + HEIGHT_FIELD_ROW * y, WIDTH_FIELD_COLUMN, HEIGHT_FIELD_ROW);
				contentPane.add(button);

				if (y != 2) {
					JSeparator separator = new JSeparator();
					separator.setBounds(x * WIDTH_FIELD_COLUMN + 25, 60 + HEIGHT_FIELD_ROW * y + HEIGHT_FIELD_ROW, WIDTH_FIELD_COLUMN, 2);
					contentPane.add(separator);
				}

				if (x != 2) {
					JSeparator separator = new JSeparator();
					separator.setBounds(x * WIDTH_FIELD_COLUMN + 25 + WIDTH_FIELD_COLUMN, 60 + HEIGHT_FIELD_ROW * y, 2, HEIGHT_FIELD_ROW);
					separator.setOrientation(SwingConstants.VERTICAL);
					contentPane.add(separator);
				}

				this.buttons[index] = button;

				button.addActionListener(e -> {
					if (!model.fieldEmpty(index)) return;
					boolean res = model.Turn(index, currentPlayer);
					if (!res) return;
					button.setText(currentPlayer == 0 ? "O" : "X");

					model.checkWin(currentPlayer == 0 ? 0 : 9);
					if (model.getWinFlag() != WinStatusFlag.NONE) {
						this.handleWin(model.getWinFlag());
						return;
					}

					currentPlayer = currentPlayer == 0 ? 1 : 0;
					player.setText((currentPlayer == 0 ? "O" : "X") + "'s turn");
				});
			}
		}
	}

	public void handleWin(WinStatusFlag winFlag) {

		for (JButton button : buttons) {
			button.setEnabled(false);
		}

		this.setSize(WIDTH_WINDOW, HEIGHT_WINDOW + 50);

		this.resetButton = new JButton("Restart");
		this.resetButton.setBounds(15, 70 + HEIGHT_FIELD_ROW * 3, 150, HEIGHT_FIELD_ROW);
		this.resetButton.setContentAreaFilled(false);
		contentPane.add(this.resetButton);

		resetButton.addActionListener(e -> {
			model.reset();
			for (JButton button : buttons) {
				button.setText("");
				button.setEnabled(true);
			}
			this.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
			currentPlayer = 0;
			player.setText("O's turn");
			resetButton.setVisible(false);
		});

		switch (winFlag) {
			case DRAW -> {
				player.setText("Draw -_-");
			}
			case PLAYER_1 -> {
				player.setText("Player 1 won!");
			}
			case PLAYER_2 -> {
				player.setText("Player 2 won!");
			}
		}
	}

}
