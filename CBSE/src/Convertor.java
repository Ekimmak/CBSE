import java.io.Serializable;

/**
 * 
 */

/**
 * @author no_author
 *
 */
public class Convertor implements Serializable {
	// Sample object.
	private Sample originalSample
	private ConvertedSample convertedSample;

	// Get the average noise profile of a sample.
	public float getNoise() {
	}

	// Get the average line level of a sample.
	public float getLine() {
	}

	// Get the average distortion of a sample.
	public float getDistortion() {
	}
 
	// Default constructor.
	public Convertor(Sample newSample) {
		// Set default attributes.
		originalSample = newSample;
	}
}
