package clock.wise.service.impl;

import clock.wise.dao.ScreenshotDao;
import clock.wise.dto.ScreenshotDto;
import clock.wise.mapper.ScreenshotModelMapperWrapper;
import clock.wise.model.Screenshot;
import clock.wise.model.User;
import clock.wise.service.ScreenshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScreenshotServiceImpl implements ScreenshotService {

    private static final String SORTING_PARAM = "date";

    @Autowired
    ScreenshotDao screenshotDao;

    @Autowired
    ScreenshotModelMapperWrapper modelMapperWrapper;

    @Override
    @Transactional
    public ScreenshotDto create(ScreenshotDto screenshotDto, User user) {
        Screenshot screenshot = modelMapperWrapper.getModelMapper().map(screenshotDto, Screenshot.class);
        screenshot.setUser(user);
        return modelMapperWrapper.getModelMapper().map(screenshotDao.save(screenshot), ScreenshotDto.class);
    }

    @Override
    public ScreenshotDto findById(Long id) {
        return modelMapperWrapper.getModelMapper().map(getScreenshotById(id), ScreenshotDto.class);
    }

    @Override
    public byte[] getImageDataById(Long id) {
        return getScreenshotById(id).getImage();
    }

    @Override
    public byte[] getThumbnailDataById(Long id) {
        return getScreenshotById(id).getThumbnail();
    }

    @Override
    @Transactional
    public List<ScreenshotDto> findByUserId(Long userId) {
        List<ScreenshotDto> screenshotDtos = new ArrayList<>();
        for (Screenshot screenshot : screenshotDao.findByUserId(userId, new Sort(Sort.Direction.ASC, SORTING_PARAM))) {
            screenshotDtos.add(modelMapperWrapper.getModelMapper().map(screenshot, ScreenshotDto.class));
        }
        return screenshotDtos;
    }

    @Override
    @Transactional
    public Page<ScreenshotDto> findPageByUserId(Long userId, Integer page, Integer size) {
        if (page == null || size == null) {
            throw new IllegalArgumentException("Page and size can not be null");
        }

        if (page < 0 || size < 0) {
            throw new IllegalArgumentException("Page and size must be not negative numbers");
        }

        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, SORTING_PARAM);
        Page<Screenshot> out = screenshotDao.findByUserId(userId, pageable);
        return out.map(source -> modelMapperWrapper.getModelMapper().map(source, ScreenshotDto.class));
    }

    @Override
    @Transactional
    public List<ScreenshotDto> findByUserIdBetween(Long userId, Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start date and end date can not be null");
        }

        if (end.before(start)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        List<ScreenshotDto> screenshotDtos = new ArrayList<>();
        for (Screenshot screenshot : screenshotDao.findByUserIdAndDateBetween(userId, start, end, new Sort(Sort.Direction.ASC, SORTING_PARAM))) {
            screenshotDtos.add(modelMapperWrapper.getModelMapper().map(screenshot, ScreenshotDto.class));
        }
        return screenshotDtos;
    }

    @Override
    @Transactional
    public Page<ScreenshotDto> findPageByUserIdBetween(Long userId, Date start, Date end, Integer page, Integer size) {
        if (page == null || size == null) {
            throw new IllegalArgumentException("Page and size can not be null");
        }

        if (page < 0 || size < 0) {
            throw new IllegalArgumentException("Page and size must be not negative numbers");
        }

        if (start == null || end == null) {
            throw new IllegalArgumentException("Start date and end date can not be null");
        }

        if (end.before(start)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, SORTING_PARAM);
        Page<Screenshot> out = screenshotDao.findByUserIdAndDateBetween(userId, start, end, pageable);
        return out.map(source -> modelMapperWrapper.getModelMapper().map(source, ScreenshotDto.class));
    }

    @Transactional
    private Screenshot getScreenshotById(Long id) {
        Screenshot screenshot = screenshotDao.findOne(id);
        if (screenshot == null) {
            throw new EntityNotFoundException("Screenshot with id: " + id + " not found");
        }
        return screenshot;
    }
}
