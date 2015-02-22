package shared;

import GUI.CGameLayout;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import logic.CDiamondGame;
import sounds.CAudioPlayer;

public class CDialogs {

    private CDialogs() {
    }

    public static void changeLang() {

        String s = (String) JOptionPane.showInputDialog(null,
                CLangs.messages.getString("chooseLang"),
                CLangs.getString("lang"),
                JOptionPane.PLAIN_MESSAGE,
                null,
                CLangs.possibilities,
                CLangs.possibilities[0]);
        CLangs.init(s);
    }

    public static String name() {
        return JOptionPane.showInputDialog(null, CLangs.messages.getString("nickname"), CLangs.messages.getString("hero"), JOptionPane.INFORMATION_MESSAGE);
    }

    public static void about() {
        JOptionPane.showMessageDialog(null, CLangs.messages.getString("about") + ":\nKvido - Ing. Kvido\nPaladin - Ing. Jiří Kožusznik\nMr. R. - " + CLangs.messages.getString("Mr.R."));
    }

    public static void exit(JFrame frame) {
        if (JOptionPane.showConfirmDialog(frame, CLangs.getString("sure?"), CLangs.getString("exitGame"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
            frame.setVisible(false);
            CAudioPlayer.stop();
            frame.dispose();
        }
    }

    public static File load() {
        JFileChooser chooser = saveAndLoad();
        chooser.setDialogTitle(CLangs.getString("loadGame"));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    public static File save() {
        JFileChooser chooser = saveAndLoad();
        chooser.setDialogTitle(CLangs.getString("saveGame"));

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    private static JFileChooser saveAndLoad() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Saves", "sav");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        return chooser;
    }

    public static void win(CGameLayout layout) {
       // JOptionPane.showMessageDialog(cGameLayout, "Congratulations");
            CAudioPlayer.stop();
            CAudioPlayer.play(101);
            JOptionPane.showMessageDialog(layout, CLangs.getString("congrats")/*napis*/, CLangs.getString("congrats")/*title*/, JOptionPane.YES_OPTION);
            layout.setVisible(false);
            
            CAudioPlayer.stop();
            layout.dispose();
    }
}
