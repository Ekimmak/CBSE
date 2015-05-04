import java.io.Serializable;
import javax.sound.sampled.AudioFormat;

/**
*
*/
/**
* @author Taylor
*
*/
public class Sample implements Serializable {
	private String sampleName;
	private Byte sampleData[];
	private AudioFormat sampleFormat;
	
	// Get Methods.
	public String getName() {
		return sampleName;
	}
	public Byte[] getData() {
		return sampleData;
	}
	public AudioFormat getFormat() {
		return sampleFormat;
	}
	
	// Set Methods.
	public void setData(Byte[] data) {
		sampleData = data;
	}
	public void setFormat(AudioFormat format) {
		sampleFormat = format;
	}
	
	// Constructors.
	public Sample(String name) {
		sampleName = name;
	}
}
