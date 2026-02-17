# Coaching Service - API Documentation

## Base URL

```
http://localhost:5057/Coaching-service/api
```

---

## 📚 Models

### Seance

| Field        | Type       | Format       | Description              |
|-------------|------------|--------------|--------------------------|
| `id`        | `integer`  | —            | Auto-generated ID        |
| `goodName`  | `string`   | —            | Nom de la séance         |
| `seanceDate`| `string`   | `YYYY-MM-DD` | Date de la séance        |
| `seanceTime`| `string`   | `HH:mm:ss`   | Heure de la séance       |
| `reservations` | `array` | `Reservation[]` | Liste des réservations |

### Reservation

| Field         | Type       | Format       | Description                    |
|--------------|------------|--------------|--------------------------------|
| `id`         | `integer`  | —            | Auto-generated ID              |
| `studidname` | `string`   | —            | Nom / identifiant de l'étudiant|
| `merenumber` | `string`   | `YYYY-MM-DD` | Date de la réservation         |
| `status`     | `string`   | —            | Statut de la réservation       |
| `seance`     | `object`   | `Seance`     | Séance associée                |

---

## 🏋️ Seance Endpoints

### 1. Create a Seance

```
POST /api/seances
```

**Request Body:**
```json
{
  "goodName": "Coaching Angular",
  "seanceDate": "2026-03-15",
  "seanceTime": "10:00:00"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "goodName": "Coaching Angular",
  "seanceDate": "2026-03-15",
  "seanceTime": "10:00:00",
  "reservations": []
}
```

**Frontend (Angular/Axios) Example:**
```typescript
// Angular HttpClient
this.http.post<Seance>(`${BASE_URL}/seances`, {
  goodName: "Coaching Angular",
  seanceDate: "2026-03-15",
  seanceTime: "10:00:00"
}).subscribe(seance => console.log(seance));

// Axios
axios.post(`${BASE_URL}/seances`, {
  goodName: "Coaching Angular",
  seanceDate: "2026-03-15",
  seanceTime: "10:00:00"
});
```

---

### 2. Get All Seances

```
GET /api/seances
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "goodName": "Coaching Angular",
    "seanceDate": "2026-03-15",
    "seanceTime": "10:00:00",
    "reservations": []
  }
]
```

**Frontend Example:**
```typescript
// Angular
this.http.get<Seance[]>(`${BASE_URL}/seances`).subscribe(data => this.seances = data);

// Axios
const { data } = await axios.get(`${BASE_URL}/seances`);
```

---

### 3. Get Seance by ID

```
GET /api/seances/{id}
```

| Parameter | Type      | Location | Description     |
|-----------|-----------|----------|-----------------|
| `id`      | `integer` | path     | ID de la séance |

**Response:** `200 OK` or `404 Not Found`
```json
{
  "id": 1,
  "goodName": "Coaching Angular",
  "seanceDate": "2026-03-15",
  "seanceTime": "10:00:00",
  "reservations": []
}
```

**Frontend Example:**
```typescript
// Angular
this.http.get<Seance>(`${BASE_URL}/seances/${id}`).subscribe(seance => this.seance = seance);

// Axios
const { data } = await axios.get(`${BASE_URL}/seances/${id}`);
```

---

### 4. Update a Seance

```
PUT /api/seances/{id}
```

| Parameter | Type      | Location | Description     |
|-----------|-----------|----------|-----------------|
| `id`      | `integer` | path     | ID de la séance |

**Request Body:**
```json
{
  "goodName": "Coaching React",
  "seanceDate": "2026-04-01",
  "seanceTime": "14:00:00"
}
```

**Response:** `200 OK` or `404 Not Found`

**Frontend Example:**
```typescript
// Angular
this.http.put<Seance>(`${BASE_URL}/seances/${id}`, updatedSeance).subscribe();

// Axios
await axios.put(`${BASE_URL}/seances/${id}`, updatedSeance);
```

---

### 5. Delete a Seance

```
DELETE /api/seances/{id}
```

| Parameter | Type      | Location | Description     |
|-----------|-----------|----------|-----------------|
| `id`      | `integer` | path     | ID de la séance |

**Response:** `204 No Content`

**Frontend Example:**
```typescript
// Angular
this.http.delete(`${BASE_URL}/seances/${id}`).subscribe();

// Axios
await axios.delete(`${BASE_URL}/seances/${id}`);
```

---

## 📅 Reservation Endpoints

### 1. Create a Reservation (for a Seance)

```
POST /api/seances/{seanceId}/reservations
```

| Parameter   | Type      | Location | Description     |
|-------------|-----------|----------|-----------------|
| `seanceId`  | `integer` | path     | ID de la séance |

**Request Body:**
```json
{
  "studidname": "Ahmed Ben Ali",
  "merenumber": "2026-03-10",
  "status": "CONFIRMED"
}
```

**Response:** `201 Created` or `404 Not Found` (if seance doesn't exist)
```json
{
  "id": 1,
  "studidname": "Ahmed Ben Ali",
  "merenumber": "2026-03-10",
  "status": "CONFIRMED",
  "seance": {
    "id": 1,
    "goodName": "Coaching Angular",
    "seanceDate": "2026-03-15",
    "seanceTime": "10:00:00"
  }
}
```

**Frontend Example:**
```typescript
// Angular
this.http.post<Reservation>(`${BASE_URL}/seances/${seanceId}/reservations`, {
  studidname: "Ahmed Ben Ali",
  merenumber: "2026-03-10",
  status: "CONFIRMED"
}).subscribe(res => console.log(res));

// Axios
await axios.post(`${BASE_URL}/seances/${seanceId}/reservations`, {
  studidname: "Ahmed Ben Ali",
  merenumber: "2026-03-10",
  status: "CONFIRMED"
});
```

---

### 2. Get All Reservations

```
GET /api/reservations
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "studidname": "Ahmed Ben Ali",
    "merenumber": "2026-03-10",
    "status": "CONFIRMED",
    "seance": { ... }
  }
]
```

**Frontend Example:**
```typescript
// Angular
this.http.get<Reservation[]>(`${BASE_URL}/reservations`).subscribe(data => this.reservations = data);

// Axios
const { data } = await axios.get(`${BASE_URL}/reservations`);
```

---

### 3. Get Reservation by ID

```
GET /api/reservations/{id}
```

| Parameter | Type      | Location | Description          |
|-----------|-----------|----------|----------------------|
| `id`      | `integer` | path     | ID de la réservation |

**Response:** `200 OK` or `404 Not Found`

**Frontend Example:**
```typescript
// Angular
this.http.get<Reservation>(`${BASE_URL}/reservations/${id}`).subscribe();

// Axios
const { data } = await axios.get(`${BASE_URL}/reservations/${id}`);
```

---

### 4. Get Reservations by Seance

```
GET /api/seances/{seanceId}/reservations
```

| Parameter   | Type      | Location | Description     |
|-------------|-----------|----------|-----------------|
| `seanceId`  | `integer` | path     | ID de la séance |

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "studidname": "Ahmed Ben Ali",
    "merenumber": "2026-03-10",
    "status": "CONFIRMED",
    "seance": { ... }
  }
]
```

**Frontend Example:**
```typescript
// Angular
this.http.get<Reservation[]>(`${BASE_URL}/seances/${seanceId}/reservations`).subscribe();

// Axios
const { data } = await axios.get(`${BASE_URL}/seances/${seanceId}/reservations`);
```

---

### 5. Update a Reservation

```
PUT /api/reservations/{id}
```

| Parameter | Type      | Location | Description          |
|-----------|-----------|----------|----------------------|
| `id`      | `integer` | path     | ID de la réservation |

**Request Body:**
```json
{
  "studidname": "Ahmed Ben Ali",
  "merenumber": "2026-03-12",
  "status": "CANCELLED"
}
```

**Response:** `200 OK` or `404 Not Found`

**Frontend Example:**
```typescript
// Angular
this.http.put<Reservation>(`${BASE_URL}/reservations/${id}`, updatedReservation).subscribe();

// Axios
await axios.put(`${BASE_URL}/reservations/${id}`, updatedReservation);
```

---

### 6. Delete a Reservation

```
DELETE /api/reservations/{id}
```

| Parameter | Type      | Location | Description          |
|-----------|-----------|----------|----------------------|
| `id`      | `integer` | path     | ID de la réservation |

**Response:** `204 No Content`

**Frontend Example:**
```typescript
// Angular
this.http.delete(`${BASE_URL}/reservations/${id}`).subscribe();

// Axios
await axios.delete(`${BASE_URL}/reservations/${id}`);
```

---

## 🔧 Frontend Setup

### TypeScript Interfaces

```typescript
export interface Seance {
  id?: number;
  goodName: string;
  seanceDate: string;   // "YYYY-MM-DD"
  seanceTime: string;   // "HH:mm:ss"
  reservations?: Reservation[];
}

export interface Reservation {
  id?: number;
  studidname: string;
  merenumber: string;   // "YYYY-MM-DD"
  status: string;
  seance?: Seance;
}
```

### Angular Service Example

```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:5057/Coaching-service/api';

@Injectable({ providedIn: 'root' })
export class CoachingService {

  constructor(private http: HttpClient) {}

  // ---- Seances ----
  getAllSeances(): Observable<Seance[]> {
    return this.http.get<Seance[]>(`${BASE_URL}/seances`);
  }

  getSeanceById(id: number): Observable<Seance> {
    return this.http.get<Seance>(`${BASE_URL}/seances/${id}`);
  }

  createSeance(seance: Seance): Observable<Seance> {
    return this.http.post<Seance>(`${BASE_URL}/seances`, seance);
  }

  updateSeance(id: number, seance: Seance): Observable<Seance> {
    return this.http.put<Seance>(`${BASE_URL}/seances/${id}`, seance);
  }

  deleteSeance(id: number): Observable<void> {
    return this.http.delete<void>(`${BASE_URL}/seances/${id}`);
  }

  // ---- Reservations ----
  getAllReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${BASE_URL}/reservations`);
  }

  getReservationById(id: number): Observable<Reservation> {
    return this.http.get<Reservation>(`${BASE_URL}/reservations/${id}`);
  }

  getReservationsBySeance(seanceId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${BASE_URL}/seances/${seanceId}/reservations`);
  }

  createReservation(seanceId: number, reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(`${BASE_URL}/seances/${seanceId}/reservations`, reservation);
  }

  updateReservation(id: number, reservation: Reservation): Observable<Reservation> {
    return this.http.put<Reservation>(`${BASE_URL}/reservations/${id}`, reservation);
  }

  deleteReservation(id: number): Observable<void> {
    return this.http.delete<void>(`${BASE_URL}/reservations/${id}`);
  }
}
```

### React/Axios Service Example

```typescript
import axios from 'axios';

const BASE_URL = 'http://localhost:5057/Coaching-service/api';

export const CoachingAPI = {
  // ---- Seances ----
  getAllSeances: () => axios.get(`${BASE_URL}/seances`),
  getSeanceById: (id: number) => axios.get(`${BASE_URL}/seances/${id}`),
  createSeance: (seance: Seance) => axios.post(`${BASE_URL}/seances`, seance),
  updateSeance: (id: number, seance: Seance) => axios.put(`${BASE_URL}/seances/${id}`, seance),
  deleteSeance: (id: number) => axios.delete(`${BASE_URL}/seances/${id}`),

  // ---- Reservations ----
  getAllReservations: () => axios.get(`${BASE_URL}/reservations`),
  getReservationById: (id: number) => axios.get(`${BASE_URL}/reservations/${id}`),
  getReservationsBySeance: (seanceId: number) => axios.get(`${BASE_URL}/seances/${seanceId}/reservations`),
  createReservation: (seanceId: number, reservation: Reservation) =>
    axios.post(`${BASE_URL}/seances/${seanceId}/reservations`, reservation),
  updateReservation: (id: number, reservation: Reservation) =>
    axios.put(`${BASE_URL}/reservations/${id}`, reservation),
  deleteReservation: (id: number) => axios.delete(`${BASE_URL}/reservations/${id}`),
};
```

---

## ⚠️ CORS Note

If the frontend runs on a different port (e.g., `localhost:4200` for Angular), you need to enable CORS on the backend. Add `@CrossOrigin(origins = "http://localhost:4200")` on each controller or create a global CORS config.

---

## 📋 Endpoints Summary

| Method   | URL                                        | Description                        |
|----------|--------------------------------------------|------------------------------------|
| `POST`   | `/api/seances`                             | Create a seance                    |
| `GET`    | `/api/seances`                             | Get all seances                    |
| `GET`    | `/api/seances/{id}`                        | Get seance by ID                   |
| `PUT`    | `/api/seances/{id}`                        | Update a seance                    |
| `DELETE` | `/api/seances/{id}`                        | Delete a seance                    |
| `POST`   | `/api/seances/{seanceId}/reservations`     | Create reservation for a seance    |
| `GET`    | `/api/reservations`                        | Get all reservations               |
| `GET`    | `/api/reservations/{id}`                   | Get reservation by ID              |
| `GET`    | `/api/seances/{seanceId}/reservations`     | Get reservations by seance         |
| `PUT`    | `/api/reservations/{id}`                   | Update a reservation               |
| `DELETE` | `/api/reservations/{id}`                   | Delete a reservation               |
