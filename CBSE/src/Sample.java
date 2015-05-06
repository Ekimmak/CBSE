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

	// Format information.
	private short formatTag;
	private short channels;
	private int samplesPerSec;
	private int avgBytesPerSec;
	private short blockAlign;
	private short bitsPerSample;

	// Format information for Non-PCM WAVs.
	private short extSize;
	private short validBitsPerSample;
	private int channelMask;
	private int subFormat;
	private int sampleLength;

	// Sample data.
	private byte[] sampleData;
	
	// Getter methods.
	public short getFormatTag() {
		return formatTag;
	}
	
	public short getChannels() {
		return channels;
	}

	public int getSamplesPerSec() {
		return samplesPerSec;
	}

	public int getAvgBytesPerSec() {
		return avgBytesPerSec;
	}

	public short getBlockAlign() {
		return blockAlign;
	}

	public short getBitsPerSample() {
		return bitsPerSample;
	}

	public short getExtSize() {
		return extSize;
	}

	public short getValidBitsPerSample() {
		return validBitsPerSample;
	}

	public int getChannelMask() {
		return channelMask;
	}

	public int getSubFormat() {
		return subFormat;
	}

	public int getSampleLength() {
		return sampleLength;
	}

	public byte[] getSampleData() {
		return sampleData;
	}

	// Setter methods.
	public void setFormatTag(short formatTag ) {
		this.formatTag = formatTag ;
	}
	
	public void setChannels(short channels) {
		this.channels = channels;
	}

	public void setSamplesPerSec(int samplesPerSec) {
		this.samplesPerSec = samplesPerSec;
	}

	public void setAvgBytesPerSec(int avgBytesPerSec) {
		this.avgBytesPerSec = avgBytesPerSec;
	}

	public void setBlockAlign(short blockAlign) {
		this.blockAlign = blockAlign;
	}

	public void setBitsPerSample(short bitsPerSample) {
		this.bitsPerSample = bitsPerSample;
	}

	public void setExtSize(short extSize) {
		this.extSize = extSize;
	}

	public void setValidBitsPerSample(short validBitsPerSample) {
		this.validBitsPerSample = validBitsPerSample;
	}

	public void setChannelMask(int channelMask) {
		this.channelMask = channelMask;
	}

	public void setSubFormat(int subFormat) {
		this.subFormat = subFormat;
	}

	public void setSampleLength(int sampleLength) {
		this.sampleLength = sampleLength;
	}

	public void setSampleData(byte[] sampleData) {
		this.sampleData = sampleData;
	}

	// Constructors.
	public Sample() {
		formatTag = 0;
		channels = 0;
		samplesPerSec = 0;
		avgBytesPerSec = 0;
		blockAlign = 0;
		bitsPerSample = 0;
		extSize = 0;
		validBitsPerSample = 0;
		channelMask = 0;
		subFormat = 0;
		sampleLength = 0;
	}
}
