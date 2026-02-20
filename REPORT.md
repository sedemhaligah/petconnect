
# Report for 02.01.2025

### Reflection Team Member 1

In diesem Sprint habe ich...

### Reflection Team Member 2

In diesem Sprint habe ich...

### Reflection Team Member 3

In diesem Sprint habe ich...

### Reflection Team Member 4  

In diesem Sprint habe ich...

# Report for 01.01.2025


# Report for 04.05.2025
### Reflection Team Member 1 (Yaser)

In diesem Sprint habe ich an zwei wichtigen Frontend-Komponenten gearbeitet:

Betreuerfinden-Seite:
Das HTML vollständig strukturiert und mit semantischen Elementen überarbeitet.

Eine Suchfunktion nach Ort/PLZ eingebaut (filterCaregivers()), die dynamisch auf Benutzer-Eingaben reagiert.

Das visuelle Design mit CSS modernisiert (rundere Karten, Schatten, Farben, responsive Layouts).

Die Header-Navigation dynamisch gemacht: sie zeigt je nach Login-Status den Profil-Link, Benutzernamen und Abmelde-Button.

Zusätzliche Beispielkarten für Tierbetreuer erstellt (Anna, Lisa, Tom, Max).

Passwort-vergessen-Seite:
Eine neue Seite mit einem Zurücksetzen-Formular erstellt (forgotPassword.html).

Eingabefeld für E-Mail und Button zum Versenden des Links.

Seite visuell gestaltet mit CSS: Logo, Begrüßung, Button, Footer und Social Links.

### Reflection Team Member 2 (Karl)
### Backend-Entwicklung für die Profilseite
- Umsetzung der Backend-Funktionalität zur Unterstützung der Profilseite.
- Dynamisches Auslesen der Daten im Frontend mit Hilfe von Thymeleaf.
- Benutzer können ihre persönlichen Daten bearbeiten; alle Änderungen werden in der Datenbank gespeichert und angezeigt.

### Verfügbarkeitsverwaltung für Betreuer (Kalenderfunktion)
- Entwicklung einer Funktion zur Verwaltung der Verfügbarkeit von Betreuern über eine Kalenderansicht.
- Sicherstellung, dass die ausgewählten Verfügbarkeitszeiten korrekt in der Datenbank gespeichert werden.

### Funktion zur Diensterstellung
- Implementierung einer Funktion, mit der Benutzer neue Dienste erstellen können.
- Benutzer können alle notwendigen Details für den Dienst eingeben, die anschließend in der Datenbank gespeichert werden.


### Reflection Team Member 3 (Youssef)
### Google Kalender Integration
Diese Komponente zeigt die Integration des **Google Kalenders** über die **Google Calendar API**.

### Reflection Team Member 4 (Haval)
###Product Backlog Erstellung
In diesem Sprint habe ich das Product Backlog für PetCareConnect erstellt und finalisiert,
inklusive Priorisierung und Strukturierung der Anforderungen für das MVP.

## Aktueller Stand

**Frontend**:
- Google API eingebunden 
- Kalenderanzeige mit FullCalendar
- Google OAuth-Anmeldung via `gapi` & `gis`
- Termine können leider aus dem Google Kalender noch nicht gebucht werden.

**Backend** (nicht vollständig):
- Erste Controller in Spring Boot sind bereits implementiert
- Speicherung von Terminen erfolgt derzeit ausschließlich über die Google Calendar API 
- Keine persistente Speicherung in einer eigenen Datenbank implementiert

## Technologien

- HTML + JavaScript
- [FullCalendar.js](https://fullcalendar.io/)
- Google Calendar API v3
- OAuth2.0 TokenClient (Browserbasiert)
- CSS
- Thymeleaf

## erwarteter Ablauf 
- Anmeldung mit Google :
  Nutzer klicken auf „Mit Google anmelden“. OAuth 2.0 wird gestartet, und bei Erfolg wird der Zugriff auf den Kalender gewährt.

- Kalender wird geladen:
  Die Google Calendar API wird verwendet, um Termine aus dem Primärkalender des angemeldeten Nutzers zu laden und anzuzeigen.

- Terminbuchung :
  Aktuell wird der Termin nur bei Google gespeichert, nicht im eigenen Backend.

- Abmelden:
  Der Nutzer kann sich per Klick auf „Abmelden“ wieder ausloggen. Das Token wird widerrufen, und Termine werden aus dem Kalender entfernt.
---
## Report für 16.05.2025
Reflection Team Member 1 (Yaser)
In diesem Sprint habe ich mich auf die Testabsicherung, die Optimierung bestehender Funktionen und die Qualitätssicherung vor dem Push konzentriert:

# Unit Tests und Codequalität:
Unit Tests für UserAvailabilityService erstellt (Methoden: add, get, remove)
Mockito verwendet für das Mocken von Repository-Zugriffen
Sicherstellung, dass alle Tests automatisch per mvn test durchlaufen
# Git pre-push Hook:
Lokaler pre-push Hook eingerichtet, der mvn test vor jedem Push ausführt
Push-Vorgang wird bei fehlschlagenden Tests automatisch blockiert
Ziel: Fehler frühzeitig erkennen, keine fehlerhaften Builds auf GitLab
# CI/CD mit GitLab:
.gitlab-ci.yml korrigiert und stabilisiert (POM-Fehler behoben)
Testprofil test über application-test.yml integriert
CI-Tests laufen automatisch bei jedem Commit
# Kalender-Optimierung:
Verfügbarkeitslogik überarbeitet (Datenmodell & Service verbessert)
Kalenderansicht im Frontend nutzerfreundlicher gestaltet
Fehlerbehandlung für doppelte/vergangene Einträge ergänzt
# Profilseite & Bearbeitung:
Profilseite funktional und visuell optimiert
Datenaktualisierung (z. B. Adrese) im Backend refaktoriert
Validierung verbessert (leere Felder, ungültige Eingaben)
# Chat-Overlay auf der Profilseite integriert:
Ein Overlay für Direktnachrichten wurde eingebaut, das sich beim Klick auf „Nachricht senden“ öffnet und schließt.
# Synchronisierung der IDs:
Alle relevanten HTML-Elemente (Overlay, Nachrichtenliste, Eingabefeld, Senden-Button) wurden mit eindeutigen IDs versehen und mit dem JavaScript abgeglichen.
# Nachrichten laden und senden:
JavaScript-Funktionen zum Laden (loadMessages) und Senden (sendMessage) von Nachrichten implementiert.
Nachrichten werden per Fetch-API an das Backend gesendet und nach erfolgreichem Senden automatisch neu geladen.
# Konversations-Handling:
Die Konversation zwischen zwei Nutzern wird beim Öffnen des Overlays gesucht oder beim ersten Senden automatisch angelegt.
# CSRF-Token-Handling verbessert:
Das CSRF-Token wird jetzt aus <meta>-Tags im HTML ausgelesen und im Header jedes Fetch-Requests gesetzt, um 403-Fehler zu vermeiden.
# Debugging & Fehlerbehebung:
Debug-Ausgaben und Konsolen-Logs genutzt, um Fehlerquellen wie falsche IDs, doppelte Variablendeklarationen und fehlende Tokens zu identifizieren und zu beheben.
# UI/UX-Verbesserungen:
Overlay-Design per CSS angepasst, automatisches Scrollen der Nachrichtenliste nach unten, Schließen-Button für das Overlay ergänzt.

### Reflection Team Member 2 (Karl)
## Optimierung der lokalen Build-Pipeline
- Vereinfachung und Beschleunigung der lokalen Testumgebung durch Optimierung des Build-Skripts (bash.sh).
- Sicherstellung, dass Tests und Builds lokal zuverlässig laufen – auch im Zusammenspiel mit Docker-Containern.

##  Erweiterung der CI-Pipeline
- Integration automatischer Code-Formatierungs- und Linting-Prüfungen in die GitLab CI/CD-Pipeline.
- Fehlerhafte Formatierungen werden bereits im CI frühzeitig erkannt, was zu höherer Codequalität führt.

## JUnit Tests für AppUserController und AppUserService
- Erstellung umfangreicher Unit Tests mit JUnit für zentrale Backend-Komponenten.
- Fokus auf die Absicherung der Benutzerlogik und REST-Endpunkte.

##  Einführung eines Pre-Commit Hooks
- Einrichtung eines Pre-Commit Hooks mit mvn spotless:check.Gewährleistung, dass nur korrekt formatierter und überprüfter Code ins Repository gepusht wird.

##  Implementierung des Hochladens von Haustierfotos
- Beginn der Umsetzung einer Funktion zum Upload von Haustierbildern.
- Vorverarbeitung der Dateien zur Speicherung und Anzeige im Frontend vorbereitet.


### Reflexion Teammitglied 3 (Haval):

In diesem Sprint habe ich mich auf die Arbeit am Product Backlog, dem Notrufbutton und der „Kontaktiere uns“-Seite konzentriert. Dabei ging es vor allem um die Strukturierung und Weiterentwicklung von Anforderungen im Backlog, die Gestaltung und Funktionalität eines Notrufbuttons sowie den Aufbau einer Kontaktseite zur Nutzerkommunikation. Die Aufgaben erforderten sowohl konzeptionelles Arbeiten als auch die Umsetzung im Frontend.

Was gut lief:
Ich konnte mich schnell in die Aufgaben einarbeiten und habe die Anforderungen und Zusammenhänge zügig verstanden, was mir geholfen hat, effizient zu arbeiten.

Was nicht gut lief:
Leider konnte ich nicht wie geplant weiterarbeiten, da meine Docker-Umgebung nicht funktionierte und ich dadurch mit technischen Problemen zu kämpfen hatte. Für den nächsten Sprint möchte ich diesen Aspekt verbessern, indem ich sicherstelle, dass meine Entwicklungsumgebung frühzeitig funktioniert und ich auftretende Probleme schneller behebe.



### Reflection Team Member 4 (Youssef)
- Umsetzung einer funktionalen Bewertungsseite, die es Nutzer:innen ermöglicht,
  eine Bewertung mit Kommentar abzugeben und im Backend zu speichern.
- Erreichte Ergebnisse:
  - Frontend (HTML, CSS, JavaScript):
    - Gestaltung eines einfachen Bewertungsformulars mit den Feldern:
      - Name, Kommentare und Bewertung mit Sterne (Leider diese Funktion funktioniert nicht)
      - Integration grundlegender Styles (CSS)
  - Backend (Spring Boot):
    - Erstellung einer Bewertung-Entität mit relevanten Attributen (id, user, autor, kommentar, bewertung und datum)
    - Implementierung eines Repository-Interfaces mit Spring Data JPA
    - Aufbau eines REST-Controllers
- Noch offen / Nicht abgeschlossen:
  - Sternbewertung: Die grafische Darstellung und Interaktion mit Sternen (1–5) funktioniert leider nicht.
  -  Anzeige vorheriger Kommentare: Es fehlt ein Bereich im Frontend, um bereits abgegebene Bewertungen anzuzeigen
  - Tests fehlen: Es wurden noch keine Unit- oder Integrationstests geschrieben, um die Backend-Funktionalität abzusichern.

# Report für 06.06.2025
Reflection Team Member 1 (Yaser)

Auf der Homepage (/) wird das Header-Layout fehlerhaft dargestellt:
Die Navigationslinks und Buttons (z. B. „Abmelden“) rutschen unter das Logo, 
anstatt wie vorgesehen horizontal daneben zu stehen. Auf anderen Seiten 
wie z. B. der Profilseite (/profile) funktioniert das Layout korrekt.

eim Klick auf den „NOTFALLKONTAKT“-Button im Notfall-Banner
(und auf die Notfall-Karte im Service-Bereich) soll ein zentriertes, modernes Modal-Fenster erscheinen.
Das Modal zeigt die Notfallnummer an und bietet die Möglichkeit, direkt anzurufen.
Das Fenster kann per X oder ESC-Taste wieder geschlossen werden.

### Reflection Team Member 2(Karl)
## Was gut lief

### Hohe Umsetzungsgeschwindigkeit
Die Aufgaben wurden zügig und mit minimalen Unterbrechungen bearbeitet. Übergänge zwischen Implementierung, Commit-Erstellung, Konfiguration und Debugging verliefen reibungslos und effizient.

### Klare Zieldefinition
Die Anforderungen – wie z. B. das Ausblenden des eingeloggten Nutzers in der Chatliste oder das Auslösen des E-Mail-Versands bei Formularübermittlung – wurden präzise formuliert, was eine zielgerichtete und schnelle Umsetzung ermöglichte.

### Direkte Problemlösung
Fehlermeldungen und Herausforderungen wurden unmittelbar und klar kommuniziert, was eine schnelle und lösungsorientierte Bearbeitung ermöglichte.

## Was nicht gut lief

### Tooling-Probleme (Git & Java-Version)
- GitLab verhinderte den Push auf den geschützten Hauptbranch, was den Entwicklungsfluss unterbrach.
- Mockito war mit der eingesetzten JDK-Version (23) nicht kompatibel, was zu Test- und Debuggingproblemen führte.

### Eingeschränkte Testsicherheit
Aufgrund von Testfehlern konnte keine vollständige Absicherung der Änderungen erfolgen. Dies erhöht das Risiko für unbeabsichtigte Nebenwirkungen. Die zugrundeliegenden Probleme lassen sich nur durch technische bzw. architektonische Entscheidungen nachhaltig lösen.

### Fehlende Anfangsabstraktion für E-Mails
Ein separater E-Mail-Controller war zu Beginn nicht vorhanden, was die Integration erschwerte. Eine frühzeitige Abstraktion hätte die Wiederverwendbarkeit und Wartbarkeit verbessert.



Reflection Team Member 3 (Haval)

In diesem Sprint habe ich an der Erstellung einer Kontaktseite sowie an der Umsetzung eines Notfallbuttons gearbeitet. 
Dabei lag der Fokus auf der Gestaltung einer benutzerfreundlichen Oberfläche für die Kontaktaufnahme und der Implementierung eines leicht zugänglichen Buttons für Notfälle. 
Beide Aufgaben umfassten sowohl Designaspekte als auch technische Umsetzung im Frontend.

Was gut lief:
Ich konnte zügig mit der Entwicklung starten und war in der Lage, Fehler schnell zu erkennen und zu beheben.
Besonders beim Notfallbutton hat mir gefallen, dass ich direkt sehen konnte, ob alles wie geplant funktioniert, 
was den Entwicklungsprozess sehr transparent und motivierend gemacht hat.

Was nicht gut lief:
Bei der Kontaktseite war es herausfordernd, ein sauberes und responsives Layout umzusetzen, das auf allen Bildschirmgrößen gut aussieht. 
Teilweise wirkte die Seite unübersichtlich, und ich musste mehrere Anläufe unternehmen, um die Benutzerführung zu verbessern. 

Für den nächsten Sprint möchte ich von Anfang an mehr Zeit für das UI-Design einplanen,
 um solche Nachbesserungen zu minimieren.



### Reflection Team Member 4 (Youssef)

Obwohl ich in den letzten zehn Tagen krank war, habe ich folgende Aufgaben erledigt:

- Frontend der Bewertungsseite optimiert:
  Ich habe das Frontend der Bewertungsseite verbessert, um die Benutzerfreundlichkeit zu steigern und die Performance zu optimieren.

- Sterne-Bewertungsfunktion implementiert:
  Eine neue Funktion zur Bewertung mit Sternen wurde erfolgreich hinzugefügt.

- Backend-Updates durchgeführt:
  Ich habe einige Updates im Backend vorgenommen, um Funktionen zu verbessern und Fehler zu beheben.

- Integrationstest erstellt und durchgeführt:
  Zusätzlich habe ich einen Integrationstest geschrieben und ausgeführt, um sicherzustellen, dass Frontend und Backend fehlerfrei zusammenarbeiten.


# Report für 20.06.2025

### Reflection Team Member 1(Yaser)
Ich habe an der Optimierung der Login- Homepag und Profeilbarbeitung und Chat und Registrierungsseiten gearbeitet. 
Dabei habe ich das Layout verbessert, 
insbesondere die Ausrichtung von Checkboxen und Text (z. B. „Eingeloggt bleiben“, AGB-Zustimmung), 
damit sie sauber nebeneinander stehen. Ich habe Flexbox eingesetzt und die HTML-Struktur angepasst, um ein klares, responsives Design zu erreichen.

### Reflection Team Member 2(Karl)
In diesem Sprint haben wir die Benutzerfreundlichkeit unserer App mit echten Nutzer:innen getestet. Es war spannend zu sehen, wie sie mit der App umgehen. Vieles lief gut, aber manche Dinge waren überraschend schwierig.

Besonders aufgefallen ist, dass der Notfall-Button kaum gefunden wurde. Daraus haben wir gelernt, wie wichtig sichtbares und klares Design ist.

Ich habe gemerkt, wie wertvoll echtes Nutzerfeedback ist. Für den nächsten Sprint wollen wir die Erkenntnisse direkt umsetzen, vor allem bei der Profilbearbeitung und dem Notfallbereich.


### Reflection Team Member 3(Haval)
Nutzertests im aktuellen Sprint

In diesem Sprint habe ich unsere Webseite mit zwei Personen getestet, um weitere Erkenntnisse zur Benutzerfreundlichkeit zu gewinnen. Die erste Testperson war ein Student, die zweite hatte keine Vorerfahrung mit ähnlichen Anwendungen. Es war spannend zu beobachten, wie unterschiedlich sie mit der App umgegangen sind.

Was gut lief:
Beide Testpersonen konnten sich ohne größere Schwierigkeiten einloggen. Besonders erfreulich war, dass dieser zentrale Schritt reibungslos funktionierte – auch wenn die zweite Person dafür etwas länger brauchte.

Was Schwierigkeiten bereitete:
Die zweite Testperson hatte anfangs Probleme, die „Kontaktiere uns“-Seite zu finden. Das Missverständnis konnte zwar schnell geklärt werden, zeigt aber, dass hier noch Potenzial für eine klarere Navigation besteht.
Wie auch Karl bereits festgestellt hat, wurde der Notfall-Button von beiden Personen nicht direkt erkannt. Das bestätigt, wie wichtig ein auffälliges und intuitives Design für zentrale Funktionen ist.

Fazit und Ausblick:
Insgesamt verliefen die Tests positiv. Die wichtigsten Funktionen konnten genutzt werden, kleinere Hürden zeigen uns jedoch klar, wo wir im nächsten Sprint ansetzen sollten – insbesondere bei der Sichtbarkeit des Notfallbereichs und der allgemeinen Orientierung innerhalb der App.



### Reflection Team Member 4(Youseef)
In diesem Sprint habe ich die Bewertungsseite optimiert, um die Benutzerfreundlichkeit und Performance zu verbessern. Zusätzlich habe ich erste Repository-Test geschrieben, um die Funktionalität und Stabilität der Seite besser abzusichern.

Außerdem habe ich versucht, die geschriebenen Kommentare direkt auf der Seite zu laden, um die Nutzererfahrung zu verbessern. Leider hat diese Implementierung nicht wie erwartet funktioniert und bedarf weiterer Anpassungen.