//MazeMain.java
// Duncan McCabe and Jake Gaughan
//Simple Java applet demonstrating GUI components and event handling
//derived from EventDrawing.java -- just a few changes
// Taken from BoxGame.java

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; // for Stack


public class MazeMain extends Applet implements ActionListener, KeyListener
{  
    private Button sstartButton, nmazeButton, doneButton;
    private Canvas2 c;
    protected Label result; 
  
  
    public void init() {  // instead of constructor
    	setFont(new Font("Dialog", Font.BOLD, 18));
    	//result label at the top, also used as instructions
    	Panel p1 = new Panel();
        p1.setLayout(new BorderLayout());
        result = new Label("Click New Maze!", Label.CENTER);
        result.setBackground(Color.white);
        p1.add(result);
        
        //everything beneath this is to set up the maze
        c = new Canvas2();
        c.setBackground(Color.WHITE);
        
        
        // Gameplay buttons
        //sstartButton = new Button("Start");
        nmazeButton = new Button("New Maze");
        doneButton = new Button("Show me my time");
        //sstartButton.addActionListener(this);
        nmazeButton.addActionListener(this);
        doneButton.addActionListener(this);
        
   
        Panel p = new Panel();
        p.setLayout(new FlowLayout());
        //p.add(sstartButton);
        p.add(nmazeButton);
        p.add(doneButton);

    
        setSize(400, 300);
        setVisible(true);
        
        c.addKeyListener(this); 
        c.addMouseListener(c);
        //c.addMouseMotionListener(c);
        add("Center", c);
        
        setLayout(new BorderLayout());
        add("North", p1);
        add("Center", c);
        add("South", p);
    }
    
    public void actionPerformed(ActionEvent evt) {
        //System.out.println("something happened!!");
    	
        if (evt.getSource() == nmazeButton) {
        	// creates a new maze
            System.out.println("New Maze pressed");
            result.setText("Click the top left cell to start! Navigate with arrow keys");
            c.newMaze();
        } else if (evt.getSource() == doneButton)  {
        	// for checking the time, only works when done
        	// result of this functionality is that the player plays the game in a vacuum
        	System.out.println("done pressed");
        	if (c.time == 0) {
        		result.setText("you aren't done yet, finish the maze");
        	} else {
        		result.setText(Double.toString(c.time));
        	}
        	//result.setText(Double.toString(c.time));
            //System.out.println(c.quit());  
        } //else if (evt.getSource() == quitButton)
        
    }

	//@Override

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// taking key inputs, primary mode of gameplay
		int keyCode = e.getKeyCode();               
        if (keyCode == KeyEvent.VK_UP) {
        	System.out.println("up pressed");// up arrow
            c.moveCurr(0, -1);
        } else if (keyCode == KeyEvent.VK_DOWN) {
        	System.out.println("down pressed");
            c.moveCurr(0, 1);
        } else if (keyCode == KeyEvent.VK_LEFT ) {
        	System.out.println("left pressed");
            c.moveCurr(-1, 0);
        } else if (keyCode == KeyEvent.VK_RIGHT ) {
        	System.out.println("right pressed");
            c.moveCurr(1, 0);
        }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}

