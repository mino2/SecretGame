package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.*;
import logic.CDiamondGame;
import logic.CGameboard;
import logic.CPlace;
import shared.CDialogs;
import sounds.CAudioPlayer;

public class CGameLayout extends JFrame implements ActionListener, ItemListener {

    private FlowLayout experimentLayout = new FlowLayout();
    private JPanel desktop; //stores all buttons/diamonds for displaying
    private CGameboard mGame;

    private JPanel menu;
    private JLabel player;
    private JLabel scoreLabel;
    private JButton exit;
    private JButton mAbout;
    private JButton save;
    private JButton load;
    private JCheckBox fullScreen;
    private JCheckBox music;

    /**
     * ***********************************
     */
    private final int diamondSizeX = 48;
    private final int diamondSizeY = 48;
    private final int sizeOfWindowLabel = 22; // 22px panel s nazvem okna, minimalizaci, krizkem...
    private final int mMenuWidth = 200;
    private Dimension mWindowScale;

    /**
     * ***********************************
     * @param version
     * @param cGame
     */
    public CGameLayout(String version, CGameboard cGame) {
        super(CDialogs.getString("title") + version);
        mGame = cGame;

        int gameboardX = mGame.mWidth * (diamondSizeX);
        int gameboardY = mGame.mHeight * (diamondSizeY);
        if (CDiamondGame.DEBUG) {
            gameboardX += 20; //4px without default system window borders, 10 with, 20 with resizable
            gameboardY += sizeOfWindowLabel + 20;
            setUndecorated(false);
            mWindowScale = new Dimension(gameboardX + mMenuWidth, gameboardY);
            setSize(mWindowScale);
            setResizable(true);
        } else {
            gameboardX += 4; //4px without default system window borders, 10 with
            gameboardY += 4; //4px without default system window borders, 10 with
            setSize(new Dimension(gameboardX + mMenuWidth, gameboardY));
            setExtendedState(MAXIMIZED_BOTH);
            setUndecorated(true);
            setResizable(false);
        }
        //    setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createAndShowGUI();
        setVisible(true);
    }

    private void createMenu() {
        menu = new ImagePanel(new ImageIcon(this.getClass().getResource("/images/ingameMenu_bg.jpg")).getImage());
        menu.setLayout(null);

        int offsetX = 5;
        menu.setPreferredSize(new Dimension(mMenuWidth, 0));

        add(menu, BorderLayout.EAST);//bacha tady se to dava do JFRAMU

        Font fontik = new Font("SansSerif", 1, 20); //1 = bold, 2 = italic, 3 = both

        player = new JLabel(mGame.getPlayer().getName());
        player.setFont(fontik);
        player.setForeground(Color.white);
        player.setBounds(mMenuWidth / 2 - player.getText().length() * 10 / 2, 0, player.getText().length() * 15, fontik.getSize() + 5);
        fitCompToFont(player, menu);
        menu.add(player);

        scoreLabel = new JLabel(CDialogs.getString("score") + mGame.getPlayer().getScore());
        scoreLabel.setForeground(Color.white);
        scoreLabel.setBounds(offsetX, 20, 100, fontik.getSize() + 5);
        fitCompToFont(scoreLabel);
        
        menu.add(scoreLabel);

        fullScreen = createCheckBoxOnMenu(CDialogs.getString("fullScreen"), offsetX, mWindowScale.height - 230, 100, 25);
        fitCompToFont(fullScreen);
        
        music = createCheckBoxOnMenu(CDialogs.getString("music"), offsetX, mWindowScale.height - 250, 100, 25);
        music.setSelected(true);
        CAudioPlayer.play(mGame.getActualLevel());
        fitCompToFont(music);
        
        save = createButtonOnMenu(CDialogs.getString("saveGame"), mWindowScale.height - 140);
        fitCompToFont(save, menu);
        load = createButtonOnMenu(CDialogs.getString("loadGame"), mWindowScale.height - 120);
        fitCompToFont(load, menu);
        mAbout = createButtonOnMenu(CDialogs.getString("about"), mWindowScale.height - 100);
        fitCompToFont(mAbout, menu);
        exit = createButtonOnMenu(CDialogs.getString("exitGame"), mWindowScale.height - 80);
        fitCompToFont(exit, menu);

    }

    private JButton createButtonOnMenu(String Name, int y) {
        JButton button = new JButton(Name);
        button.setBounds(mMenuWidth / 2 - button.getText().length() * 8 / 2, y, button.getText().length() * 8, 18);
        button.setMargin(new Insets(0, 0, 0, 0));
        menu.add(button);
        button.addActionListener(this);
        return button;
    }

    private JCheckBox createCheckBoxOnMenu(String Name, int x, int y, int width, int height) {
        JCheckBox chbox = new JCheckBox(Name);
        chbox.setBounds(x, y, width, height);
        chbox.setSelected(false);
        chbox.setOpaque(false);
        chbox.addItemListener(this);
        chbox.setForeground(Color.white);
        menu.add(chbox);
        return chbox;
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

        add(desktop, BorderLayout.CENTER);

        for (int i = 0; i < mGame.totalDiamonds; i++) {

            mGame.getItems().get(i % mGame.mWidth).get(i / mGame.mWidth)
                    .setPreferredSize(new Dimension(diamondSizeX, diamondSizeY));
            mGame.getItems().get(i % mGame.mWidth).get(i / mGame.mWidth)
                    .setMinimumSize(new Dimension(diamondSizeX, diamondSizeY));
        }
    }

    public void updateScore() {
        scoreLabel.setText(CDialogs.getString("score") + mGame.getPlayer().getScore());
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    private void createAndShowGUI() {
        createDesktop();
        createMenu();
    }

    @Override//exit button
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exit) {
            CDialogs.exit(this);
        }
        if (e.getSource() == mAbout) {
            CDialogs.about();
        }
        if (e.getSource() == save) {
            File tmp = CDialogs.save();
            if (tmp != null) {
                mGame.save(tmp);
            }
        }
        if (e.getSource() == load) {
            loadGame();
        }
    }

    public void loadGame() {
        File tmp = CDialogs.load();
        if (tmp != null) {
            desktop.removeAll();
            mGame.loadGame(tmp);
            for (int i = 0; i < mGame.totalDiamonds; i++) {
                int x = (i % mGame.mWidth);
                int y = (i / mGame.mWidth);
                desktop.add(mGame.getItems().get(x).get(y));
            }
            desktop.revalidate();
            desktop.repaint();
            player.setText(mGame.getPlayer().getName());
            updateScore();
        }

    }

    public void win() {
        JOptionPane.showMessageDialog(this, "Congratulations");

    }

    public static void fitCompToFont(JLabel cpt) { //resize to fit text
        cpt.setSize(new Dimension(cpt.getFont().getSize() * cpt.getText().length(), cpt.getFont().getSize() + 5));
    }

    public static void fitCompToFont(JCheckBox cpt) { //resize to fit text
        cpt.setSize(new Dimension(cpt.getFont().getSize() * cpt.getText().length() + 20, cpt.getFont().getSize() + 5));
    }
    
    public static void fitCompToFont(JButton cpt) { //resize to fit text
        cpt.setSize(new Dimension(cpt.getFont().getSize() * cpt.getText().length(), cpt.getFont().getSize() + 5));
    }

    public static void fitCompToFont(JLabel cpt, JPanel panel) { //resize to fit text and center to panel
        int width = cpt.getFont().getSize() * cpt.getText().length()/2; 
        int height = cpt.getFont().getSize() + 5;
        int x = panel.getWidth() / 2 - width / 2;
        int y = cpt.getBounds().y;
        cpt.setBounds(x, y, panel.getWidth(), height);
        cpt.setBorder(null);
    }
    
    public static void fitCompToFont(JCheckBox cpt, JPanel panel) { //resize to fit text and center to panel
        int width = cpt.getFont().getSize() * cpt.getText().length() * 3 / 4;
        int height = cpt.getFont().getSize() + 5;
        int x = panel.getWidth() / 2 - width / 2;
        int y = cpt.getBounds().y;
        cpt.setBounds(x, y, width, height);
        cpt.setBorder(null);
    }

    public static void fitCompToFont(JButton cpt, JPanel panel) { //resize to fit text and center to panel
        int width = cpt.getFont().getSize() * cpt.getText().length() * 3 / 4;
        int height = cpt.getFont().getSize() + 5;
        int x = panel.getWidth() / 2 - width / 2;
        int y = cpt.getBounds().y;
        cpt.setBounds(x, y, width, height);
        cpt.setBorder(null);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == fullScreen) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setExtendedState(MAXIMIZED_BOTH);
            } else {
                setSize(mWindowScale);
            }
        }
        if (e.getSource() == music) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                CAudioPlayer.play(mGame.getActualLevel());
            } else {
                CAudioPlayer.stop();
            }
        }
    }

    public Object[] getSelectedObjects() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addItemListener(ItemListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeItemListener(ItemListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
