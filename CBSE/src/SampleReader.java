import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
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
	public Sample readFromFile(String file) {
				Sample waveFile = new Sample();

				try {
				// Create a new input stream for scanning the WAV file.
				FileInputStream wavStream = new FileInputStream(new File(file));
				  
				// Used to traverse through the WAV to get chunk information.
				byte[] byteChunkID;
				int chunkID = 0;
				
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
						wavStream.read(byteSampleData );
						waveFile.setSampleData(byteSampleData);

						loopChunks = false;
					}
					else
					{
						wavStream.skip(chunkSize);
					}
				} 

				// Close the input stream.
				wavStream.close();
				
				// Print data out for testing purposes. 
				PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
					byteBuffer = ByteBuffer.wrap(waveFile.getSampleData());
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					float j = 0;
					
				for(int i = 0; i < 44100; i+=2) {
					writer.println(j + ", " + byteBuffer.getShort());
					j+=0.01;
				} 

				writer.close(); 
			}
			catch(Exception e) {	
				// Catch any exceptions [TBD].
			}
		
		return waveFile;
	}
}