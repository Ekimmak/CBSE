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
		Sample sample = new Sample("Sample1", new Byte[1024]);
		System.out.println("Test");
		System.out.println(sample.getName());
		System.out.println(sample.getData().length);
	}

}
