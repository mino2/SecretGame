package logic;

import GUI.CMainMenu;
import shared.CDialogs;

public class CDiamondGame {
    public static boolean DEBUG = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*For choose lang before start game*/
        //CDialogs.changeLang();
        /*For default lang*/
        CDialogs.init();
        CMainMenu menu=new CMainMenu();
        
    }
    
}
