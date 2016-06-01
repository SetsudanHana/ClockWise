package clock.wise.service;

import clock.wise.dto.ScreenshotDto;
import clock.wise.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScreenshotService {

    ScreenshotDto create(ScreenshotDto screenshotDto, User user);

    ScreenshotDto findById(Long id);

    byte[] getImageDataById(Long id);

    List<ScreenshotDto> findByUserId(Long userId);

    Page<ScreenshotDto> findPageByUserId(Long userId, Integer page, Integer pageLength);
}
