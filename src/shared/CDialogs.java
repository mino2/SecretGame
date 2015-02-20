package shared;

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

    private static Locale currentLocale = new Locale("en", "US");
    private static ResourceBundle messages;
    private static final String version = "0.79"; //actual version

    private CDialogs() {

    }

    public static void changeLang() {

        String language;
        Object[] possibilities = {"english", "czech", "roumenstina"};
        String s = (String) JOptionPane.showInputDialog(
                null,
                messages.getString("chooseLang"),
                getString("lang"),
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "english");
        if (s != null) {
            language = s.substring(0, 2);
            if ("en".equals(language)) {
                currentLocale = new Locale(language, "US");
            }
            if ("cz".equals(language)) {
                currentLocale = new Locale(language, "CZ");
            }
            if ("ro".equals(language)) {
                currentLocale = new Locale(language, "RM");
            }
        }
        init();
    }

    public static void init() {
        messages = ResourceBundle.getBundle("lang/lang", currentLocale);
        UIManager.put("FileChooser.lookInLabelText", messages.getString("lookIn"));
        UIManager.put("FileChooser.cancelButtonText", messages.getString("cancel"));
        UIManager.put("FileChooser.openButtonText", messages.getString("open"));
        UIManager.put("FileChooser.saveButtonText", messages.getString("save"));
        UIManager.put("FileChooser.filesOfTypeLabelText", messages.getString("filesOfType"));
        UIManager.put("FileChooser.fileNameLabelText", messages.getString("fileName"));
        UIManager.put("FileChooser.saveInLabelText", messages.getString("saveIn"));

        UIManager.put("OptionPane.yesButtonText", messages.getString("yes"));
        UIManager.put("OptionPane.noButtonText", messages.getString("no"));
        UIManager.put("OptionPane.cancelButtonText", messages.getString("cancel"));

    }

    public static String getString(String key) {
        return messages.getString(key);
    }

    public static String name() {
        return JOptionPane.showInputDialog(null, messages.getString("nickname"), messages.getString("hero"), JOptionPane.INFORMATION_MESSAGE);
    }

    public static void about() {
        JOptionPane.showMessageDialog(null, messages.getString("about") + ":\nKvido - Ing. Kvido\nPaladin - Ing. Jiří Kožusznik\nMr. R. - " + messages.getString("Mr.R."));
    }

    public static void exit(JFrame frame) {
        if (JOptionPane.showConfirmDialog(frame, CDialogs.getString("sure?"), CDialogs.getString("exitGame"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
            frame.setVisible(false);
            CAudioPlayer.stop();
            frame.dispose();
        }
    }

    public static File load() {
        JFileChooser chooser = saveAndLoad();
        chooser.setDialogTitle(CDialogs.getString("loadGame"));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    public static File save() {
        JFileChooser chooser = saveAndLoad();
        chooser.setDialogTitle(CDialogs.getString("saveGame"));

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

    public static void printDebug(String s) {
        if (CDiamondGame.DEBUG) {
            System.out.println(s);
        }
    }

    public static String getVersion() {
        return version;
    }
}
