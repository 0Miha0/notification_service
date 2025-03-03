package faang.school.notificationservice.messaging;

import faang.school.notificationservice.event.LikeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LikeMessageBuilder implements MessageBuilder<LikeEvent>{

    private final MessageSource messageSource;

    @Override
    public Class<?> getInstance() {
        return LikeEvent.class;
    }

    @Override
    public String buildMessage(LikeEvent event, Locale locale) {
        return messageSource.getMessage(
                "like.new",
                new Object[]{event.getReceiverAt()},
                locale
        );
    }
}
