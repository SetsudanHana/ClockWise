package clock.wise.controller;

import clock.wise.dto.ScreenshotDto;
import clock.wise.model.User;
import clock.wise.service.ScreenshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screenshots")
public class ScreenshotController {

    @Autowired
    ScreenshotService screenshotService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ScreenshotDto createScreenshot(@RequestBody ScreenshotDto screenshotDto) {
        return screenshotService.create(screenshotDto, (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ScreenshotDto getScreenshot(@PathVariable("id") final Long id) {
        return screenshotService.findById(id);
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getScreenshotImage(@PathVariable("id") final Long id) {
        return screenshotService.getImageDataById(id);
    }

    @RequestMapping(value = "/all/{userId}", method = RequestMethod.GET)
    public List<ScreenshotDto> getAllScreenshotsForUser(@PathVariable("userId") final Long userId) {
        return screenshotService.findByUserId(userId);
    }
}
