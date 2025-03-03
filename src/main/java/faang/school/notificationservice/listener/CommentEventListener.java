package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.event.CommentEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.List;
import java.util.Locale;

public class CommentEventListener extends AbstractEventListener<CommentEvent>{

    @Value("${spring.data.redis.channel.comment}")
    private String channel;

    public CommentEventListener(ObjectMapper objectMapper, List<NotificationService> notificationServices, List<MessageBuilder<CommentEvent>> messageBuilder, UserServiceClient userServiceClient) {
        super(objectMapper, notificationServices, messageBuilder, userServiceClient);
    }

    @Override
    public ChannelTopic getChannelTopic() {
        return new ChannelTopic(channel);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        processEvent(message, CommentEvent.class, event -> {
            String messageText = getMessage(event, Locale.ENGLISH);
            sendNotification(event.getActorId(), messageText);
        });
    }
}
