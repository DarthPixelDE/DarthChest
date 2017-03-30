Vorweg:
Javadocs sind Größtenteils vorhanden, teilweise aber noch nicht vollständig in Testklassen, View-Klassen und evtl vereinzelt an anderen Punkten.
Testklassen existieren zu allen (interessanten) Methoden zu allen Model- und Controlklassen.
Tests sollten umfangreich genug sein, müssen aber noch nicht alle möglichen Fälle umfassen.
Funktionalitäten der Controlklassen sollten alle vollständig und funktionierend sein, aber nicht alle sind auch in der GUI integriert.
Modelklassen sollten vollständig sein und auch nur noch leicht verändert werden.
Alle Klassen können serialisiert werden, und die dafür nötigen Klassen sind vorhanden, sowie die Funktionalität implementiert. Serialisiert werden Daten während das System arbeitet, und geladen bevor das System startet. Es gibt eventuell noch Fehler. Falls diese auftreten sollte ein Löschen der .ser Dateien im Ordner saveData, in den jeweiligen Ordnern für die Klassen alle Fehler, die wir bisher bemerkt haben beheben.
Das Objektentwurfheft ist an überall bearbeitet, jedoch eventuell etwas veraltet, weil wir heute(08.03) Änderungen an der Struktur des Systems vorgenommen haben. Manche Textaspekte sind noch nicht vollständig.
 

Funktionalität der GUI:
Starten des Systems über die MainView Klasse
Nach Start hat man Sicht auf das gesamte Sortiment. In der Kopfleiste kann man das Sortiment nach Begriffen (durch das Textfeld) und nach Kategorien ( durch das Auswahlmenü) durchsuchen.
Ein Artikel kann auf der Sortimentliste angeklickt werden, um zusätzliche Informationen ein zu sehen.
Durch einen Klick in die obere linke Ecke wird ein zusätzliches Menü aufgerufen, in dem man sich anmelden kann.
Nach Klick auf anmelden wird ein neues Fenster aufgerufen, in dem man seine Daten anmelden kann (muss vorher registiert werden).
Durch Drücken auf Registrieren kann man ein Konto als Kunde anlegen. Nach ausfüllen aller Daten auf Registrieren drücken.
Erfolgreiches registrieren meldet gleichzeitig als Kunde an.
Vor dem Anmelden eine Rolle anklicken und dann seine Daten eingeben. Zur Zeit werden nur Kundendaten auf Korrektheit überprüft, alle anderen Rollen können sich mit beliebigen Kombinationen anmelden.
Nach dem Anmelden werden spezifische Funktionen durch klicken in der oberen linken Ecke dargestellt.
Jede Rolle kann sich nach dem anmelden auch wieder abmelden.

Rollenspezifische Funktionen nach anmelden:

1) Kunde:
-Kann das Sortiment weiterhin durchsuchen, jetzt zusätzlich nach anmelden Artikel zu seinem Warenkorb hinzufügen, in dem er diese anklickt und dann auswählt.
-Kann seinen Warenkorb auswählen und Artikel in diesem ansehen. Artikel kaufen funktioniert derzeit nicht. Man kann derzeit zusätzlich Artikel im Warenkorb anklicken und erneut hinzu fügen. Dies ist nicht so vorgesehen.
-Bestellhistorie anklicken zeigt eine Liste von zuvor getätigten Bestellungen. Man kann bei jeder Bestellung diese wiederholen und stornieren. Derzeit wird in der GUI nicht von Status der Bestellungen unterschieden.
-Kundendaten anklicken ruft ein Formular zum ändern seiner Kundendaten auf. Speichern ist derzeit nicht möglich. Hier kann man zusätzlich sein Konto löschen, dies funktioniert. Danach wird man abgemeldet.
-Kann seinen Vor und Nachnamen nach klicken oben links sehen

2)Fahrer:
-Hat derzeit keine Überprüfung der Daten. Also kann jede Kombination von Nutzer Name und Passwort zum anmelden benutzt werden.
-Sieht nach anmelden verfügbare Fahrzeuge, und kann sich an einem anmelden. Dies funktioniert derzeit nicht.
-Kann seinen vor- und nachnamen nach klicken oben links ansehen.

3)Lagerist
-Hat derzeit keine Überprüfung der Daten. Also kann jede Kombination von Nutzer Name und Passwort zum anmelden benutzt werden.
-Kann sich den Lagerbestand ansehen
-Abmelden funktioniert derzeit nur, wenn man sich in der Bestand Oberfläche befindet
-Kann ein Wechseln zwischen den Oberflächen ist möglich, jedoch hat jede Oberfläche wenig bis gar keine Funktionalität.

4) Inhaber kann sich an- und abmelden. Sonst hat der Inhaber bisher keine Funktionalitäten.
Anmelden auch hier ohne Überprüfung.







# Sopra Basisprojekt
- Code im Ordner `src`
- Tests im Ordner `test`
- Compilieren des Codes und Ausführen der Tests via `mvn test`
- Ausführen des Programms via `mvn exec:java`
- JavaDoc unter `target/site/apidocs` generieren via `mvn javadoc:javadoc`
- Konfiguration des Maven Projektes in der `pom.xml`
 - Setzen der Hauptklasse die die Main-Methode enthält unter `plugins` im `exec-maven-plugin`
 - Hinzufügen von weiteren Bibliotheken unter `dependencies`
- Konfiguration der Gitlab Continuous Integration in der `.gitlab-ci.yml`
- Ausführen der Tests im Docker container: `docker build -t sopra-mvn-test . && docker run -it sopra-mvn-test`

# Warm-up-Aufgabe
- Auschecken des initialen Commits via `git checkout init`
- Anlegen eines eigenen Branches via `git checkout -b warmup-<vorname>-<nachname>`
- Pushen des eigenen Branches via `git push -u origin`
- Implementieren und ständiges commiten + pushen
- Taggen der finalen Version via `git tag warmup-final-<vorname>-<nachname>`
- Pushen des Tags via `git push origin warmup-final-<vorname>-<nachname>`
