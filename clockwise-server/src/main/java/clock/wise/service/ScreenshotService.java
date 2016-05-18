package clock.wise.service;

import clock.wise.dto.ScreenshotDto;
import clock.wise.model.User;

import java.util.List;

public interface ScreenshotService {

    ScreenshotDto create(ScreenshotDto screenshotDto, User user);

    ScreenshotDto findById(Long id);

    byte[] getImageDataById(Long id);

    List<ScreenshotDto> findByUserId(Long userId);

}
