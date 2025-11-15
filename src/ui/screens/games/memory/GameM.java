package minigames;
//note the count variable is not reset when a new game is pressed

import javax.swing.*;

import VocabAPI.VocabParser;

import src.Main;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
//btn1.setBackground(colors[index]
public class GameM implements ActionListener { 
	
	JPanel field = new JPanel();
	JPanel menu = new JPanel();
	JPanel menu2 = new JPanel();
	JPanel menu3 = new JPanel();
	JPanel mini = new JPanel();

	JPanel start_screen = new JPanel();
	JPanel end_screen = new JPanel();
	JPanel instruct_screen = new JPanel();

	JButton btn[];
	JButton start = new JButton("Start");
    JButton over = new JButton("Exit");
    JButton inst = new JButton("Instructions");
    JButton redo = new JButton("Play Again");
    JButton goBack = new JButton("Main Menu");
    
    Random randomGenerator = new Random();
    private boolean purgatory = false;
	JLabel winner;
	Boolean game_over = false;
	Boolean second_click = false;
	int level=0;
	int clicks=0;
	
	JPanel content;

	ArrayList<String> vocabGerman;
    ArrayList<String> vocabLatin;

	String lesson;

	String[] board;
	int[] boardQ;
	Boolean shown = true;
	int temp=30;
	int temp2=30;

	int Abfrageanzahl=0;
	
	public void winner(){
			start_screen.remove(field);
			start_screen.add(end_screen, BorderLayout.CENTER);
			end_screen.add(new TextField("You Win"), BorderLayout.NORTH);
			end_screen.add(new TextField("times failed: " + clicks), BorderLayout.SOUTH);
			end_screen.add(goBack);
			goBack.setEnabled(true);
			goBack.addActionListener(this);
	}
}