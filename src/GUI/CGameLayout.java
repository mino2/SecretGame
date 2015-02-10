package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import logic.CDiamondGame;
import logic.CGameboard;
import logic.CPlace;

public class CGameLayout extends JFrame implements ActionListener {

    private FlowLayout experimentLayout = new FlowLayout();
    private JPanel desktop; //stores all buttons/diamonds for displaying
    private JPanel menu;
    private JLabel scoreLabel;
    private Button exit;
    private CGameboard mGame;

    /**
     * ***********************************
     */
    private final int diamondSizeX = 50;
    private final int diamondSizeY = 50;
    private final int sizeOfWindowLabel = 22; // 22px panel s nazvem okna, minimalizaci, krizkem...
    private final int mMenuWidth = 100;

    /**
     * ***********************************
     * @param version
     * @param cGame
     */

    public CGameLayout(String version, CGameboard cGame) {
        super("Diamanty verze " + version);
        mGame = cGame;

        int gameboardX = mGame.mWidth * (diamondSizeX); 
        int gameboardY = mGame.mHeight * (diamondSizeY); 
        if (CDiamondGame.DEBUG) {
            gameboardX += 10; //4px without default system window borders, 10 with
            gameboardY += sizeOfWindowLabel;
            setUndecorated(false);
            setPreferredSize(new Dimension(gameboardX + mMenuWidth, gameboardY));
            setResizable(true);
        } else {
            gameboardX += 4; //4px without default system window borders, 10 with
            gameboardY += 4; //4px without default system window borders, 10 with
            setPreferredSize(new Dimension(gameboardX + mMenuWidth, gameboardY));
            setExtendedState(MAXIMIZED_BOTH);
            setUndecorated(true);
            setResizable(false);
        }
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createAndShowGUI();
        setVisible(true);
        revalidate();
        repaint();
    }

    private void createMenu() {
        menu = new ImagePanel(new ImageIcon(this.getClass().getResource("/images/menuBG.jpg")).getImage());
        menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));

        menu.setPreferredSize(new Dimension(mMenuWidth, 0));
        add(menu, BorderLayout.EAST);
        Font fontik = new Font("SansSerif", 1, 20); //1 = bold, 2 = italic, 3 = both

        JLabel player = new JLabel(mGame.getPlayer().getName());
        player.setFont(fontik);

        menu.add(player, BoxLayout.X_AXIS);

        scoreLabel = new JLabel("Score: " + mGame.getPlayer().getScore());
        menu.add(scoreLabel);
        exit = new Button("Exit");
        menu.add(exit);

        exit.addActionListener(this);

    }

    private void createDesktop() {

        desktop = new JPanel();
        //  final JPanel desktop = new JPanel();
        desktop.setLayout(experimentLayout);
        experimentLayout.setAlignment(FlowLayout.LEADING);
        experimentLayout.minimumLayoutSize(desktop);
        experimentLayout.setVgap(0);
        experimentLayout.setHgap(0);

        for (int i = 0; i < mGame.totalDiamonds; i++) {
            int x = (i % mGame.mWidth);
            int y = (i / mGame.mWidth);
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
        //getContentPane().add(desktop, BorderLayout.CENTER);
        add(desktop, BorderLayout.CENTER);

        for (int i = 0; i < mGame.totalDiamonds; i++) {

            mGame.getItems().get(i % mGame.mWidth).get(i / mGame.mWidth)
                    .setPreferredSize(new Dimension(diamondSizeX, diamondSizeY));
            mGame.getItems().get(i % mGame.mWidth).get(i / mGame.mWidth)
                    .setMinimumSize(new Dimension(diamondSizeX, diamondSizeY));
        }
    }

    public void updateScore() {
        scoreLabel.setText("Score: " + mGame.getPlayer().getScore());
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    private void createAndShowGUI() {
        createDesktop();
        createMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (JOptionPane.showConfirmDialog(this, "Are you sure ?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
            setVisible(false);
            dispose();
        }
    }

}
