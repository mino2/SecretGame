package logic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
 
public class CGameLayout extends JFrame{
    JRadioButton RtoLbutton;
    JRadioButton LtoRbutton;
    FlowLayout experimentLayout = new FlowLayout();
    final String RtoL = "Right to left";
    final String LtoR = "Left to right";
    JButton applyButton = new JButton("Apply component orientation");
    private JPanel desktop; //stores all buttons/diamonds for displaying
    private JPanel menu;
    private JLabel scoreLabel;
    private CGameboard mGame;
    
    public CGameLayout(String version,CGameboard cGame) {
        super("Diamanty verze "+version);
        //super.setTitle("Diamanty verze " + CGameboard.getVersion());  //title setup
        mGame=cGame;
        
        if (CDiamondGame.DEBUG) {
            setSize(600, 600);
        } else {
            setExtendedState(MAXIMIZED_BOTH);
            setUndecorated(true);
        }
        setVisible(true);
        
        menu=new JPanel();
        desktop = new JPanel();
        
//        CGameLayout.createAndShowGUI("Diamondy", desktop,this);
        
        menu.setLayout(new BoxLayout(menu,BoxLayout.PAGE_AXIS));
        add(menu,BorderLayout.EAST);
        menu.add(new JLabel(mGame.getPlayer().getName()));
        scoreLabel=new JLabel("Score: "+mGame.getPlayer().getScore());
        menu.add(scoreLabel);
        desktop.setLayout(new GridLayout(mGame.mHeight, mGame.mWidth));
        add(desktop, BorderLayout.CENTER);
        addComponentsToPane(this);
        
        
    }
     
    public void addComponentsToPane(final Container pane) {
      //  final JPanel desktop = new JPanel();
        desktop.setLayout(experimentLayout);
        experimentLayout.setAlignment(FlowLayout.LEADING);
        experimentLayout.setVgap(3);
        experimentLayout.setHgap(4);
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());
         
        LtoRbutton = new JRadioButton(LtoR);
        LtoRbutton.setActionCommand(LtoR);
        LtoRbutton.setSelected(true);
        RtoLbutton = new JRadioButton(RtoL);
        RtoLbutton.setActionCommand(RtoL);
         

         
        //Add controls to set up the component orientation in the experiment layout
        final ButtonGroup group = new ButtonGroup();
        group.add(LtoRbutton);
        group.add(RtoLbutton);
        controls.add(LtoRbutton);
        controls.add(RtoLbutton);
        controls.add(applyButton);
         
 
        
        
         for (int i = 0; i < mGame.totalDiamonds; i++) {
            int x = i % mGame.mWidth;
            int y = i / mGame.mWidth;
            desktop.add(mGame.getItems().get(x).get(y));
             if (CDiamondGame.DEBUG) {
              CPlace place = mGame.GetItem(x, y);
             place.draw();
             }
        }
         
               desktop.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);

        desktop.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        
        
        
        //Process the Apply component orientation button press
 
        pane.add(desktop, BorderLayout.CENTER);
     //   pane.add(controls, BorderLayout.SOUTH); ;
    }
    
    public void updateScore()
    {
      scoreLabel.setText("Score: "+mGame.getPlayer().getScore());
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
   /* public static void createAndShowGUI(String name, JPanel desktop,CGameboard cg) {
        //Create and set up the window.
        CGameLayout frame = new CGameLayout(name,cg);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane(), desktop);
        //Display the window.
       // frame.pack();
        frame.setVisible(true);
    }*/
     

}