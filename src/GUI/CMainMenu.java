package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import logic.CGameboard;
import logic.CPlayer;
import shared.CDialogs;
import shared.CFunctions;
import shared.CLangs;
import sounds.CAudioPlayer;

public class CMainMenu extends JFrame implements ActionListener {
    
     private static final long serialVersionUID = 56L;

    private final ImagePanel menu;
    private final JButton exit;
    private final JButton mAbout;
    private final JButton newGame;
    private final JButton load;
    private final JButton languages;
    private final int mMaxNameLenght = 15;

    public CMainMenu() {
        super(CLangs.getString("title") + CFunctions.getVersion());
       // CAudioPlayer.init(); //initialize music
        CAudioPlayer.play(0);
        setSize(400, 400);

        menu = new ImagePanel(new ImageIcon(this.getClass().getResource("/images/mainMenu_bg.jpg")).getImage());
        add(menu);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // setVisible(true);
        //  setVisible(false);
        newGame = createButtonOnMenu(CLangs.getString("newGame"), 90);
        load = createButtonOnMenu(CLangs.getString("loadGame"), 120);
        languages = createButtonOnMenu(CLangs.getString("lang"), 150);
        mAbout = createButtonOnMenu(CLangs.getString("about"), 180);
        exit = createButtonOnMenu(CLangs.getString("exitGame"), 210);
        pack();
        setVisible(true);
      //  CAudioPlayer.init(); //initialize music

    }

    private JButton createButtonOnMenu(String Name, int y) {
        JButton button = new JButton(Name);
        button.setBounds(getSize().width / 20 - button.getText().length() * 8 / 2, y, button.getText().length() * 9, 18);
        CGameLayout.fitCompToFont(button, menu);
        // button.setMargin(new Insets(0, 0, 0, 0));
        menu.add(button);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exit) {
           // CAudioPlayer.stop();
            CDialogs.exit(this);
        }
        if (e.getSource() == mAbout) {
            CDialogs.about();
        }

        if (e.getSource() == newGame) {
            setVisible(false);
            String name = "";
            while (name != null && (name.length() <= 0 || name.length() > mMaxNameLenght)) {
                name = CDialogs.name();
            }
            if (name != null) {
                CAudioPlayer.stop();
                CGameboard game = new CGameboard(new CPlayer(name));          
                dispose();
            } else {
                setVisible(true);
            }

        }
        if (e.getSource() == load) {

            File tmp = CDialogs.load();
            if (tmp != null) {
                CAudioPlayer.stop();
                CGameboard game = new CGameboard(tmp);
                dispose();
            }
        }
        if (e.getSource() == languages) {
            CDialogs.changeLang();
            updateLang();
        }

    }

    public void updateLang() {
        newGame.setText(CLangs.getString("newGame"));
        CGameLayout.fitCompToFont(newGame, menu);
        load.setText(CLangs.getString("loadGame"));
        CGameLayout.fitCompToFont(load, menu);
        languages.setText(CLangs.getString("lang"));
        CGameLayout.fitCompToFont(languages, menu);
        mAbout.setText(CLangs.getString("about"));
        CGameLayout.fitCompToFont(mAbout, menu);
        exit.setText(CLangs.getString("exitGame"));
        CGameLayout.fitCompToFont(exit, menu);
        setTitle(CLangs.getString("title") + CFunctions.getVersion());

    }

}
