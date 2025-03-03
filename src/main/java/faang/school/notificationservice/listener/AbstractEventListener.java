package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.MappingException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Async;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements RedisContainerMessageListener, MessageListener {

    private final ObjectMapper objectMapper;
    private final List<NotificationService> notificationServices;
    private final List<MessageBuilder<T>> messageBuilder;
    private final UserServiceClient userServiceClient;

    @Async("eventListenerTaskExecutor")
    public void processEvent(Message message, Class<T> eventType, Consumer<T> processingEvent) {
        try {
            T event = objectMapper.readValue(message.getBody(), eventType);
            processingEvent.accept(event);
        } catch (IOException e) {
            String exceptionMessage = String.format("Unable to parse event: %s, with message: %s",
                    eventType.getName(), message);
            MappingException mappingException = new MappingException(exceptionMessage, e);
            log.error(exceptionMessage, e);
            throw mappingException;
        }
    }

    public String getMessage(T event, Locale locale) {
        return messageBuilder.stream()
                .filter(messageBuilder -> messageBuilder.getInstance().equals(event.getClass()))
                .findFirst()
                .map(messageBuilder -> messageBuilder.buildMessage(event, locale))
                .orElseThrow(() -> {
                    String exceptionMessage = String.format("Message wasn't found for event type - %s",
                            event.getClass().getName());
                    NotFoundException e = new NotFoundException(exceptionMessage);
                    log.error(exceptionMessage, e);
                    return e;
                });
    }

    public void sendNotification(Long userId, String messageText) {
        UserDto user = userServiceClient.getUser(userId);
        notificationServices.stream()
                .filter(notificationService -> notificationService.getPreferredContact().equals(user.getPreference()))
                .findFirst()
                .ifPresentOrElse(
                        service -> service.send(user, messageText),
                        () -> {
                            String msg = String.format("Notification service not found for user %d with preference %s",
                                    userId, user.getPreference());
                            log.error(msg);
                            throw new NotFoundException(msg);
                        }
                );
        log.info("Notification service sent notification - {}. To user with id: {}", messageText, userId);
    }
}
