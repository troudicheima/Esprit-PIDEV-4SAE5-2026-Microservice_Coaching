# Chat en temps réel avec WebSocket - Configuration Angular

## Prérequis

Dans votre projet Angular, installez les dépendances nécessaires :

```bash
npm install sockjs-client stompjs
npm install --save-dev @types/sockjs-client @types/stompjs
```

## Intégration du Chat

### 1. Copier les fichiers

Copiez les fichiers suivants dans votre projet Angular :
- `chat.service.ts` → `src/app/services/chat.service.ts`
- `chat.component.ts` → `src/app/components/chat/chat.component.ts`

### 2. Configurer le Service HttpClient

Assurez-vous d'importer `HttpClientModule` dans votre `app.module.ts` :

```typescript
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    // ... autres imports
  ],
  // ...
})
export class AppModule { }
```

### 3. Utiliser le composant Chat

Dans votre composant parent, importez et utilisez le ChatComponent :

```typescript
import { ChatComponent } from './components/chat/chat.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ChatComponent],
  template: `
    <app-chat></app-chat>
  `
})
export class HomeComponent { }
```

## Configuration du Backend

Le backend Spring Boot est configuré avec :
- **Endpoint WebSocket** : `http://localhost:8088/ws/chat`
- **Topic public** : `/topic/public`
- **Point d'entrée** : `/app/chat`

## Fonctionnalités

- ✅ Connexion automatique au démarrage
- ✅ Envoi de messages en temps réel
- ✅ Notification d'arrivée/départ des utilisateurs
- ✅ Affichage du statut de connexion
- ✅ Interface utilisateur responsive
- ✅ Support SockJS (fallback pour navigateurs anciens)

## Personnalisation

Pour modifier l'URL du serveur WebSocket, modifiez la constante `WS_ENDPOINT` dans `chat.service.ts` :

```typescript
private readonly WS_ENDPOINT = 'http://votre-serveur:port/ws/chat';
```
