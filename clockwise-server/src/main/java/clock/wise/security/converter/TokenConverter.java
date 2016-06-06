package clock.wise.security.converter;

import clock.wise.dto.TokenDto;
import clock.wise.security.model.Token;
import org.modelmapper.AbstractConverter;

public class TokenConverter extends AbstractConverter< Token, TokenDto > {
    @Override
    protected TokenDto convert( Token source ) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.token = source.getUuid().toString();
        return tokenDto;
    }
}
