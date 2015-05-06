import java.io.Serializable;

/**
 * 
 */

/**
 * @author no_author
 *
 */
public class NoiseFunction implements Serializable {
	private Sample originalSample;

	public NoiseFunction(Sample originalSample) {
		this.originalSample = originalSample;
	}
}