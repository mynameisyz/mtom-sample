package rst.sample.mtom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import rst.sample.mtom.client.ws.DocumentsClient;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(final String[] args) throws Exception {
		final SpringApplication springApplication = new SpringApplication(
				Application.class);
		ApplicationContext ctx = springApplication.run();
		DocumentsClient documentsClient = ctx.getBean(DocumentsClient.class);

		
		/*
		int size = readSize();
		while (size > 0) {
			documentsClient.storeDocument(size);
			size = readSize();
		}
		*/
		readInput(documentsClient);
		
		
		System.out.println("exit");
	}

	private static boolean readInput(DocumentsClient documentsClient) throws IOException {
		System.out.println("Input file: <path> <filename> <author>");
		String line = readLine();
		if (line.equals("")) return false;
		
		String[] inputs = line.split(" ");
		documentsClient.storeDocument(inputs[0], inputs[1], inputs[2]);
		return true;
	}
	
	private static int readSize() throws IOException {
		Integer result = null;
		while (result == null) {
			System.out
					.printf("\nenter size of document to upload, or just press enter to exit: ");
			String line = readLine();
			if (line == null || line.trim().isEmpty()) {
				return 0;
			}
			try {
				result = Integer.parseInt(line);
				if (result < 1) {
					System.out.println("enter an integer > 0");
					result = null;
				}
			} catch (NumberFormatException e) {
				System.out.println("not a valid integer: " + line);
				result = null;
			}
		}
		return result;
	}

	private static String readLine() throws IOException {
		return new BufferedReader(new InputStreamReader(System.in)).readLine();
	}
}
