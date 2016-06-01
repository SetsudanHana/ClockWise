package clock.wise.converter;

import clock.wise.dto.ScreenshotDto;
import clock.wise.model.Screenshot;
import org.modelmapper.AbstractConverter;

import java.util.Base64;

public class DtoToScreenshotConverter extends AbstractConverter<ScreenshotDto, Screenshot> {

    @Override
    protected Screenshot convert(ScreenshotDto source) {
        Screenshot out = new Screenshot();
        out.setDate(source.getDate());
        out.setImage(Base64.getDecoder().decode(source.getBase64Data()));
        return out;
    }

}
