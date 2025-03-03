package faang.school.notificationservice.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementEvent {

    @NotNull
    private String title;

    @NotNull
    private Long userId;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime createdAt;
}
