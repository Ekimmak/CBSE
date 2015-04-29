import java.io.Serializable;
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
	
	public String getName() {
		return sampleName;
	}
	public Byte[] getData() {
		return sampleData;
	}
	public Sample(String name, Byte data[]) {
		sampleName = name;
		sampleData = data;
	}
}
