package sounds;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.*;
public class CAudioPlayer {

    public CAudioPlayer() throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        
AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource("/sounds/media/diamonds2.wav"));

Mixer.Info[] arrMixerInfo = AudioSystem.getMixerInfo();

// Get a sound clip resource.
Clip clip = AudioSystem.getClip(arrMixerInfo[1]);

// Open audio clip and load samples from the audio input stream.
clip.open(audioIn);
clip.loop(0);
//clip.start();

//clip.drain();
//clip.close();
    }

}
