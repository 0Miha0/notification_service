package faang.school.notificationservice.messaging;

import faang.school.notificationservice.event.FollowerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class FollowerMessageBuilder implements MessageBuilder<FollowerEvent>{

    private final MessageSource messageSource;

    @Override
    public Class<?> getInstance() {
        return FollowerEvent.class;
    }

    @Override
    public String buildMessage(FollowerEvent event, Locale locale) {
        return messageSource.getMessage(
                "follower.new",
                new Object[]{event.getId()},
                locale
        );
    }
}
