

/**
 * 
 */

/**
 * @author no_author
 *
 */

public class componentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Get convertor.
		Convertor sampleConvertor = new Convertor();
		
		// Get sample information.
		Sample waveSample1 = new Sample("samples/1A220.wav");
		Sample waveSample2 = new Sample("samples/2A220.wav");
		Sample waveSample3 = new Sample("samples/3A220.wav");
		
		// Print sample information.
		sampleConvertor.setSample(waveSample1);
		waveSample1 = sampleConvertor.readInSample();
		System.out.println("DNR: " + sampleConvertor.getDNR() +" | Peak: " + sampleConvertor.getPeakAmp() + " | Min: " + sampleConvertor.getMinAmp());
		
		sampleConvertor.setSample(waveSample2);
		waveSample2 = sampleConvertor.readInSample();
		System.out.println("DNR: " + sampleConvertor.getDNR() +" | Peak: " + sampleConvertor.getPeakAmp() + " | Min: " + sampleConvertor.getMinAmp());
		
		sampleConvertor.setSample(waveSample3);
		waveSample3 = sampleConvertor.readInSample();
		System.out.println("DNR: " + sampleConvertor.getDNR() +" | Peak: " + sampleConvertor.getPeakAmp() + " | Min: " + sampleConvertor.getMinAmp());

		// Fill a sample with noise and save it to another file.
		NoiseFunction noiseFunction = new NoiseFunction();
		waveSample3.setFileName("NoiseTest.wav");
		noiseFunction.AddNoise(waveSample3, 0.30f, 0f);
		sampleConvertor.setSample(waveSample3);
		sampleConvertor.readOutSample();
	}
}
