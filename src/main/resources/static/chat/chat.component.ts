import { Component, OnInit, OnDestroy } from '@angular/core';
import { ChatService, ChatMessage } from './chat.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="chat-container">
      <div class="chat-header">
        <h3>Chat en temps réel</h3>
        <span [class.connected]="isConnected" [class.disconnected]="!isConnected">
          {{ isConnected ? 'Connecté' : 'Déconnecté' }}
        </span>
      </div>
      
      <!-- Login section if not logged in -->
      <div *ngIf="!isLoggedIn" class="login-section">
        <input 
          [(ngModel)]="username" 
          placeholder="Entrez votre nom..."
          (keyup.enter)="joinChat()"
        />
        <button (click)="joinChat()">Rejoindre le chat</button>
      </div>
      
      <!-- Chat section -->
      <div *ngIf="isLoggedIn" class="chat-section">
        <div class="messages-container" #messagesContainer>
          <div 
            *ngFor="let msg of messages" 
            [class.my-message]="msg.sender === username"
            [class.join-leave]="msg.type !== 'CHAT'"
            class="message"
          >
            <div class="message-header">
              <strong>{{ msg.sender }}</strong>
              <span class="timestamp">{{ formatTime(msg.timestamp) }}</span>
            </div>
            <div class="message-content">{{ msg.content }}</div>
          </div>
        </div>
        
        <div class="input-section">
          <input 
            [(ngModel)]="messageContent" 
            placeholder="Tapez votre message..."
            (keyup.enter)="sendMessage()"
          />
          <button (click)="sendMessage()">Envoyer</button>
          <button (click)="leaveChat()" class="leave-btn">Quitter</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .chat-container {
      max-width: 600px;
      margin: 20px auto;
      border: 1px solid #ccc;
      border-radius: 8px;
      font-family: Arial, sans-serif;
    }
    
    .chat-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px;
      background-color: #4a90d9;
      color: white;
      border-radius: 8px 8px 0 0;
    }
    
    .connected { color: #90EE90; }
    .disconnected { color: #FFB6C1; }
    
    .login-section {
      padding: 20px;
      display: flex;
      gap: 10px;
    }
    
    .login-section input {
      flex: 1;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    
    .chat-section {
      display: flex;
      flex-direction: column;
      height: 400px;
    }
    
    .messages-container {
      flex: 1;
      overflow-y: auto;
      padding: 15px;
      background-color: #f9f9f9;
    }
    
    .message {
      margin-bottom: 15px;
      padding: 10px;
      border-radius: 8px;
      background-color: white;
      box-shadow: 0 1px 3px rgba(0,0,0,0.1);
    }
    
    .my-message {
      background-color: #e3f2fd;
      margin-left: 20%;
    }
    
    .join-leave {
      text-align: center;
      background-color: #f0f0f0;
      color: #666;
      font-style: italic;
    }
    
    .message-header {
      display: flex;
      justify-content: space-between;
      margin-bottom: 5px;
      font-size: 0.9em;
    }
    
    .timestamp {
      color: #999;
      font-size: 0.8em;
    }
    
    .input-section {
      display: flex;
      gap: 10px;
      padding: 15px;
      border-top: 1px solid #ddd;
    }
    
    .input-section input {
      flex: 1;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    
    button {
      padding: 10px 20px;
      background-color: #4a90d9;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    
    button:hover {
      background-color: #357abd;
    }
    
    .leave-btn {
      background-color: #d94a4a;
    }
    
    .leave-btn:hover {
      background-color: #bd3535;
    }
  `]
})
export class ChatComponent implements OnInit, OnDestroy {
  username = '';
  messageContent = '';
  messages: ChatMessage[] = [];
  isLoggedIn = false;
  isConnected = false;
  
  private messageSubscription?: Subscription;
  private connectionSubscription?: Subscription;
  
  constructor(private chatService: ChatService) {}
  
  ngOnInit(): void {
    // Subscribe to messages
    this.messageSubscription = this.chatService.getMessages().subscribe(
      (message) => {
        this.messages.push(message);
        this.scrollToBottom();
      }
    );
    
    // Subscribe to connection status
    this.connectionSubscription = this.chatService.getConnectionStatus().subscribe(
      (connected) => {
        this.isConnected = connected;
        if (connected && this.isLoggedIn) {
          this.chatService.joinChat(this.username);
        }
      }
    );
    
    // Connect to WebSocket
    this.chatService.connect();
  }
  
  ngOnDestroy(): void {
    this.messageSubscription?.unsubscribe();
    this.connectionSubscription?.unsubscribe();
    this.chatService.disconnect();
  }
  
  joinChat(): void {
    if (this.username.trim()) {
      this.isLoggedIn = true;
      this.chatService.joinChat(this.username);
    }
  }
  
  sendMessage(): void {
    if (this.messageContent.trim()) {
      this.chatService.sendMessage(this.username, this.messageContent);
      this.messageContent = '';
    }
  }
  
  leaveChat(): void {
    this.chatService.leaveChat(this.username);
    this.isLoggedIn = false;
    this.messages = [];
  }
  
  formatTime(timestamp: string): string {
    const date = new Date(timestamp);
    return date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
  }
  
  private scrollToBottom(): void {
    setTimeout(() => {
      const container = document.querySelector('.messages-container');
      if (container) {
        container.scrollTop = container.scrollHeight;
      }
    }, 100);
  }
}
