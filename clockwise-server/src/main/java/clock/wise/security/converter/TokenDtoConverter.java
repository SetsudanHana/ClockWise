package clock.wise.security.converter;

import clock.wise.dto.TokenDto;
import clock.wise.security.model.Token;
import org.modelmapper.AbstractConverter;

import java.util.UUID;

public class TokenDtoConverter extends AbstractConverter<TokenDto, Token> {
    @Override
    protected Token convert(TokenDto source) {
        return new Token(UUID.fromString(source.token));
    }
}
