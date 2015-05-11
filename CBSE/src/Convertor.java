import java.io.Serializable;

/**
 * 
 */

/**
 * @author Taylor
 *
 */
public class Convertor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	// Get the Dynamic Range of the sample.
	public float getDNR() {
		float[] data = originalSample.getSampleData();
		
		float min = data[0];
		float max = data[0];
		float val = data[0];
		
		for(int i = 1; i < data.length; i++)
		{
			val = data[i];
			
			if(val > max)
				max = val;
			else if(val < min)
				min = val;
		}
		
		return (max/min);
	}
	
	// Get the peak amplitude.
	public float getPeakAmp() {
		float[] data = originalSample.getSampleData();
		
		float peak = data[0];
		float val = data[0];
		
		for(int i = 1; i < data.length; i++)
		{
			val = data[i];
			
			if(val > peak)
				peak = val;
		}
		
		return peak;
	}

	// Get the minimum amplitude.
	public float getMinAmp() {
		float[] data = originalSample.getSampleData();
		
		float min = data[0];
		float val = data[0];
		
		for(int i = 1; i < data.length; i++)
		{
			val = data[i];
			
			if(val < min)
				min = val;
		}
		
		return min;
	}
	
	// Set the sample data and attributes.
	public void setSample(Sample sample) {
		originalSample = sample;
	}
	
	// Get the sample data and attributes.
	public Sample getSample() {
		return originalSample;
	}
	
	// Read in sample data.
	public Sample readInSample() {
		originalSample = sampleReader.readFromFile(originalSample.getFileName());
		return originalSample;

	}
	
	// Read out sample data.
	public void readOutSample() {
		sampleReader.readOutToFile(originalSample);
	}
 
	// Default constructor.
	public Convertor() {
		// Set default attributes.
		sampleReader = new SampleReader();
	}
}
