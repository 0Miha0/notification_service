package faang.school.notificationservice.service.telegram;

import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class TelegramService implements NotificationService {

    @Override
    public void send(UserDto user, String message) {

    }

    @Override
    public UserDto.PreferredContact getPreferredContact() {
        return null;
    }
}
