
# PetConnect

PetConnect ist eine moderne Webanwendung, die Tierhalter mit zuverlässigen Betreuern verbindet. Die Plattform bietet Profile, Bewertungen, Chatfunktionen und eine einfache Vermittlung von Tierservices wie Gassi gehen, Pflege und Urlaubsbetreuung. Ziel ist es, Vertrauen und Gemeinschaft zwischen Tierfreunden digital zu stärken.

## Ziel und Motivation

Viele Haustierbesitzer finden kurzfristig keine geeignete Betreuung. Gleichzeitig gibt es Menschen, die gerne Tiere betreuen würden, aber keine passende Plattform finden. PetConnect bringt beide Seiten zusammen. Die Anwendung soll unkompliziert, sicher und effektiv funktionieren, damit Tierfreunde sich gegenseitig helfen können.

## Funktionen im Überblick

- Betreuer nach Ort und Verfügbarkeit finden
- Nutzerprofile mit Beschreibung, Bewertungen und Haustierbildern
- Echtzeit-Chat mit Benachrichtigungen
- Bewertungssystem mit Sternen und Kommentaren
- Anzeige und Pflege von eigenen Verfügbarkeiten
- Upload von bis zu vier Bildern pro Haustier
- Notfallknopf zum Rufen von Hilfe

## Projektstruktur

```
.gitlab-ci.yml
docker-compose.yml
Dockerfile
README.md
src/
  main/
    java/
    resources/
  test/
.env
```

## Dienste

### Backend

Das Backend basiert auf Spring Boot und stellt eine REST-API zur Verfügung. Die Daten werden in einer PostgreSQL-Datenbank gespeichert. Die Anwendung enthält Funktionen wie Nutzerverwaltung, Verfügbarkeiten, Bewertungen, Chat und Notfallmeldungen.

- Spring Boot Port: 8003
- Quellcode: src/main/java/
- Konfiguration: application.properties und .env

### Datenbank

Die PostgreSQL-Datenbank speichert alle Nutzer-, Bewertungs- und Verfügbarkeitsdaten.

- Port: 5432
- Konfiguration: In docker-compose.yml und .env definiert

## Docker Compose

Die Datei docker-compose.yml definiert alle Services:

- app: Spring Boot REST-API
- db: PostgreSQL Datenbank

## GitLab CI/CD

Die .gitlab-ci.yml Datei beschreibt die CI/CD-Pipeline, mit der das Projekt gebaut und getestet wird. Docker-Images werden automatisch erstellt und in die GitLab Registry geladen.

## Anwendung lokal ausführen

### Voraussetzungen

- Docker und Docker Compose installiert

### Starten

```sh
docker-compose up --build
```

### Zugriff

- Backend API: http://localhost:8003
- Datenbank: lokal erreichbar auf Port 5432

## Verwendete Technologien

### Backend
- Java mit Spring Boot
- RESTful API Design
- Spring Security für Authentifizierung
- JPA mit PostgreSQL

### Frontend
- Thymeleaf mit HTML, CSS und JavaScript

### Infrastruktur und Tools
- Docker und Docker Compose
- Maven als Build-Tool
- WebSocket (SockJS + STOMP) für den Chat
- JUnit und Mockito für Tests

## Testen

Tests können lokal über Maven ausgeführt werden:

```sh
mvn test
```

Das Projekt enthält:
- Unit-Tests für Controller, Services und Repositories
- Integrationstests mit MockMvc
- Verwendung von Mockito für isolierte Tests

## Softwarearchitektur

Die Anwendung folgt dem MVC-Prinzip (Model-View-Controller), ergänzt durch eine klare Schichtung nach Verantwortlichkeiten:

### 1. Architekturüberblick

Model-View-Controller (MVC) sorgt für eine klare Trennung:

- Model: Repräsentiert die Domänendaten (z. B. AppUser, Bewertung, PetPhoto)
- View: Dynamische HTML-Seiten mit Thymeleaf
- Controller: Verarbeitet Anfragen, ruft Services auf und gibt Daten an die View weiter

Zusätzlich folgt die Anwendung einer mehrschichtigen Architektur:

- Controller-Schicht: Verantwortlich für die REST-Endpunkte (z. B. /api/users)
- Service-Schicht: Enthält die Geschäftslogik (z. B. Validierung, Verarbeitung)
- Repository-Schicht: Abstraktion zur Datenbank mit Spring Data JPA
- Konfigurationsschicht: Sicherheit, WebSockets, Ressourcenverwaltung

### 2. Kommunikation und Ablauf

- Frontend ↔ Backend: HTTP über REST-Endpunkte
- Live-Kommunikation: WebSocket (STOMP über SockJS) für den Chat
- Datenbankzugriff: ORM über JPA mit PostgreSQL

### 3. Sicherheit und Authentifizierung

- Login und Registrierung mit Spring Security
- Benutzerrollen zur Unterscheidung zwischen Tierhalter und Betreuer
- Zugriffsschutz über HTTP-Security-Konfiguration

### 4. Deployment-Struktur

Die Anwendung ist containerisiert:

- Backend: Java Spring Boot Service
- Datenbank: PostgreSQL
- Docker Compose: Startet beide Container und verbindet sie im gleichen Netzwerk

### 5. Erweiterbarkeit

Die modulare Struktur erlaubt es, neue Funktionen wie Buchungen, Zahlungsintegration oder Kalenderansichten problemlos zu ergänzen, da jede Schicht klar definiert und entkoppelt ist.

## Hinweise zur KI-Unterstützung

Zur Qualitätssicherung und sprachlichen Korrektur wurde KI eingesetzt. Die KI hat insbesondere bei der Verbesserung von Dokumentationsabschnitten geholfen, wurde aber nicht zur Codegenerierung verwendet.

## Lizenz

Dieses Projekt dient ausschließlich Studien- und Lernzwecken. Eine kommerzielle Nutzung ist nicht vorgesehen.
