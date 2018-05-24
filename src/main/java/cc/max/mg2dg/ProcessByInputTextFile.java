package cc.max.mg2dg;

import static cc.max.mg2dg.utils.Constants.RESPOND_MUTE;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import cc.max.mg2dg.utils.CheckAndRespond;

public class ProcessByInputTextFile {

	public static final Logger logger = (Logger) LogManager.getLogger(ProcessByInputTextFile.class);

	public static void main(String[] args) throws IOException {
		System.out.println("Please key in the text file path end with ENTER");
		Scanner scanner = new Scanner(System.in);
		BufferedReader reader = null;
		try {
			String filePath = scanner.nextLine();
			InputStream is = new FileInputStream(filePath);
			reader = new BufferedReader(new InputStreamReader(is));
			String input = null;
			while (true) {
				input = reader.readLine();
				if (input != null) {
					String respond = CheckAndRespond.process(input);
					if (!RESPOND_MUTE.equals(respond)) {
						System.out.println(respond);
					}
				} else {
					break;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			logger.error("file not found", e);
			System.out.println("File not found, please run again and using correct file path");
		} catch (IOException e) {
			logger.error("IOException orrcured", e);
			System.out.println("IOException orrcured, please run again with correct input file");
		} finally {
			scanner.close();
		}

	}

}
