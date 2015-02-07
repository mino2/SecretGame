package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import logic.CDiamondGame;
import logic.CGameboard;
import logic.CPlace;
 
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
    private int mMenuWidth;
    
    public CGameLayout(String version,CGameboard cGame) {
        super("Diamanty verze "+version);
        mGame=cGame;
        mMenuWidth=100;
        if (CDiamondGame.DEBUG) {
            setSize(700, 700);
        } else {
            setExtendedState(MAXIMIZED_BOTH);
            setUndecorated(true);
        }
        
        menu=new JPanel();
        desktop = new JPanel();
        createAndShowGUI();
        
        menu.setLayout(new BoxLayout(menu,BoxLayout.PAGE_AXIS));
        menu.setPreferredSize(new Dimension(mMenuWidth,0));
        add(menu,BorderLayout.EAST);
        menu.add(new JLabel(mGame.getPlayer().getName()));
        scoreLabel=new JLabel("Score: "+mGame.getPlayer().getScore());
        menu.add(scoreLabel);
        desktop.setLayout(new GridLayout(mGame.mHeight, mGame.mWidth));
        add(desktop, BorderLayout.CENTER);
        addComponentsToPane(this);
        
        for (int i = 0; i < mGame.totalDiamonds; i++) {

            mGame.getItems().get(i % mGame.mWidth).get(i/mGame.mWidth)
            .setPreferredSize(new Dimension(
            (this.getSize().width-mMenuWidth-1)/mGame.mWidth,
            (this.getSize().height-mMenuWidth)/mGame.mHeight));
        }
    }
     
    public void addComponentsToPane(final Container pane) {
      //  final JPanel desktop = new JPanel();
        desktop.setLayout(experimentLayout);
        experimentLayout.setAlignment(FlowLayout.LEADING);
        setResizable(false);
        experimentLayout.minimumLayoutSize(desktop);
        experimentLayout.setVgap(0);
        experimentLayout.setHgap(0);
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
    public void createAndShowGUI() {
        //Create and set up the window.
       // CGameLayout frame = new CGameLayout(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        this.addComponentsToPane(this.getContentPane());
        //Display the window.
       // frame.pack();
        this.setVisible(true);
    }
     

}