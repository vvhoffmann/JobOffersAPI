# JobOffersAPI

Aplikacja REST API do zarządzania ofertami pracy dla junior Java developerów.

---

## Opis

**JobOffersAPI** to aplikacja służąca do pobierania i zarządzania listą ofert pracy dla junior Java developerów. Dane są przechowywane w bazie MongoDB i regularnie aktualizowane co 3 godziny za pomocą wbudowanego schedulera.
Aplikacja zawiera testy jednostkowe dla domain oraz testy integracyjne dla połaczenia HTTP oraz happy path użytkownika korzystającego z endpointów.

---

## Architektura

- **Modularny monolit** – aplikacja podzielona na moduły ułatwiające rozwój i utrzymanie
- **Heksagonalna (Ports and Adapters)**

---

## Funkcjonalności

- Pobieranie listy aktualnych ofert pracy ze zdalnego API przez REST Template
- Dodawanie nowych ofert pracy za pomocą endpointa POST /offers
- Automatyczna aktualizacja ofert co 3 godziny

---

## Endpoints API

| Metoda | Endpoint           | Opis                         |
|--------|--------------------|------------------------------|
| GET    | `/offers`          | Pobierz listę wszystkich ofert pracy |
| POST   | `/offers`          | Dodaj nową ofertę pracy       |
| GET    | `/offers/{id}`     | Pobierz szczegóły oferty o podanym ID |

---

## Technologie

- Java
- Spring Boot
- MongoDB
- Spring Scheduler
- WireMock
- MockMvc
- AssertJ
- 
- 

---
