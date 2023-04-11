package application.util;

import java.util.Random;
import java.util.UUID;

/**
 * Utility class for random number generation & misc.
 * 
 * @author Adriana R.F.
 * @version 1.0 Single
 */
public class Util {
	
	private final static Random RANDOM = new Random();
	private final static int MAX_NUM = 100;
	
	public static int generateRandomMillis() {
		return RANDOM.nextInt(11) * 1000;
	}
	
	public static int generateRandomNumber() {
		return RANDOM.nextInt(MAX_NUM+1);
	}
	
	public static String generateRandomPayload() {
		return "FR" + UUID.randomUUID().toString().substring(0,20);
	}

}
