package sounds;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class CAudioPlayer {

    private static Clip mClip = null;
    private static AudioInputStream mAudioIn;

    public CAudioPlayer() {
    }
      
    public static void play() {
        try {
            if (mClip == null) { //no song was played, new track is needed to initialize
                mAudioIn = AudioSystem.getAudioInputStream(CAudioPlayer.class.getResource("/sounds/media/diamonds2.wav"));
                Mixer.Info[] arrMixerInfo = AudioSystem.getMixerInfo();
                // Get a sound clip resource.
                mClip = AudioSystem.getClip(arrMixerInfo[1]);
                // Open audio clip and load samples from the audio input stream.
                mClip.open(mAudioIn);
            }
            //clip.loop(0);
            mClip.loop(100);
            //clip.drain();
            //clip.close();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(CAudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CAudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(CAudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void stop() {
        if (mClip != null) {
            mClip.stop();
        }
    }
}
