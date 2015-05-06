import java.io.Serializable;

/**
 * 
 */

/**
 * @author Taylor
 *
 */
public class Convertor implements Serializable {
	private String sampleFileName;
	private Sample originalSample;
	private SampleReader sampleReader;

	// Get the average noise profile of a sample.
	public float getNoise() {
		return 0;
	}

	// Get the average line level of a sample.
	public float getLine() {
		return 0;
	}

	// Get the average distortion of a sample.
	public float getDistortion() {
		return 0;
	}

	// Get the sample data and attributes.
	public Sample getSample() {
		return originalSample;
	}
 
	// Default constructor.
	public Convertor(String sampleFileName) {
		// Set default attributes.
		this.sampleFileName = sampleFileName;
		sampleReader = new SampleReader();

		// Load in sample data.
		originalSample = sampleReader.readFromFile(this.sampleFileName);
	}
}
