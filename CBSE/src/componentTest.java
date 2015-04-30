import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * 
 */

/**
 * @author no_author
 *
 */

public class componentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Get audio stream file.
		String fileName = "samples/1A5.wav";
		File audioFile = new File(fileName);
		
		try {
			// Select audio stream from file system and read in information.
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);
			System.out.println("Opened audio stream " + inputStream.getFormat());
			// Store .WAV information.
			/* AudioInputStream seems to be unsuitable for getting WAV data as it searches for readable chunks.
			 * May need to use an alternate scanner to skip a certain amount of bytes to the WAV data so it
			 * can be read into a byte array.
			 */
			
		
		} catch(Exception e) {
			System.out.println("Unable to open audio stream.");
		}
	}
	
	

}
