# PROMPT POUR LE DÉVELOPPEUR FRONTEND

## Tâche : Intégrer un chat temps réel avec WebSocket

---

### 1. INSTALLATION DES DÉPENDANCES

Dans votre projet Angular, exécutez :

```bash
npm install sockjs-client stompjs
npm install --save-dev @types/sockjs-client @types/stompjs
```

---

### 2. CRÉER LE SERVICE CHAT

Créez le fichier `src/app/services/chat.service.ts` :

```typescript
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';

export interface ChatMessage {
  sender: string;
  content: string;
  type: 'CHAT' | 'JOIN' | 'LEAVE';
  timestamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: Stomp.Client | null = null;
  private messageSubject = new Subject<ChatMessage>();
  private connectedSubject = new Subject<boolean>();
  
  // URL du backend WebSocket - ADAPTER SELON VOTRE CONFIGURATION
  private readonly WS_ENDPOINT = 'http://localhost:8088/ws/chat';
  private readonly TOPIC = '/topic/public';

  connect(): void {
    const socket = new SockJS(this.WS_ENDPOINT);
    this.stompClient = Stomp.over(socket);
    
    this.stompClient.connect({}, (frame) => {
      console.log('Connecté:', frame);
      this.connectedSubject.next(true);
      
      this.stompClient?.subscribe(this.TOPIC, (message) => {
        const chatMessage: ChatMessage = JSON.parse(message.body);
        this.messageSubject.next(chatMessage);
      });
    }, (error) => {
      console.error('Erreur connexion:', error);
      this.connectedSubject.next(false);
    });
  }

  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }

  sendMessage(sender: string, content: string): void {
    const message: ChatMessage = {
      sender: sender,
      content: content,
      type: 'CHAT',
      timestamp: new Date().toISOString()
    };
    this.stompClient?.send('/app/chat', {}, JSON.stringify(message));
  }

  joinChat(username: string): void {
    const message: ChatMessage = {
      sender: username,
      content: `${username} a rejoint le chat`,
      type: 'JOIN',
      timestamp: new Date().toISOString()
    };
    this.stompClient?.send('/app/chat/addUser', {}, JSON.stringify(message));
  }

  getMessages(): Observable<ChatMessage> {
    return this.messageSubject.asObservable();
  }

  getConnectionStatus(): Observable<boolean> {
    return this.connectedSubject.asObservable();
  }
}
```

---

### 3. CRÉER LE COMPOSANT CHAT

Créez `src/app/components/chat/chat.component.ts` avec le template inline fourni.

---

### 4. IMPORTS NÉCESSAIRES

Dans votre `app.module.ts` ou composant standalone :

```typescript
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
```

---

### 5. INFORMATIONS TECHNIQUES POUR TESTS

**Backend (Spring Boot) :**
- Port : `8088`
- Endpoint WebSocket : `/ws/chat`
- Topic STOMP : `/topic/public`
- Message API : `/app/chat`

**Tester :**
1. Lancer le backend Spring Boot
2. Lancer Angular
3. Ouvrir 2 navigateurs différents
4. Dans les deux, saisir un pseudo différent
5. Envoyer des messages - ils devraient apparaître en temps réel dans les deux navigateurs

---

### 6. NOTES IMPORTANTES

- Pas de gestion d'utilisateurs connectés - juste un pseudonyme
- Le protocole STOMP over SockJS est utilisé
- Les messages sont diffusés à tous les clients connectés
