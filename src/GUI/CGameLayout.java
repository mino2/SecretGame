package GUI;

import java.awt.*;
import javax.swing.*;
import logic.CDiamondGame;
import logic.CGameboard;
import logic.CPlace;
 
public class CGameLayout extends JFrame{
    FlowLayout experimentLayout = new FlowLayout();
    private JPanel desktop; //stores all buttons/diamonds for displaying
    private JPanel menu;
    private JLabel scoreLabel;
    private CGameboard mGame;
    
    /**************************************/
    private final int diamondSizeX = 50;
    private final int diamondSizeY = 50;
    private final int sizeOfWindowLabel = 22; // panel s nazvem okna, minimalizaci, krizkem...
    private final int mMenuWidth = 100;
    /**************************************/
    
    
    public CGameLayout(String version,CGameboard cGame) {
        super("Diamanty verze "+version);
        mGame=cGame;
        
        int gameboardX = mGame.mWidth*(diamondSizeX+1); //1 is the width of frame around diamond
        int gameboardY = mGame.mHeight*(diamondSizeY+1);
        if (CDiamondGame.DEBUG) {
            setSize(gameboardX + mMenuWidth, gameboardY + sizeOfWindowLabel);
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
        Font fontik = new Font("SansSerif", 1, 20); //1 = bold, 2 = italic, 3 = both
        JLabel player = new JLabel(mGame.getPlayer().getName());
        player.setFont(fontik);
        menu.add(player, BoxLayout.X_AXIS);
        scoreLabel=new JLabel("Score: "+mGame.getPlayer().getScore());
        menu.add(scoreLabel);
        desktop.setLayout(new GridLayout(mGame.mHeight, mGame.mWidth));
        add(desktop, BorderLayout.CENTER);
        addComponentsToPane(this);
        
        for (int i = 0; i < mGame.totalDiamonds; i++) {

            mGame.getItems().get(i % mGame.mWidth).get(i/mGame.mWidth)
            .setPreferredSize(new Dimension(diamondSizeX,diamondSizeY));
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