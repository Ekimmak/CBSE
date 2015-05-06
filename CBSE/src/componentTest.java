import java.io.File;
import java.io.PrintWriter;

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
		System.out.println("Loading..." + fileName);
		// Get sample information.
		Convertor sampleConvertor = new Convertor(fileName);
		Sample waveSample = sampleConvertor.getSample();
		
		System.out.println("Loading completed" + waveSample.getAvgBytesPerSec());
		try {/*
			// Print data out for testing purposes. 
			PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
			System.out.println(waveSample.getAvgBytesPerSec());
				/*
			for(int i = 0; i < 44100; i+=2) {
				writer.println(j + ", " + byteBuffer.getShort());
				j+=0.01;
			} 

			writer.close(); */
			
		} catch(Exception e) {
			System.out.println("Unable to open audio stream.");
		} 
	}
}
