package clock.wise.security.model;

import java.util.Calendar;
import java.util.UUID;

public class Token {

    private static final long NO_EXPIRE = -1;

    private UUID uuid;
    private long timestamp = NO_EXPIRE;

    private long expirationTime = 0;

    public Token() {
    }

    public Token(UUID uuid) {
        this.uuid = uuid;
    }

    public Token(UUID uuid, long expiration) {
        this.uuid = uuid;
        this.expirationTime = expiration;
        this.timestamp = Calendar.getInstance().getTimeInMillis() + expirationTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void reinvokeToken() {
        if (this.timestamp != NO_EXPIRE) {
            this.timestamp = Calendar.getInstance().getTimeInMillis() + expirationTime;
        }
    }

    public boolean isExpired() {
        try {
            return timestamp != NO_EXPIRE && timestamp < Calendar.getInstance().getTimeInMillis();
        } finally {
            reinvokeToken();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Token) {
            Token other = (Token) obj;
            return uuid.equals(other.uuid);
        }
        return false;
    }
}
