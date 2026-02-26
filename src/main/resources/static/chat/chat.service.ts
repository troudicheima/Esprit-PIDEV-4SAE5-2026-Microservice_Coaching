import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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
  
  // Replace with your backend URL
  private readonly WS_ENDPOINT = 'http://localhost:8088/ws/chat';
  private readonly TOPIC = '/topic/public';
  
  constructor(private http: HttpClient) {}

  /**
   * Connect to WebSocket server
   */
  connect(): void {
    const socket = new SockJS(this.WS_ENDPOINT);
    this.stompClient = Stomp.over(socket);
    
    this.stompClient.connect({}, (frame) => {
      console.log('Connected: ' + frame);
      this.connectedSubject.next(true);
      
      // Subscribe to public topic
      this.stompClient?.subscribe(this.TOPIC, (message) => {
        const chatMessage: ChatMessage = JSON.parse(message.body);
        this.messageSubject.next(chatMessage);
      });
    }, (error) => {
      console.error('Error connecting to WebSocket:', error);
      this.connectedSubject.next(false);
    });
  }

  /**
   * Disconnect from WebSocket server
   */
  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect(() => {
        console.log('Disconnected from WebSocket');
        this.connectedSubject.next(false);
      });
    }
  }

  /**
   * Send a chat message
   */
  sendMessage(sender: string, content: string): void {
    const message: ChatMessage = {
      sender: sender,
      content: content,
      type: 'CHAT',
      timestamp: new Date().toISOString()
    };
    
    this.stompClient?.send('/app/chat', {}, JSON.stringify(message));
  }

  /**
   * Send join message when user enters the chat
   */
  joinChat(username: string): void {
    const message: ChatMessage = {
      sender: username,
      content: `${username} a rejoint le chat`,
      type: 'JOIN',
      timestamp: new Date().toISOString()
    };
    
    this.stompClient?.send('/app/chat/addUser', {}, JSON.stringify(message));
  }

  /**
   * Send leave message when user leaves the chat
   */
  leaveChat(username: string): void {
    const message: ChatMessage = {
      sender: username,
      content: `${username} a quitté le chat`,
      type: 'LEAVE',
      timestamp: new Date().toISOString()
    };
    
    this.stompClient?.send('/app/chat/leave', {}, JSON.stringify(message));
  }

  /**
   * Get incoming messages as Observable
   */
  getMessages(): Observable<ChatMessage> {
    return this.messageSubject.asObservable();
  }

  /**
   * Get connection status as Observable
   */
  getConnectionStatus(): Observable<boolean> {
    return this.connectedSubject.asObservable();
  }

  /**
   * Check if connected to WebSocket
   */
  isConnected(): boolean {
    return this.stompClient?.connected || false;
  }
}
