package shared;

import logic.CDiamondGame;

public class CFunctions {

    public static String getVersion() {
        return CConstants.version;
}

    public static void printDebug(String s) {
        if (CDiamondGame.DEBUG) {
            System.out.println(s);
        }
    }
}
