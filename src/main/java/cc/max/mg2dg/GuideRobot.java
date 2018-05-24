package cc.max.mg2dg;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import static cc.max.mg2dg.utils.Constants.*;

import cc.max.mg2dg.utils.CheckAndRespond;

public class GuideRobot {

	public static final Logger logger = (Logger) LogManager.getLogger(GuideRobot.class);

	public static final String EXIT = "exit";

	private static Scanner scanner = null;

	public static void main(String[] args) {
		System.out.println("Hello! what should I do for you? (Type 'exit' to end conversation.)");

		// initialize scanner for system in
		scanner = new Scanner(System.in);
		String respond = null;

		do {

			String input = scanner.nextLine();
			if (EXIT.equalsIgnoreCase(input)) {
				System.out.println("Bye.");
				break;
			}
			respond = CheckAndRespond.process(input);
			if (!RESPOND_MUTE.equals(respond)) {
				System.out.println(respond);
			}

		} while (true);
	}

}
