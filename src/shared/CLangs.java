package shared;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.UIManager;

public class CLangs {

    static Locale currentLocale = new Locale("en", "US");
    static ResourceBundle messages;
    public static Object[] possibilities = {"english", "czech", "roumenstina"};

    public static void init(String s) {

        if (s != null) {

            String language = s.substring(0, 2);
            if ("en".equals(language)) {
                CLangs.currentLocale = new Locale(language, "US");
            }
            if ("cz".equals(language)) {
                CLangs.currentLocale = new Locale(language, "CZ");
            }
            if ("ro".equals(language)) {
                CLangs.currentLocale = new Locale(language, "RM");
            }
        }
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

}
