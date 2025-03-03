package faang.school.notificationservice.messaging;

import faang.school.notificationservice.event.ProfileViewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class PostViewMessageBuilder implements MessageBuilder<ProfileViewEvent>{

    private final MessageSource messageSource;

    @Override
    public Class<?> getInstance() {
        return ProfileViewEvent.class;
    }

    @Override
    public String buildMessage(ProfileViewEvent event, Locale locale) {
        return messageSource.getMessage(
                "profileView.new",
                new Object[]{event.getActorId()},
                locale
        );
    }
}
