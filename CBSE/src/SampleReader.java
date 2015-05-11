import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 
 */

/**
 * @author Taylor
 *
 */

public class SampleReader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Sample readFromFile(String file) {
				Sample waveFile = new Sample(file);

				try {
				// Create a new input stream for scanning the WAV file.
				FileInputStream wavStream = new FileInputStream(new File(file));
				  
				// Used to traverse through the WAV to get chunk information.
				byte[] byteChunkID;
				
				byte[] byteChunkSize = new byte[4];
				int chunkSize = 0;
				
				// Create byte buffer to read in bytes. WAV is little endian by default.
				ByteBuffer byteBuffer;

				// Skip to fmt chunk.
				boolean loopChunks = true;
				String chunkName;
				while(loopChunks) {
					byteChunkID = new byte[4];
					wavStream.read(byteChunkID);
					
					char[] charChunkID = new char[4];
					charChunkID[0] = (char)byteChunkID[0];
					charChunkID[1] = (char)byteChunkID[1];
					charChunkID[2] = (char)byteChunkID[2];
					charChunkID[3] = (char)byteChunkID[3];
					chunkName = new String(charChunkID);

					if(chunkName.equals("RIFF"))
					{
						wavStream.skip(8);
					}
					else if(chunkName.equals("fmt "))
					{
						loopChunks = false;
					}
					else
					{
						wavStream.read(byteChunkSize);
						byteBuffer = ByteBuffer.wrap(byteChunkSize);
						byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
						chunkSize = byteBuffer.getInt();
						wavStream.skip(chunkSize);
						System.out.println(chunkSize);
					}
				}
				
				
				// Temporary byte arrays for read in data.
				byte[] byteFormatTag = new byte[2];
				byte[] byteChannels = new byte[2];
				byte[] byteSamplesPerSec = new byte[4];
				byte[] byteAvgBytesPerSec = new byte[4];
				byte[] byteBlockAlign = new byte[2];
				byte[] byteBitsPerSample = new byte[2];
				byte[] byteExtSize = new byte[2];
				byte[] byteValidBitsPerSample = new byte[2];
				byte[] byteChannelMask = new byte[4];
				byte[] byteSubFormat = new byte[16];
				
				// Get the format chunk size. This determines what form the WAV is (PCM, Extensible etc).
				byte byteFmtChunkSize[] = new byte[4];
				wavStream.read(byteFmtChunkSize);
				
				byteBuffer = ByteBuffer.wrap(byteFmtChunkSize);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				int fmtChunkSize = byteBuffer.getInt();
	
				// Read in some more information about the sample.
				wavStream.read(byteFormatTag);
				byteBuffer = ByteBuffer.wrap(byteFormatTag);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				waveFile.setFormatTag(byteBuffer.getShort());
				
				wavStream.read(byteChannels);
				byteBuffer = ByteBuffer.wrap(byteChannels);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				waveFile.setChannels(byteBuffer.getShort());
				
				wavStream.read(byteSamplesPerSec);
				byteBuffer = ByteBuffer.wrap(byteSamplesPerSec);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				waveFile.setSamplesPerSec(byteBuffer.getInt());
				
				wavStream.read(byteAvgBytesPerSec);
				byteBuffer = ByteBuffer.wrap(byteAvgBytesPerSec);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				waveFile.setAvgBytesPerSec(byteBuffer.getInt());
				
				wavStream.read(byteBlockAlign);
				byteBuffer = ByteBuffer.wrap(byteBlockAlign);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				waveFile.setBlockAlign(byteBuffer.getShort());
				
				wavStream.read(byteBitsPerSample);
				byteBuffer = ByteBuffer.wrap(byteBitsPerSample);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				waveFile.setBitsPerSample(byteBuffer.getShort());
				
				// Extensible WAV formats
				if(fmtChunkSize == 18)
				{
					wavStream.read(byteExtSize);
					byteBuffer = ByteBuffer.wrap(byteBitsPerSample);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					waveFile.setBitsPerSample(byteBuffer.getShort());
				}
	
				else if(fmtChunkSize == 40)
				{
					wavStream.read(byteExtSize);
					byteBuffer = ByteBuffer.wrap(byteExtSize);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					waveFile.setExtSize(byteBuffer.getShort());

					wavStream.read(byteValidBitsPerSample);
					byteBuffer = ByteBuffer.wrap(byteValidBitsPerSample);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					waveFile.setValidBitsPerSample(byteBuffer.getShort());

					wavStream.read(byteChannelMask);
					byteBuffer = ByteBuffer.wrap(byteChannelMask);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					waveFile.setChannelMask(byteBuffer.getInt());

					wavStream.read(byteSubFormat);
					byteBuffer = ByteBuffer.wrap(byteSubFormat);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					waveFile.setSubFormat(byteBuffer.getInt());
				}
	
				// Start scanning through the file again.
				loopChunks = true;
				while(loopChunks) {
					// Get the next chunk.
					byteChunkID = new byte[4];
					wavStream.read(byteChunkID);
					char[] charChunkID = new char[4];
					charChunkID[0] = (char)byteChunkID[0];
					charChunkID[1] = (char)byteChunkID[1];
					charChunkID[2] = (char)byteChunkID[2];
					charChunkID[3] = (char)byteChunkID[3];
					chunkName = new String(charChunkID);

					// Get size of chunk.
					byteChunkSize = new byte[4];
					wavStream.read(byteChunkSize);
					byteBuffer = ByteBuffer.wrap(byteChunkSize);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					chunkSize = byteBuffer.getInt();

					// Some other chunk for extensible formats: This may need to be implemented at some stage. 
					if((chunkName.equals("RIFF")) && (waveFile.getFormatTag() != 0x0001))
					{
						byte[] byteSampleLength = new byte[4];
						wavStream.read(byteSampleLength);
						byteBuffer = ByteBuffer.wrap(byteSampleLength);
						byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
						waveFile.setSampleLength(byteBuffer.getInt());
					}

					// Found data structure, all WAV formats have the samee data structure..
					else if(chunkName.equals("data"))
					{
						// Start reading in sample data [Note: sample data may be ordered big endian].
						byte[] byteSampleData = new byte[chunkSize];
						wavStream.read(byteSampleData);
						
						byteBuffer = ByteBuffer.wrap(byteSampleData);
						byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
						
						// Sample data.
						float[] floatSampleData;
						
						// 8 bit sample.
						if(waveFile.getBitsPerSample() == 8)
						{
							floatSampleData = new float[chunkSize];
							for(int i = 0; i < floatSampleData.length; i++) {
								floatSampleData[i] = byteBuffer.get();
							}
							waveFile.setSampleData(floatSampleData);
						}
						
						// 16 bit sample.
						else if((waveFile.getBitsPerSample() == 16))
						{
							floatSampleData = new float[chunkSize/2];
							for(int i = 0; i < floatSampleData.length; i++) {
								floatSampleData[i] = byteBuffer.getShort();
							}
							waveFile.setSampleData(floatSampleData);
						}
						
						// 32 bit sample.
						else if((waveFile.getBitsPerSample() == 32))
						{
							floatSampleData = new float[chunkSize/4];

							for(int i = 0; i < floatSampleData.length; i++) {
								floatSampleData[i] = byteBuffer.getFloat();
							}
						}

						loopChunks = false;
					}
					else
					{
						wavStream.skip(chunkSize);
					}
				} 

				// Close the input stream.
				wavStream.close();
			}
			catch(Exception e) {	
				return null;
			}
		
		return waveFile;
	}
	
	/**
	 * 
	 */
	public int readOutToFile(Sample sample) {
		try {
			FileOutputStream outputStream = new FileOutputStream(sample.getFileName());
			
			// Create byte buffer to read in bytes. WAV is little endian by default.
			ByteBuffer byteBuffer;
			
			// Build the RIFF chunk,
			byte[] riffID = new byte[] {'R', 'I', 'F', 'F'};
			byte[] riffSize = new byte[4];
			byte[] waveID = new byte[] {'W', 'A', 'V', 'E'};
			
			// Build the fmt chunk.
			byte[] fmtID = new byte[] {'f', 'm', 't', ' '};
			byte[] fmtSize = new byte[4];
			byte[] fmtTag = new byte[2];
			byte[] fmtChannels = new byte[2];
			byte[] fmtSamplesPerSec = new byte[4];
			byte[] fmtAvgBytesPerSec = new byte[4];
			byte[] fmtBlockAlign = new byte[2];
			byte[] fmtBitsPerSample = new byte[2];
			
			// Build the data chunk.
			byte[] dataID = new byte[] {'d', 'a', 't', 'a'};
			byte[] dataSize = new byte[4];
			
			// PCM data.
			if(sample.getFormatTag() == 1)
			{
				if(sample.getBitsPerSample() == 16) {
					byte[] dataArray;
					
					float[] sampleData = sample.getSampleData();
					dataArray = new byte[sampleData.length*2];
					byteBuffer = ByteBuffer.wrap(dataArray);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					
					for(int i = 0; i < sampleData.length; i++) {
						byteBuffer.putShort((short)sampleData[i]);
					}
					
					byteBuffer = ByteBuffer.wrap(riffID);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(riffSize);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					byteBuffer.putInt((36+(dataArray.length)));
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(waveID);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(fmtID);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(fmtSize);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					byteBuffer.putInt(16);
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(fmtTag);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					byteBuffer.putShort((short)1);
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(fmtChannels);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					byteBuffer.putShort((short) sample.getChannels());
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(fmtSamplesPerSec);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					byteBuffer.putInt(sample.getSamplesPerSec());
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(fmtAvgBytesPerSec);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					byteBuffer.putInt(sample.getAvgBytesPerSec());
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(fmtBlockAlign);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					byteBuffer.putShort((short) sample.getSamplesPerSec());
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(fmtBitsPerSample);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					byteBuffer.putShort((short) sample.getBitsPerSample());
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(dataID);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(dataSize);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					byteBuffer.putInt(dataArray.length);
					outputStream.write(byteBuffer.array());
					
					byteBuffer = ByteBuffer.wrap(dataArray);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					outputStream.write(dataArray);
				}
			}
			
			else {
				outputStream.close();
				return 1;
			}
			
			outputStream.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
		
		return 0;
	}
}