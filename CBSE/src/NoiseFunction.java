import java.io.Serializable;
import java.util.Random;

/**
 * 
 */

/**
 * @author no_author
 *
 */
// Noise may need to be removed in blocks, rather than on each amplitude.
public class NoiseFunction implements Serializable {
	/** Add or remove noise from a Sample.
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /** Add a certain amount of noise to a Sample.
     @param sample - The Sample object.
     @param variance - Amount of noise added to each amplitude (0 - 2.0).
     @param threshold - Maximum amplitude to add noise to (0 - 1.0).
     */
	public Sample AddNoise(Sample sample, float variance, float threshold) {
		Sample newSample = sample;
		float[] audioData = newSample.getSampleData();
		Random rnd = new Random();
		
		if(sample.getBitsPerSample() == 16) {
			short max = Short.MAX_VALUE;
			short min = Short.MIN_VALUE;
			for(int i = 0; i < audioData.length; i++) {
				if(audioData[i] <= audioData[i])
					audioData[i] = ((rnd.nextInt((max - min)) + 1) + (-min));
			}
		}
		
		newSample.setSampleData(audioData);
		
		return newSample;
	}
	
	public Sample RemoveNoise(Sample sample) {
		return sample;
	}

	public NoiseFunction() {
	}
}