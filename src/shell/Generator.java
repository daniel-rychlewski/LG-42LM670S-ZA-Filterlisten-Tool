package shell;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

// Das Tool generiert je nach Eingabe im selben Verzeichnis eine Black- oder Whitelist fuer einen LG Smart TV. Es wurde am Smart TV 42LM670S-ZA mit der Firmware-Version 04.62.12 getestet.
public class Generator {
	
	/* Der Scanner liest die Eingaben des Nutzers in der Kommandozeile. Bei einem "j" erfolgt der Eintrag in die Datei und bei einem "n" nicht.
	 * Sonstige Eingaben zaehlen als unerwartet und sorgen dafuer, dass das Programm beendet wird. */
	private final Scanner scanner = new Scanner(System.in);
	// Der FileWriter schreibt die Domains in eine Datei, sofern auf die Datei schreibend zugegriffen werden kann.
	private FileWriter writer;
	
	/**
	 * In der Main-Methode wird je nach Wunsch des Nutzers eine Blacklist oder Whitelist generiert, die seinen Beduerfnissen entspricht. 
	 * @param args Zu diesem Zweck irrelevante Kommandozeilenparameter
	 */
	public static void main(String[] args) {
		Generator generator = new Generator();
		generator.starteTool();
		
		String ersteEingabe = generator.scanner.next();
		if (ersteEingabe.equals("j")) {
			// Blacklist soll generiert werden
			generator.erstelleBlacklist(generator.scanner);
		} else if (ersteEingabe.equals("n")) {
			// Whitelist soll generiert werden
			generator.erstelleWhitelist(generator.scanner);
		} else {
			generator.beenden("Unerwartete Eingabe. Das Programm wird jetzt beendet", generator.scanner);
		}
	}

	/**
	 * Informiert den Nutzer ueber den Zweck des Tools und fragt danach, welche Filterliste erstellt werden soll.
	 */
	private void starteTool() {
		System.out.println("Dieses Tool erstellt fuer Ihren LG Smart TV eine Black- oder Whitelist, die in eine Fritzbox importiert werden kann.");
		System.out.println("Soll eine Blacklist (j) oder eine Whitelist (n) erstellt werden? [j/n]");
	}

	/**
	 * Diese Methode fuegt der Black-/Whitelist bei der Eingabe "j" die gewuenschten Domains hinzu, sodass diese blockiert/erlaubt werden.
	 * @param frage An den Nutzer gerichtete Frage, welches Feature verboten (Blacklist) / erlaubt (Whitelist) sein soll
	 * @param fqdn Domains, die fuer das betrachtete Feature relevant sind
	 */
	private void hinzufuegen(String frage, String... fqdn) {
		System.out.println(frage+" [j/n]");
		String eingabe = scanner.next(); 
		if (eingabe.equals("j")) {
			// Nutzer hat bestaetigt, dass die Eintraege aus fqdn hinzugefuegt werden sollen
			for (String domain : fqdn) {
				try {
					writer.write(domain+"\n");
				} catch (IOException e) {
					beenden("Es ist ein Problem beim Schreiben der Datei aufgetreten. Das Programm wird jetzt beendet", scanner);
					return;
				}
			}
		} else if (eingabe.equals("n")) {
			// Kein Eintrag wird hinzugefuegt
		} else {
			beenden("Unerwartete Eingabe. Das Programm wird jetzt beendet", writer, scanner);
		}
	}

	/**
	 * Erstellt im selben Verzeichnis wie dieses Programm eine Blacklist als Textdatei.
	 * @param scanner Wird verwendet, um die Eingaben des Nutzers zu erfahren und passend darauf zu reagieren
	 */
	private void erstelleBlacklist(Scanner scanner) {
		try {
			writer = new FileWriter("Blacklist_" + System.currentTimeMillis() + ".txt");
			writer.write("kr.lgtvsdp.com\n");
		} catch (IOException e) {
			beenden("Es ist ein Problem beim Schreiben der Datei aufgetreten. Das Programm wird jetzt beendet", scanner);
			return;
		}

		hinzufuegen("Sollen Online-Dienste von LG auf dem Smart TV blockiert werden?",
				"DE.lgtvsdp.com");
		hinzufuegen("Soll Werbung von LG-Servern blockiert werden?",
				"DE.ad.lgsmartad.com",
				"de.ad.lgsmartad.com");
		hinzufuegen("Soll das Tracking des Werbeverhaltens von LG-Servern blockiert werden?",
				"DE.info.lgsmartad.com",
				"de.info.lgsmartad.com");
		hinzufuegen("Soll Werbung von Drittservern blockiert werden?",
				"cdn.smartclip.net",
				"s1.adform.net");
		hinzufuegen("Soll das Tracking des Werbeverhaltens von Drittservern blockiert werden?",
				"stats.smartclip.net",
				"stats-irl.sxp.smartclip.net",
				"track.adform.net");
		hinzufuegen("Sollen Icons im LG App Store blockiert werden?",
				"ngfts.lge.com");
		hinzufuegen("Soll die Suche nach Firmware-Updates blockiert werden?",
				"snu.lge.com");
		hinzufuegen("Soll das Herunterladen von Firmware-Updates blockiert werden?",
				"su.lge.com");

		/* Die obige Liste kann bei Bedarf aktualisiert oder hier erweitert werden */

		beenden("Die Blacklist wurde erfolgreich generiert. Das Programm wird jetzt beendet", writer, scanner);
	}

	/**
	 * Erstellt im selben Verzeichnis wie dieses Programm eine Whitelist als Textdatei.
	 * @param scanner Wird verwendet, um die Eingaben des Nutzers zu erfahren und passend darauf zu reagieren
	 */
	private void erstelleWhitelist(Scanner scanner) {
		try {
			writer = new FileWriter("Whitelist_" + System.currentTimeMillis() + ".txt");
		} catch (IOException e) {
			beenden("Es ist ein Problem beim Schreiben der Datei aufgetreten. Das Programm wird jetzt beendet", scanner);
			return;
		}

		hinzufuegen("Sollen Online-Dienste von LG weiterhin auf dem Smart TV funktionieren?",
				"DE.lgtvsdp.com");
		hinzufuegen("Sollen Icons im LG App Store weiterhin angezeigt werden koennen?",
				"ngfts.lge.com");
		hinzufuegen("Soll die App 'Tagesschau' weiterhin funktionieren?",
				"www.tagesschau.de");
		hinzufuegen("Soll die App 'MK IPTV' weiterhin funktionieren?",
				"51.255.48.71",
				"tools.mkvod.info",
				"live.netd.com.tr");
		hinzufuegen("Soll die App 'Amazon Instant Video' weiterhin funktionieren?",
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
		hinzufuegen("Soll die App 'YouTube' weiterhin funktionieren?",
				"www.youtube.com",
				"s.ytimg.com",
				"youtube.googleapis.com",
				"www.youtube-nocookie.com",
				"www.googleapis.com",
				"i.ytimg.com",
				"i1.ytimg.com",
				"googlevideo.com");

		/* Die obige Liste kann bei Bedarf aktualisiert oder hier erweitert werden */

		beenden("Die Whitelist wurde erfolgreich generiert. Das Programm wird jetzt beendet", writer, scanner);
	}

	/**
	 * Beendet die Ausfuehrung des Programms mit einer passenden Ausgabe.
	 * @param ausgabe Text, der dem Nutzer zum Abschluss der Programmausfuehrung mitgeteilt werden soll
	 * @param writer Objekt, das vorhin in eine Datei geschrieben hat und hier geschlossen wird
	 * @param scanner Objekt, das vorhin Nutzereingaben erkannt hat und in einer Hilfsmethode geschlossen wird
	 */
	private void beenden(String ausgabe, FileWriter writer, Scanner scanner) {
		try {
			writer.close();
		} catch (IOException e) {
			beenden("Es ist ein Problem beim Schreiben der Datei aufgetreten. Das Programm wird jetzt beendet", scanner);
			return;
		}
		beenden(ausgabe, scanner);
	}
	/**
	 * Hilfsmethode fuer das Beenden des Programms, die aufgerufen wird, wenn kein FileWriter vorhanden ist, den man schliessen koennte, oder ein Problem mit diesem aufgetreten ist.
	 * @param ausgabe Text, der dem Nutzer zum Abschluss der Programmausfuehrung mitgeteilt wird
	 * @param scanner Objekt, das vorhin Nutzereingaben erkannt hat und hier geschlossen wird
	 */
	private void beenden(String ausgabe, Scanner scanner) {
		System.out.println(ausgabe);
		scanner.close();
		System.exit(-1);
	}
}