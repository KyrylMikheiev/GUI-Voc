package minigames;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

// TODO: add this

public class FallingWordsGame extends JFrame implements KeyListener {
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;
    private static final int WORD_WIDTH = 100;
    private static final int WORD_SPEED = 1;

    private ArrayList<Word> fallingWords;
    private String currentWord;
    private int score;
    private Random random;

    private JLabel scoreLabel;
    private JLabel inputLabel;
    private Timer timer;
    private GamePanel gamePanel;

    public FallingWordsGame() {
        setTitle("Falling Words Game");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        fallingWords = new ArrayList<>();
        currentWord = "";
        score = 0;
        random = new Random();

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel, BorderLayout.NORTH);

        inputLabel = new JLabel("Input: ");
        inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(inputLabel, BorderLayout.SOUTH);

        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                gamePanel.repaint();
            }
        });
        timer.start();
    }

    private void update() {
        if (random.nextInt(100) < 5) {
            String word = generateRandomWord();
            fallingWords.add(new Word(word, random.nextInt(FRAME_WIDTH - WORD_WIDTH), 0));
        }

        for (int i = 0; i < fallingWords.size(); i++) {
            Word word = fallingWords.get(i);
            word.setY(word.getY() + WORD_SPEED);

            if (word.getY() > FRAME_HEIGHT) {
                fallingWords.remove(i);
                i--;
                score--;
                updateScore();
            }
        }
    }

    private String generateRandomWord() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        int length = random.nextInt(5) + 3;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            currentWord = "";
            inputLabel.setText("Input: ");
        } else if (key == KeyEvent.VK_BACK_SPACE && currentWord.length() > 0) {
            currentWord = currentWord.substring(0, currentWord.length() - 1);
        } else if (Character.isAlphabetic(e.getKeyChar())) {
            currentWord += e.getKeyChar();
        }

        inputLabel.setText("Input: " + currentWord);

        for (int i = 0; i < fallingWords.size(); i++) {
            Word word = fallingWords.get(i);
            if (currentWord.equalsIgnoreCase(word.getText())) {
                fallingWords.remove(i);
                i--;
                score++;
                updateScore();
                currentWord = "";
                inputLabel.setText("Input: ");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FallingWordsGame game = new FallingWordsGame();
                game.setVisible(true);
            }
        });
    }

    private class Word {
        private String text;
        private int x;
        private int y;

        public Word(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }

        public String getText() {
            return text;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Word word : fallingWords) {
                g.setColor(Color.BLUE);
                g.drawString(word.getText(), word.getX(), word.getY());
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
        }
    }
}
