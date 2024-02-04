package minigames;
//note the count variable is not reset when a new game is pressed

import javax.swing.*;

import VocabAPI.VocabParser;

import src.Main;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
//btn1.setBackground(colors[index]
public class GameM implements ActionListener{ 
	
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

	//String Inhalt[]=new String[Abfrageanzahl];
	
	private JLabel label = new JLabel("Geben Sie eine Anzahl an Pärchen ein:");
	private JTextField text = new JTextField(10);
	private JTextArea instructM = new JTextArea("Wenn das Spiel beginnt, ist der Bildschirm voller Tastenpaare.\n Merken Sie sich deren Anordnung.\n Sobald Sie eine Taste drücken, verschwinden sie alle. \n Ihr Ziel ist es, die übereinstimmenden Tasten in einer Reihe anzuklicken. \n Wenn Sie das geschafft haben, haben Sie gewonnen. \n Für jeden falschen Klick bekommen Sie einen Punkt (die sind schlecht). \n Viel Glück! \n "+"für ein einzelnes Level: Geben Sie ein Level zwischen 1 und 10 ein,\nwählen Sie leicht oder schwer, und drücken Sie dann Start.");
	//instructM.setEditable(false);
	//instructW.setEditable(false);
	//instructM.setLineWrap(true);
	//instructW.setWrapStyleWord(true);
	public GameM(JPanel content, String lektion){
		this.content = content;
		EventQueue.invokeLater(()->{
		start_screen.setLayout(new BorderLayout());
		menu.setLayout(new FlowLayout(FlowLayout.CENTER));
		menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
		menu3.setLayout(new FlowLayout(FlowLayout.CENTER));
		mini.setLayout(new FlowLayout(FlowLayout.CENTER));

		start_screen.add(menu, BorderLayout.NORTH);
		start_screen.add(menu3, BorderLayout.CENTER);
		start_screen.add(menu2, BorderLayout.SOUTH);
		menu3.add(mini, BorderLayout.CENTER);
		menu.add(label);
		menu.add(text);
		mini.add(inst, BorderLayout.SOUTH);
		
		this.lesson = lektion;
		
		start.addActionListener(this);
		start.setEnabled(true);
		menu2.add(start);
		over.addActionListener(this);
		over.setEnabled(true);
		menu2.add(over);
		inst.addActionListener(this);
		inst.setEnabled(true);
		vocabGerman = new ArrayList<String>();
		vocabLatin = new ArrayList<String>();
		
		content.add(start_screen, BorderLayout.CENTER);
	});
}	

	public void fillData(int vocabCount){
		for (int i = 0; i < vocabCount; i++) {
			vocabGerman.add(VocabParser.getVocabsFromLesson(lesson).get(i).getGerman().get(0));
			vocabLatin.add(VocabParser.getVocabsFromLesson(lesson).get(i).getBasicForm());
		}
	}
	
	public void setUpGame(int x){

		if (x > VocabParser.getVocabsFromLesson(lesson).size()){
			x = VocabParser.getVocabsFromLesson(lesson).size();
		}

		btn = new JButton[x*2];
		level=x;
		clearMain();
		boardQ = new int[x*2];
		fillData(x);
		
		board = new String[2*x];
		for(int i=0;i<(x*2);i++){
			btn[i] = new JButton("");
			btn[i].setBackground(Main.defaultButton);
			btn[i].setForeground(Main.TextColor);
			btn[i].addActionListener(this);
			btn[i].setEnabled(true);
			field.add(btn[i]);
			
		}
		

		
		//lesson
		//for(int i=0;i<x/2;i++){
		//	String[]Inhalt = {vocabGerman.get(i),vocabLatin.get(i)};
		//}

	//	for(int vokabelindex=0;vokabelindex<(Abfrageanzahl);vokabelindex++){
			
		
		
	//	}

	for (int i = 0; i < x; i++) {
		boolean placed = false;
		while (!placed) {
			int y = randomGenerator.nextInt(2*x);
			int y2 = randomGenerator.nextInt(2*x); // Assuming y2 is properly defined
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
		// while(true){	
			// 	int y = randomGenerator.nextInt(x);
			// 	if(board[y]==null){
			// 		btn[y].setText(vocabLatin.get(i));
			// 		board[y]=vocabLatin.get(i);
			// 		break;
			// 	}
			// }

		createBoard();
		
	}
	public void hideField(int x){//this sets all the boxes blank
		for(int i=0;i<(x*2);i++){
			/*if(boardQ[i]==0)*/ btn[i].setText("");		
		}
		shown=false;
	}
	public void switchSpot(int i){//this will switch the current spot to either blank or what it should have
		if(board[i]!="done"){
		//when a match is correctly chosen, it will no longer switch when pressed
		if(btn[i].getText()==""){
			btn[i].setText(board[i]);
			
			//shown=true;
		} else {
			btn[i].setText("");
			//shown=false;
		}
		
		}
	}
	public void showSpot(int i){
		btn[i].setText(board[i]);
	}
	public void showField(int x, String a[]){//this shows all the symbols on the field
		for(int i=0;i<(x*2);i++){
			btn[i].setText(a[i]);
		}
		shown=true;
	}
	void waitABit(){//this was an attempt at fixing the glitch i told you about
		try{
			Thread.sleep(5);
		} catch(Exception e){
			}
	}
	public boolean checkWin(){//checks if every spot is labeled as done
		for(int i=0;i<(level*2);i++){
			if (board[i]!="done")return false;
		}
		winner();
		return true;
	}
	public void winner(){
			start_screen.remove(field);
			start_screen.add(end_screen, BorderLayout.CENTER);
			end_screen.add(new TextField("You Win"), BorderLayout.NORTH);
			end_screen.add(new TextField("times failed: " + clicks), BorderLayout.SOUTH);
			end_screen.add(goBack);
			goBack.setEnabled(true);
			goBack.addActionListener(this);
	}
	public void goToMainScreen() {
		
		new GameM(content, lesson);
	}
	public void createBoard(){//this is just gui stuff to show the board
		field.setLayout(new BorderLayout());
		start_screen.add(field, BorderLayout.CENTER);
		
		field.setLayout(new GridLayout(5,4,2,2));
		field.setBackground(Color.black);
		field.requestFocus();
	}
	public void clearMain(){//clears the main menu so i can add the board or instructions
		start_screen.remove(menu);
		start_screen.remove(menu2);
		start_screen.remove(menu3);

        start_screen.revalidate();
        start_screen.repaint();
	}
public void actionPerformed(ActionEvent click){
		Object source = click.getSource();
		if(purgatory){
			switchSpot(temp2);
			switchSpot(temp);
			clicks++;
			temp=(level*2);
			temp2=30;
			purgatory=false;
		}
		if(source==start){ //start sets level and difficulty and calls method to set up game
			try{
			level = Integer.parseInt(text.getText());
			} catch (Exception e){
				level=1;
			}
			setUpGame(level);//level between 1 and 2, eh is true or false
		}
		if(source==over){//quits
			System.exit(0);
		}
		if(source==inst){//this just sets the instruction screen
			clearMain();
			
			start_screen.add(instruct_screen, BorderLayout.NORTH);
			
			JPanel one = new JPanel();
			one.setLayout(new FlowLayout(FlowLayout.CENTER));
			JPanel two = new JPanel();
			two.setLayout(new FlowLayout(FlowLayout.CENTER));
			instruct_screen.setLayout(new BorderLayout());
			instruct_screen.add(one, BorderLayout.NORTH);
			instruct_screen.add(two, BorderLayout.SOUTH);
			
			one.add(instructM);
			two.add(goBack);
			goBack.addActionListener(this);
			goBack.setEnabled(true);
		}
		if(source==goBack){//back to main screen
			
			goToMainScreen();
		}
		
		
		for(int i =0;i<(level*2);i++){//gameplay when a button is pressed
			if(source==btn[i]){
				if(shown){
					hideField(level);//if first time, hides field
				}else{//otherwise play
					switchSpot(i);
					if(temp>=(level*2)){
						temp=i;
					} else {
						if((boardQ[temp]!=boardQ[i])||(temp==i)){
							temp2=i;
							purgatory=true;
						} else {
							board[i]="done";
							board[temp]="done";
							checkWin();
							temp=(level*2);
						}
					}
				}
			}
		}

	}
}