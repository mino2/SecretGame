package diamondgame;

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
    private CGameboard mGame;
    
    public CGameLayout(String name,CGameboard cGame) {
        super(name);
        mGame=cGame;
    }
     
    public void addComponentsToPane(final Container pane,JPanel desktop) {
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
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI(String name, JPanel desktop,CGameboard cg) {
        //Create and set up the window.
        CGameLayout frame = new CGameLayout(name,cg);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane(), desktop);
        //Display the window.
       // frame.pack();
        frame.setVisible(true);
    }
     

}