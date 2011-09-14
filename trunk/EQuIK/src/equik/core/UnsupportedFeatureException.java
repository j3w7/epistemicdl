package equik.core;

public class UnsupportedFeatureException extends Exception {

	public static void fire() {
		try {
			throw new UnsupportedFeatureException();
		} catch (UnsupportedFeatureException e) {
			e.printStackTrace();
		}
	}

}
