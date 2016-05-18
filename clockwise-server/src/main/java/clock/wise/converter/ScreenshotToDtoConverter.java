package clock.wise.converter;

import clock.wise.dto.ScreenshotDto;
import clock.wise.model.Screenshot;
import org.modelmapper.AbstractConverter;

public class ScreenshotToDtoConverter extends AbstractConverter<Screenshot, ScreenshotDto> {

    @Override
    protected ScreenshotDto convert(Screenshot source) {
        ScreenshotDto out = new ScreenshotDto();
        out.setBase64Data("");
        out.setDate(source.getDate());
        out.setId(source.getId());
        out.setUserId(source.getUser().getId());
        return out;
    }

}
