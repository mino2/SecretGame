package shared;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CDialogs {

    private CDialogs() {
    }

    public static void about(JFrame frame) {
        
        JOptionPane.showMessageDialog(frame, "Authors:\nKvido - Ing. Kvido\nPaladin - Ing. Jiří Kožusznik\nMr. R. - Mysterious creature");
    }

    public static File load() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Load");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Saves", "sav");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    public static File save() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Save");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Saves", "sav");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
         if (chooser.showSaveDialog(null)  == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;

    }
}
