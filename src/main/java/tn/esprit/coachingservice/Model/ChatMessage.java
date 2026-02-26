package tn.esprit.coachingservice.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    
    private String sender;
    private String content;
    private MessageType type;
    private LocalDateTime timestamp;
    
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    
    // Helper method to create a chat message
    public static ChatMessage createChatMessage(String sender, String content) {
        return ChatMessage.builder()
                .sender(sender)
                .content(content)
                .type(MessageType.CHAT)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    // Helper method to create a join message
    public static ChatMessage createJoinMessage(String sender) {
        return ChatMessage.builder()
                .sender(sender)
                .content(sender + " a rejoint le chat")
                .type(MessageType.JOIN)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    // Helper method to create a leave message
    public static ChatMessage createLeaveMessage(String sender) {
        return ChatMessage.builder()
                .sender(sender)
                .content(sender + " a quitté le chat")
                .type(MessageType.LEAVE)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
