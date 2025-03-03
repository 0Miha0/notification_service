package faang.school.notificationservice.messaging;

import faang.school.notificationservice.event.PublishPostEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class PublishPostMessageBuilder implements MessageBuilder<PublishPostEvent>{

    private final MessageSource messageSource;

    @Override
    public Class<?> getInstance() {
        return PublishPostEvent.class;
    }

    @Override
    public String buildMessage(PublishPostEvent event, Locale locale) {
        return messageSource.getMessage(
                "publishPost.new",
                new Object[] { event.getUserId()},
                locale
        );
    }
}
