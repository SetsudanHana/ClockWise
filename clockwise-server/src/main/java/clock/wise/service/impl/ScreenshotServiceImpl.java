package clock.wise.service.impl;

import clock.wise.dao.ScreenshotDao;
import clock.wise.dto.ScreenshotDto;
import clock.wise.mapper.ScreenshotModelMapperWrapper;
import clock.wise.model.Screenshot;
import clock.wise.model.User;
import clock.wise.service.ScreenshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScreenshotServiceImpl implements ScreenshotService {

    @Autowired
    ScreenshotDao screenshotDao;

    @Autowired
    ScreenshotModelMapperWrapper modelMapperWrapper;

    @Override
    public ScreenshotDto create(ScreenshotDto screenshotDto, User user) {
        Screenshot screenshot = modelMapperWrapper.getModelMapper().map(screenshotDto, Screenshot.class);
        screenshot.setUser(user);
        return modelMapperWrapper.getModelMapper().map(screenshotDao.save(screenshot), ScreenshotDto.class);
    }

    @Override
    public ScreenshotDto findById(Long id) {
        return modelMapperWrapper.getModelMapper().map(screenshotDao.findOne(id), ScreenshotDto.class);
    }

    @Override
    public byte[] getImageDataById(Long id) {
        return screenshotDao.findOne(id).getImage();
    }

    @Override
    public List<ScreenshotDto> findByUserId(Long userId) {
        List<ScreenshotDto> screenshotDtos = new ArrayList<>();
        for (Screenshot screenshot : screenshotDao.findByUserId(userId)) {
            screenshotDtos.add(modelMapperWrapper.getModelMapper().map(screenshot, ScreenshotDto.class));
        }
        return screenshotDtos;
    }
}
