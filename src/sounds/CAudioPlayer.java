package sounds;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class CAudioPlayer {

    private static Clip mClip = null;
    private static AudioInputStream mAudioIn;
    private static int mActualSong = 0;

    public CAudioPlayer() {
    }

    public static void play(int songNumber) {
        int loops = 100;

        try {
            if (mClip == null || mActualSong != songNumber) { //no song was played, new track is needed to initialize
                if (mActualSong != songNumber) {
                    stop();
                    mActualSong = songNumber;
                }
                if (mActualSong > 100) {
                    mAudioIn = AudioSystem.getAudioInputStream(CAudioPlayer.class.getResource("/sounds/media/" + songNumber + ".wav"));
                    loops = 0;
                } else {
                    mAudioIn = AudioSystem.getAudioInputStream(CAudioPlayer.class.getResource("/sounds/media/" + songNumber + ".mid"));
                }
                Mixer.Info[] arrMixerInfo = AudioSystem.getMixerInfo();
                // Get a sound clip resource.
                mClip = AudioSystem.getClip(arrMixerInfo[1]);
                // Open audio clip and load samples from the audio input stream.
                mClip.open(mAudioIn);
            }
            //clip.loop(0);
            mClip.loop(loops);
            FloatControl gainControl = (FloatControl) mClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-25.0f); // Reduce volume by 10 decibels.
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
