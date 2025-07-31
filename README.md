# 💼 JobOffersAPI

Aplikacja REST API do zarządzania ofertami pracy dla junior Java developerów dostępnymi po uprzednim zalogowaniu.

---

## 📝 Opis

**JobOffersAPI** to aplikacja służąca do pobierania ofert pracy dla junior Java developerów.
Dane są przechowywane w bazie **MongoDB** i automatycznie aktualizowane co 3 godziny za pomocą wbudowanego **schedulera**.
Spring Security i generacja tokena JWT umożliwia uwierzytelnienie użytkownika.

Projekt zawiera:
- Testy jednostkowe dla warstwy domenowej
- Testy integracyjne sprawdzające komunikację HTTP oraz typowe scenariusze użytkownika (happy path)

---

## 🧱 Architektura

- **Modularny monolit** – logiczny podział na moduły ułatwia rozwój i utrzymanie
- **Heksagonalna architektura (Ports and Adapters)** – wyraźny podział na warstwy domeny, aplikacji i infrastruktury

---

## ✅ Funkcjonalności

- Rejestracja
- Logowanie (uzyskiwanie tokena JWT)

Dostępne przy autoryzacji użytkownika tokenem JWT:

- Pobieranie listy aktualnych ofert ze zdalnego API (REST Template)
- Dodawanie ofert pracy przez endpoint `POST /offers`
- Automatyczna aktualizacja ofert co 3 godziny

---

## 📡 API Endpointy

### 🔓 Publiczne:

| Metoda | Endpoint    | Opis                              |
|--------|-------------|-----------------------------------|
| POST   | `/register` | Rejestracja nowego użytkownika    |
| POST   | `/token`    | Uzyskanie tokena JWT do logowania |

#### 📦 Wymagane body (JSON) dla 
`POST /register` oraz `POST /token`:

```json
{
  "username": "user",
  "password": "tajnehaslo"
}
```

### 🔐 Endpointy wymagające JWT (Bearer Token):

| Metoda | Endpoint       | Opis                                  |
| ------ | -------------- | ------------------------------------- |
| GET    | `/offers`      | Pobierz listę wszystkich ofert pracy  |
| POST   | `/offers`      | Dodaj nową ofertę pracy               |
| GET    | `/offers/{id}` | Pobierz szczegóły oferty o podanym ID |

📦 Przykładowe wymagane body (JSON) dla `POST /offers`
```json
{
  "companyName": "X",
  "position": "Junior Java Dev",
  "salary": "8 000 - 10 000 PLN",
  "offerUrl": "https://offers.pl/offer/5"
}
```

### 🛠️ Technologie
Java 17+

Spring Boot

MongoDB

Spring Security + JWT

Spring Scheduler

WireMock (mockowanie zewnętrznych API)

MockMvc (testy kontrolerów)

AssertJ (asercje w testach)

