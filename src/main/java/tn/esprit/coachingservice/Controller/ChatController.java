package tn.esprit.coachingservice.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import tn.esprit.coachingservice.Model.ChatMessage;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    /**
     * Handle incoming chat messages and broadcast them to all subscribers
     * The message is sent to /app/chat and then broadcast to /topic/public
     */
    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public ChatMessage handleChatMessage(@Payload ChatMessage chatMessage) {
        log.info("Received chat message from: {}, content: {}", chatMessage.getSender(), chatMessage.getContent());
        
        // Set timestamp if not already set
        if (chatMessage.getTimestamp() == null) {
            chatMessage.setTimestamp(java.time.LocalDateTime.now());
        }
        
        return chatMessage;
    }

    /**
     * Handle new user joining the chat
     * Add user to session and broadcast join message
     */
    @MessageMapping("/chat/addUser")
    @SendTo("/topic/public")
    public ChatMessage handleAddUser(@Payload ChatMessage chatMessage, 
                                     SimpMessageHeaderAccessor headerAccessor) {
        log.info("User joining: {}", chatMessage.getSender());
        
        // Add username to session attributes
        if (headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        }
        
        return ChatMessage.createJoinMessage(chatMessage.getSender());
    }

    /**
     * Handle user leaving the chat
     */
    @MessageMapping("/chat/leave")
    @SendTo("/topic/public")
    public ChatMessage handleLeave(@Payload ChatMessage chatMessage) {
        log.info("User leaving: {}", chatMessage.getSender());
        return ChatMessage.createLeaveMessage(chatMessage.getSender());
    }
}
