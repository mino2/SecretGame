/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import logic.CGameboard;
import logic.CPlayer;

/**
 *
 * @author Jirka
 */
public class CMainMenu extends JFrame implements ActionListener {

    private final ImagePanel menu;
    private final JButton exit;
    private final JButton mAbout;
    private final JButton newGame;
    private final JButton load;
    private final int mMaxNameLenght = 15;

    public CMainMenu() {
        super("Diamanty");
        setSize(400, 400);
        menu = new ImagePanel(new ImageIcon(this.getClass().getResource("/images/mainMenu_bg.jpg")).getImage());
        add(menu);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGame = createButtonOnMenu("New Game", 120);
        load = createButtonOnMenu("Load Game", 150);
        exit = createButtonOnMenu("Exit Game", 180);
        mAbout = createButtonOnMenu("About", 210);

        setVisible(true);

    }

    private JButton createButtonOnMenu(String Name, int y) {
        JButton button = new JButton(Name);
        button.setBounds(getSize().width / 2 - button.getText().length() * 8 / 2, y, button.getText().length() * 9, 18);
        button.setMargin(new Insets(0, 0, 0, 0));
        menu.add(button);
        button.addActionListener(this);
        return button;
    }

    @Override//exit button
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exit) {
            if (JOptionPane.showConfirmDialog(this, "Are you sure ?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                setVisible(false);
                dispose();
            }
        }
        if (e.getSource() == mAbout) {
            JOptionPane.showMessageDialog(this, "Authors:\nKvido - Ing. Kvido\nPaladin - Ing. Jiří Kožusznik\nMr. R. - Mysterious creature");
        }

        if (e.getSource() == newGame) {
            setVisible(false);
            String name = "";
            while(name.length() <= 0 || name.length() > mMaxNameLenght){
            name = JOptionPane.showInputDialog(this, "Insert your nickname:", "Choose your hero", JOptionPane.INFORMATION_MESSAGE);
            }
            if (name != null) {
                CGameboard game = new CGameboard(new CPlayer(name));
                dispose();
            } else {
                setVisible(true);
            }

        }
        if (e.getSource() == load) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Load");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Saves", "sav");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                CGameboard game = new CGameboard(chooser.getSelectedFile());
                dispose();
                
            }
        }

    }

}
