package sounds;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.*;

public class CAudioPlayer {

    private static Clip mClip = null;
    private static AudioInputStream mAudioIn;
    private static Sequencer sequencer;
    private static int mActualSong = 0;

    public CAudioPlayer() {
    }

   /* public static void init() {
        try {
            if (mClip == null) { //no song was played, new track is needed to initialize
                mAudioIn = AudioSystem.getAudioInputStream(CAudioPlayer.class.getResource("/sounds/media/doMenu.mid"));
                Mixer.Info[] arrMixerInfo = AudioSystem.getMixerInfo();
                // Get a sound clip resource.
                mClip = AudioSystem.getClip(arrMixerInfo[1]);
                // Open audio clip and load samples from the audio input stream.
                mClip.open(mAudioIn);
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(CAudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CAudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(CAudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    public static void play(int songNumber) {
        try {
            if (mClip == null || mActualSong != songNumber) { //no song was played, new track is needed to initialize
                if(mActualSong != songNumber){
                stop();
                mActualSong = songNumber;
                }
                mAudioIn = AudioSystem.getAudioInputStream(CAudioPlayer.class.getResource("/sounds/media/"+songNumber+".mid"));
                Mixer.Info[] arrMixerInfo = AudioSystem.getMixerInfo();
                // Get a sound clip resource.
                mClip = AudioSystem.getClip(arrMixerInfo[1]);
                // Open audio clip and load samples from the audio input stream.
                mClip.open(mAudioIn);
            }
            //clip.loop(0);
            mClip.loop(100);
            FloatControl gainControl = (FloatControl) 
            mClip.getControl(FloatControl.Type.MASTER_GAIN);
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

    public static void play2() {
        try {
            // Obtains the default Sequencer connected to a default device.
            sequencer = MidiSystem.getSequencer();

            // Opens the device, indicating that it should now acquire any
            // system resources it requires and become operational.
            sequencer.open();

            // create a stream from a file
            InputStream is = new BufferedInputStream(new FileInputStream((CAudioPlayer.class.getResource("/sounds/media/doMenu.mid").getFile())));

            // Sets the current sequence on which the sequencer operates.
            // The stream must point to MIDI file data.
            sequencer.setSequence(is);

            // Starts playback of the MIDI data in the currently loaded sequence.
            sequencer.start();
        } catch (IOException ex) {
            Logger.getLogger(CAudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(CAudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(CAudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void stop() {
        if (mClip != null) {
            mClip.stop();
        }
    }

    public static void stop2() {
        if (sequencer != null) {
            sequencer.stop();
        }

    }
}
