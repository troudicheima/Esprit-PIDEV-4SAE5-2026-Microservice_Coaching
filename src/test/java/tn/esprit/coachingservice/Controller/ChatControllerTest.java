package tn.esprit.coachingservice.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import tn.esprit.coachingservice.Model.ChatMessage;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatControllerTest {

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleChatMessage() {
        ChatMessage message = ChatMessage.builder()
                .sender("Alice")
                .content("Hello")
                .build();

        ChatMessage result = chatController.handleChatMessage(message);

        assertNotNull(result);
        assertEquals("Alice", result.getSender());
        assertEquals("Hello", result.getContent());
        assertNotNull(result.getTimestamp());
    }

    @Test
    void handleAddUser() {
        ChatMessage message = ChatMessage.builder()
                .sender("Alice")
                .build();

        SimpMessageHeaderAccessor headerAccessor = mock(SimpMessageHeaderAccessor.class);
        Map<String, Object> sessionAttributes = new HashMap<>();
        when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        ChatMessage result = chatController.handleAddUser(message, headerAccessor);

        assertNotNull(result);
        assertEquals("Alice", result.getSender());
        assertEquals(ChatMessage.MessageType.JOIN, result.getType());
        assertEquals("Alice", sessionAttributes.get("username"));
    }

    @Test
    void handleLeave() {
        ChatMessage message = ChatMessage.builder()
                .sender("Alice")
                .build();

        ChatMessage result = chatController.handleLeave(message);

        assertNotNull(result);
        assertEquals("Alice", result.getSender());
        assertEquals(ChatMessage.MessageType.LEAVE, result.getType());
    }
}
