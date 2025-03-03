package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.event.FollowerEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class FollowerEventListener extends AbstractEventListener<FollowerEvent>{

    @Value("${spring.data.redis.channel.follower}")
    private String channel;

    public FollowerEventListener(
            ObjectMapper objectMapper,
            List<NotificationService> notificationServices,
            List<MessageBuilder<FollowerEvent>> messageBuilder,
            UserServiceClient userServiceClient) {
        super(objectMapper, notificationServices, messageBuilder, userServiceClient);
    }

    @Override
    public ChannelTopic getChannelTopic() {
        return new ChannelTopic(channel);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        processEvent(message, FollowerEvent.class, event -> {
            String messageText = getMessage(event, Locale.ENGLISH);
            sendNotification(event.getId(), messageText);
        });
    }
}
