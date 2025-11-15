package src.ui.screens.games.memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import src.App;
import src.ui.ColorManager;
import src.ui.helper.*;
import src.ui.screens.StartPage;
import src.ui.screens._BaseScreen;

import VocabAPI.VocabParser;

public class MemoryMain extends _BaseScreen {
    private String lesson;

    private ArrayList<String> vocabGerman;
	private ArrayList<String> vocabLatin;
    private boolean bordShown;
    private boolean purgatory;
    private JButton btn[];
    private String[] board;
	private int[] boardQ;
    private int temp=30;
	private int temp2=30;

    private int clicks=0;

    /* Possible states:
        - 0 = start screen
        - 1 = instructions
        - 2 = game running
     */
    private int state;

    private int amountVocabs;

    public MemoryMain(String lesson) {
        super(false);

        this.lesson = lesson;
        this.state = 0;
        vocabGerman = new ArrayList<String>();
		vocabLatin = new ArrayList<String>();
        purgatory = false;

        this.setKeepInHistory(false);

        this.rebuildUI();
    }

    @Override
    public JPanel createUI() {
        JPanel start_screen = new JPanel();
        start_screen.setLayout(new BorderLayout());
        if (this.state == 3)  {
            JPanel end_screen = new JPanel();
			start_screen.add(end_screen, BorderLayout.CENTER);
			end_screen.add(new TextField("You Win"), BorderLayout.NORTH);
			end_screen.add(new TextField("times failed: " + clicks), BorderLayout.SOUTH);
            JButton goBack = new JButton("Main Menu");
			end_screen.add(goBack);
			goBack.setEnabled(true);
			goBack.addActionListener(e -> {
                App.switchScreen(new StartPage());
            });
        } else if (this.state == 2) {
            this.bordShown = true;
            btn = new JButton[amountVocabs*2];
	    	boardQ = new int[amountVocabs*2];
	    	
            for (int i = 0; i < amountVocabs; i++) {
			    vocabGerman.add(VocabParser.getVocabsFromLesson(lesson).get(i).getGerman().get(0));
			    vocabLatin.add(VocabParser.getVocabsFromLesson(lesson).get(i).getBasicForm());
		    }

            JPanel field = new JPanel();
            field.setLayout(new BorderLayout());
		    start_screen.add(field, BorderLayout.CENTER);
		
		    field.setLayout(new GridLayout(5,4,2,2));
		    field.setBackground(ColorManager.bodyPrimary());
            
	    	board = new String[2*amountVocabs];
	    	for(int i=0;i<(amountVocabs*2);i++){
	    		btn[i] = new JButton("");
	    		btn[i].setBackground(ColorManager.buttonDefault());
	    		btn[i].setForeground(ColorManager.text());
	    		btn[i].addActionListener(new GameButtonListener());
	    		btn[i].setEnabled(true);
	    		field.add(btn[i]);
            
	    	}

            Random randomGenerator = new Random();
	        for (int i = 0; i < amountVocabs; i++) {
	        	boolean placed = false;
	        	while (!placed) {
	        		int y = randomGenerator.nextInt(2*amountVocabs);
	        		int y2 = randomGenerator.nextInt(2*amountVocabs); // Assuming y2 is properly defined
	        		if (board[y] == null && board[y2] == null && y != y2) {
	        			btn[y].setText(vocabGerman.get(i));
	        			board[y] = vocabGerman.get(i);
	        			boardQ[y] = i;
	        			btn[y2].setText(vocabLatin.get(i));
	        			board[y2] = vocabLatin.get(i);
	        			boardQ[y2] = i;
	        			placed = true; // Set placed to true to exit the while loop
	        		}
	        	}
	        }
            field.requestFocus();
        } else if (this.state == 1) {
            JPanel instruct_screen = new JPanel();
            JPanel one = new JPanel();
			one.setLayout(new FlowLayout(FlowLayout.CENTER));
			JPanel two = new JPanel();
			two.setLayout(new FlowLayout(FlowLayout.CENTER));
			instruct_screen.setLayout(new BorderLayout());
			instruct_screen.add(one, BorderLayout.NORTH);
			instruct_screen.add(two, BorderLayout.SOUTH);

            JTextArea instructM = new JTextArea("Wenn das Spiel beginnt, ist der Bildschirm voller Tastenpaare.\n Merken Sie sich deren Anordnung.\n Sobald Sie eine Taste drücken, verschwinden sie alle. \n Ihr Ziel ist es, die übereinstimmenden Tasten in einer Reihe anzuklicken. \n Wenn Sie das geschafft haben, haben Sie gewonnen. \n Für jeden falschen Klick bekommen Sie einen Punkt (die sind schlecht). \n Viel Glück! \n "+"für ein einzelnes Level: Geben Sie ein Level zwischen 1 und 10 ein,\nwählen Sie leicht oder schwer, und drücken Sie dann Start.");
            one.add(instructM);
            JButton goBack = new JButton("Main Menu");
			two.add(goBack);
			goBack.addActionListener(new StateSetter(0));
			goBack.setEnabled(true);

            start_screen.add(instruct_screen, BorderLayout.NORTH);
        } else {
            JPanel menu = new JPanel();
	        JPanel menu2 = new JPanel();
	        JPanel menu3 = new JPanel();
            JPanel mini = new JPanel();
			menu.setLayout(new FlowLayout(FlowLayout.CENTER));
			menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
			menu3.setLayout(new FlowLayout(FlowLayout.CENTER));
			mini.setLayout(new FlowLayout(FlowLayout.CENTER));

			start_screen.add(menu, BorderLayout.NORTH);
			start_screen.add(menu3, BorderLayout.CENTER);
			start_screen.add(menu2, BorderLayout.SOUTH);
			menu3.add(mini, BorderLayout.CENTER);
            JLabel amountLabel = new JLabel("Geben Sie eine Anzahl an Pärchen ein:");
	        JTextField amountInput = new JTextField(10);
			menu.add(amountLabel);
			menu.add(amountInput);
            JButton inst = new JButton("Instructions");
			mini.add(inst, BorderLayout.SOUTH);

            JButton start = new JButton("Start");
			start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
		        	    amountVocabs = Integer.parseInt(amountInput.getText());
	    		    } catch (Exception ex){
        				amountVocabs = 1;
			        }
                    if (amountVocabs > VocabParser.getVocabsFromLesson(lesson).size()){
    			        amountVocabs = VocabParser.getVocabsFromLesson(lesson).size();
		            }
                    state = 2;
                    rebuildUI();
                }
            });
			start.setEnabled(true);
			menu2.add(start);
            JButton over = new JButton("Exit");
			over.addActionListener(e -> {
                App.switchScreen(new StartPage());
            });
			over.setEnabled(true);
			menu2.add(over);
			inst.addActionListener(new StateSetter(1));
			inst.setEnabled(true);
        }
        return start_screen;
    }

    private class StateSetter implements ActionListener {
        private int target;

        public StateSetter(int target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            state = this.target;
            rebuildUI();
        }
    }

    private class GameButtonListener implements ActionListener {
        private void switchSpot(int i) {
            //this will switch the current spot to either blank or what it should have
            if(board[i]!="done"){
		        //when a match is correctly chosen, it will no longer switch when pressed
		        if(btn[i].getText()==""){
    			    btn[i].setText(board[i]);
	    	    } else {
		    	    btn[i].setText("");
		        }
		    }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
		    if(purgatory){
		    	switchSpot(temp2);
		    	switchSpot(temp);
		    	clicks++;
		    	temp=(amountVocabs*2);
		    	temp2=30;
		    	purgatory=false;
		    }		
        
		    for(int i =0;i<(amountVocabs*2);i++){//gameplay when a button is pressed
		    	if(source==btn[i]){
		    		if(bordShown){
		    			for(int j=0;j<(amountVocabs*2);j++){
			                btn[j].setText("");		
		                }
		                bordShown = false;
		    		}else{//otherwise play
                        switchSpot(i);

		    			if(temp>=(amountVocabs*2)){
		    				temp=i;
		    			} else {
		    				if((boardQ[temp]!=boardQ[i])||(temp==i)){
		    					temp2=i;
		    					purgatory=true;
		    				} else {
		    					board[i]="done";
		    					board[temp]="done";
		    					
                                boolean won = true;
                                for(int k=0;k<(amountVocabs*2);k++){
			                        if (board[k]!="done") {
                                        won = false;
                                    }
		                        }

                                if (won) {
                                    state = 3;
                                    rebuildUI();
                                }

		    					temp=(amountVocabs*2);
		    				}
		    			}
		    		}
		    	}
		    }
        }
    }
}