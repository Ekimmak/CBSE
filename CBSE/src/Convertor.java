import java.io.Serializable;

/**
 * 
 */

/**
 * @author Taylor
 *
 */
public class Convertor implements Serializable {
	// Sample objects.
	private Sample originalSample;
	private ConvertedSample convertedSample;
	
	private SignalConvertor signalConv;

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
 
	// Default constructor.
	public Convertor(Sample newSample) {
		// Set default attributes.
		originalSample = newSample;
		
		// Get the converted sample.
		signalConv = new SignalConvertor();
		//convertedSample = signalConv.getConvertedSample(originalSample);
	}
}
