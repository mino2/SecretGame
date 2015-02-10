package logic;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sounds.CAudioPlayer;


public class CDiamondGame {
    public static boolean DEBUG = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, MalformedURLException, LineUnavailableException {
        CAudioPlayer pl = new CAudioPlayer();
        CGameboard game = new CGameboard();
        
    }
    
}
