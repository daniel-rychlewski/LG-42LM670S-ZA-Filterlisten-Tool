# LG 42LM670S-ZA Filterlisten-Tool

<b>\[Deutsch\]</b> Enthält ein Tool zum Generieren einer Filterliste nach den eigenen Bedürfnissen, das z.B. in eine Fritzbox eingelesen werden kann.
Unterstützt wird sowohl der Whitelist- als auch der Blacklist-Ansatz. Zum Generieren kann sowohl die Kommandozeile (Ordner `shell`) als auch ein GUI-basiertes Tool (Ordner `gui`) verwendet werden.
Gegenstand der Untersuchung ist der Smart-TV <b>LG 42LM670S-ZA</b> mit der Firmware 04.62.12.

<b>\[English\]</b> This repository contains a tool to generate a blacklist or whitelist for the smart TV LG 42LM670S-ZA (analyzed for the firmware 04.62.12), which can be done from the command line or with a graphical user interface. The idea is that while the Internet access of a smart TV does grant the user additional entertainment functionality, the user might want to prevent unwanted requests (e.g., for tracking purposes) to enhance his privacy.
Therefore, my tool allows to generate a custom filter list based on the user's smart TV usage, which can then be imported into a router to block/allow only the specified requests.
Unfortunately, it is not yet translated into English (but for the time being, a [translator](https://translate.google.com/#view=home&op=translate&sl=de&tl=en) might help).

## Theorie

Mit einer wachsenden Zahl an Geräten im Smart-Home entstehen neue Herausforderungen in den Bereichen Sicherheit und Datenschutz.

Obwohl der Internetzugriff von Smart-TVs mehr Komfort bieten kann, ermöglichen andere durch den Smart-TV getätigte Requests möglicherweise ein nicht gewünschtes Tracking des Nutzerverhaltens.

Um einen <b>Kompromiss aus Nutzbarkeit und Datenschutz</b> zu erreichen, bietet es sich an, unerwünschte Requests zu blockieren bzw. nur die gewünschten durchzulassen. Zudem habe ich in meiner Bachelorarbeit <i>"Informationsfluss eines Smart-TVs aus dem Heimnetzwerk"</i> die Auswirkungen der Manipulation von Zugriffen in Echtzeit sowie des Versuchs, verschlüsselte Verbindungen abzuhören, analysiert.
Die Ergebnisse habe ich in meiner [Verteidigung](https://github.com/daniel-rychlewski/LG-42LM670S-ZA-Filterlisten-Tool/blob/master/src/files/Pr%C3%A4sentation.pptx) dargestellt, in der ich die Ergebnisse auch im größeren Kontext von IoT eingeordnet habe. Zudem kann eine feingranularere Analyse des Verhaltens des Smart-TVs dem [Forschungsbuch](https://github.com/daniel-rychlewski/LG-42LM670S-ZA-Filterlisten-Tool/blob/master/src/files/Forschungsbuch.xlsx) entnommen werden.

## Installation

Beide Tools wurden mit Java 8 erstellt, sodass die Installation des JDKs für mindestens Java 8 empfehlenswert ist. Zum Ausführen des kommandozeilenbasierten Tools reicht der Aufuf der main-Funktion der Klasse `Generator.java` im Verzeichnis `shell`.

![Konsolenversion](https://github.com/daniel-rychlewski/LG-42LM670S-ZA-Filterlisten-Tool/blob/master/src/files/Tool%20f%C3%BCr%20Filterliste%20Output.png)

Zum Ausführen des Tools mit der graphischen Benutzeroberfläche genügt es, die main-Funktion von `WhiteOderBlacklistDialog.java` im Ordner `gui` aufzurufen. Will man eine jar-Datei wie [FilterlisteGUI.jar](https://github.com/daniel-rychlewski/LG-42LM670S-ZA-Filterlisten-Tool/blob/master/src/files/FilterlisteGUI.jar) zum Zweck der Portabilität erzeugen, führen die folgenden Schritte in IntelliJ IDEA zum Ziel:
* File -> Project Structure -> Artifacts -> Add (+) -> JAR -> From modules with dependencies -> Main Class (Ordnersymbol) `WhiteOderBlacklistDialog` -> 3x OK
* Build -> Build Artifacts -> soeben erzeugtes Artefakt auswählen, Build
* Im Ordner `out/artifacts/filterliste_jar` befindet sich die soeben generierte Datei `filterliste.jar`.

![GUI-Tool](https://github.com/daniel-rychlewski/LG-42LM670S-ZA-Filterlisten-Tool/blob/master/src/files/GUI-Tool.png)

Beide Toolvarianten generieren im aktuellen Verzeichnis jeweils eine Datei namens `[White/Black]list_zeitstempel.txt`, wobei der Zeitstempel eine große Zahl stellvertretend für die aktuelle Zeit zum Zeitpunkt des Generierens repräsentiert. Das folgende Diagramm fasst die Logik der Programme zusammen:

![Ablauf](https://github.com/daniel-rychlewski/LG-42LM670S-ZA-Filterlisten-Tool/blob/master/src/files/Programmablaufplan%20des%20Tools.jpg)
