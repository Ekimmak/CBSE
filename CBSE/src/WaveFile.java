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
public class WaveFile implements Serializable {
		// Format information.
		private short formatTag;
		private short channels;
		private int samplesPerSec;
		private int avgBytesPerSec;
		private short blockAlign;
		private short bitsPerSample;
		private short extSize;
		private short validBitsPerSample;
		private int channelMask;
		private int subFormat;

		public int ReadInWAV(String file) {
				try {
				// Create a new input stream for scanning the WAV file.
				FileInputStream wavStream = new FileInputStream(new File(file));
				
				// Skip RIFF chunk and scan in format information.
				wavStream.skip(16);
				
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
				
				// Create byte buffer to read in bytes. WAV is little endian by default.
				ByteBuffer byteBuffer;
				byteBuffer = ByteBuffer.wrap(byteFmtChunkSize);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				int fmtChunkSize = byteBuffer.getInt();
	
				// Read in some more information about the sample.
				wavStream.read(byteFormatTag);
				byteBuffer = ByteBuffer.wrap(byteFormatTag);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				formatTag = byteBuffer.getShort();
				
				wavStream.read(byteChannels);
				byteBuffer = ByteBuffer.wrap(byteChannels);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				channels = byteBuffer.getShort();
				
				wavStream.read(byteSamplesPerSec);
				byteBuffer = ByteBuffer.wrap(byteSamplesPerSec);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				samplesPerSec = byteBuffer.getInt();
				
				wavStream.read(byteAvgBytesPerSec);
				byteBuffer = ByteBuffer.wrap(byteAvgBytesPerSec);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				avgBytesPerSec = byteBuffer.getInt();
				
				wavStream.read(byteBlockAlign);
				byteBuffer = ByteBuffer.wrap(byteBlockAlign);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				blockAlign = byteBuffer.getShort();
				
				wavStream.read(byteBitsPerSample);
				byteBuffer = ByteBuffer.wrap(byteBitsPerSample);
				byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
				bitsPerSample = byteBuffer.getShort();
				
				System.out.println(fmtChunkSize + "|" + formatTag + "|" + channels + "|" + samplesPerSec + "|" +
				avgBytesPerSec + "|" + blockAlign + "|" + bitsPerSample);
				
				// Extensible WAV formats
				if(fmtChunkSize == 18)
				{
					wavStream.read(byteExtSize);
				}
	
				else if(fmtChunkSize == 40)
				{
					wavStream.read(byteExtSize);
					wavStream.read(byteValidBitsPerSample);
					wavStream.read(byteChannelMask);
					wavStream.read(byteSubFormat);
				}
	
				// For a non-PCM WAV format, scan in the fact chunk. If PCM WAV, skip this.
				if(formatTag != 0x0001) {

				}
	
				// Scan in the data chunk (PCM sample).
				else {
					// Skip to the PCM data chunk
					wavStream.skip(4);
					
					byte[] WAVEID = new byte[4];
					wavStream.read(WAVEID);
					byteBuffer = ByteBuffer.wrap(WAVEID);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					int val = byteBuffer.getInt();
					wavStream.skip(34);
					wavStream.skip(4);
					
					byte[] WAVEID2 = new byte[4];
					wavStream.read(WAVEID2);
					byteBuffer = ByteBuffer.wrap(WAVEID2);
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					int val2 = byteBuffer.getInt();
					System.out.println(val2);
					byte[] WAVEID3 = new byte[val2];
					wavStream.read(WAVEID3);
					byteBuffer = ByteBuffer.wrap(WAVEID3);
					
					byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
					System.out.println(WAVEID3.length);
					
					PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
					float j = 0;
					
					for(int i = 0; i < 44100; i+=4) {
						writer.println(j + ", " + byteBuffer.getInt());
						j+=0.01;
					}
					writer.close();

					//System.out.println((char)WAVEID2[0]);
					//System.out.println((char)WAVEID2[1]);
					//System.out.println((char)WAVEID2[2]);
					//System.out.println((char)WAVEID2[3]);
					
				}
				
				// Close the input stream.
				wavStream.close();
			}
			catch(Exception e) {	
			}

			return 0;
		}
		
		public short GetFormatTag() {
			return formatTag;
		}
		
		// Constructors.
		public WaveFile() {
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
		}
}
