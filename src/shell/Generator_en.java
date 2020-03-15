package shell;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// This tool generates a blacklist or whitelist in the same directory for an LG smart TV. It has been tested for the 42LM670S-ZA model with the firmware 04.62.12 installed.
public class Generator_en {
	
	private final Scanner scanner = new Scanner(System.in);
	private FileWriter writer;
	
	public static void main(String[] args) {
		Generator_en generator = new Generator_en();
		generator.startTool();
		
		String firstInput = generator.scanner.next();
		if (firstInput.equals("b")) {
			generator.createBlacklist(generator.scanner);
		} else if (firstInput.equals("w")) {
			generator.createWhitelist(generator.scanner);
		} else {
			generator.end("Unexpected input. The program will now stop", generator.scanner);
		}
	}

	private void startTool() {
		System.out.println("This tool will create a blacklist or whitelist for your LG smart TV, which can be imported into a router to filter requests.");
		System.out.println("Do you wish to generate a blacklist (b) or a whitelist (w)? [b/w]");
	}

	private void add(String question, String... fqdn) {
		System.out.println(question+" [y/n]");
		String userInput = scanner.next();
		if (userInput.equals("y")) {
			for (String domain : fqdn) {
				try {
					writer.write(domain+"\n");
				} catch (IOException e) {
					end("There has been a problem trying to write to the file. The program will now stop", scanner);
					return;
				}
			}
		} else if (userInput.equals("n")) {
		} else {
			end("Unexpected input. The program will now stop", writer, scanner);
		}
	}

	private void createBlacklist(Scanner scanner) {
		try {
			writer = new FileWriter("Blacklist_" + System.currentTimeMillis() + ".txt");
			writer.write("kr.lgtvsdp.com\n");
		} catch (IOException e) {
			end("There has been a problem trying to write to the file. The program will now stop", scanner);
			return;
		}

		add("Shall LG online services be blocked for the smart TV?",
				"DE.lgtvsdp.com");
		add("Shall ads from LG servers be blocked?",
				"DE.ad.lgsmartad.com",
				"de.ad.lgsmartad.com");
		add("Shall ad tracking by the LG servers be blocked?",
				"DE.info.lgsmartad.com",
				"de.info.lgsmartad.com");
		add("Shall third-party ads be blocked?",
				"cdn.smartclip.net",
				"s1.adform.net");
		add("Shall third-party tracking be blocked?",
				"stats.smartclip.net",
				"stats-irl.sxp.smartclip.net",
				"track.adform.net");
		add("Shall icons in the LG App Store be blocked?",
				"ngfts.lge.com");
		add("Shall firmware update search requests be blocked?",
				"snu.lge.com");
		add("Shall firmware update download requests be blocked?",
				"su.lge.com");

		/* expand the list above if desired */

		end("The blacklist has been successfully generated. The program will now stop", writer, scanner);
	}

	private void createWhitelist(Scanner scanner) {
		try {
			writer = new FileWriter("Whitelist_" + System.currentTimeMillis() + ".txt");
		} catch (IOException e) {
			end("There has been a problem trying to write to the file. The program will now stop", scanner);
			return;
		}

		add("Shall LG online services continue to work on this smart TV?",
				"DE.lgtvsdp.com");
		add("Shall icons continue to be shown in the LG App Store?",
				"ngfts.lge.com");
		add("Shall the app 'Tagesschau' continue to work?",
				"www.tagesschau.de");
		add("Shall the app 'MK IPTV' continue to work?",
				"51.255.48.71",
				"tools.mkvod.info",
				"live.netd.com.tr");
		add("Shall the app 'Amazon Instant Video' continue to work?",
				"atv-eu.amazon.com",
				"atv-ext-eu.amazon.com",
				"g-ecx.images-amazon.com",
				"g-ec2.images-amazon.com",
				"images-eu.ssl-images-amazon.com",
				"images-na.ssl-images-amazon.com",
				"ecx.images-amazon.com",
				"api.amazon.de",
				"eu.api.amazonvideo.com",
				"m.media-amazon.com",
				"akamaihd.net");
		add("Shall the app 'YouTube' continue to work?",
				"www.youtube.com",
				"s.ytimg.com",
				"youtube.googleapis.com",
				"www.youtube-nocookie.com",
				"www.googleapis.com",
				"i.ytimg.com",
				"i1.ytimg.com",
				"googlevideo.com");

		/* expand the list above if desired */

		end("The whitelist has been successfully generated. The program will now stop", writer, scanner);
	}

	private void end(String output, FileWriter writer, Scanner scanner) {
		try {
			writer.close();
		} catch (IOException e) {
			end("There has been a problem trying to write to the file. The program will now stop", scanner);
			return;
		}
		end(output, scanner);
	}

	private void end(String output, Scanner scanner) {
		System.out.println(output);
		scanner.close();
		System.exit(-1);
	}
}