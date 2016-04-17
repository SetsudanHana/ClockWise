package clock.wise.security;

import clock.wise.security.interfaces.TokenManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TokenManagerImpl implements TokenManager {

    private Map<UserDetails, UUID> mTokenMap = new HashMap<UserDetails, UUID>();

    private final ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();

    @Override
    public String getToken(UserDetails userDetails) {
        mLock.writeLock().lock();
        try {
            mTokenMap.remove(userDetails);
            UUID token;
            do {
                token = UUID.randomUUID();
            } while (mTokenMap.containsValue(token));
            mTokenMap.put(userDetails, token);
            return token.toString();
        } finally {
            mLock.writeLock().unlock();
        }
    }

    @Override
    public String getToken(UserDetails userDetails, Long expiration) {
        //TODO not supported yet
        return null;
    }

    @Override
    public boolean validate(String token) {
        mLock.readLock().lock();
        try {
            UUID parsedToken = UUID.fromString(token);//TODO handle token being invalid
            return mTokenMap.containsValue(parsedToken);
        } finally {
            mLock.readLock().unlock();
        }
    }

    @Override
    public UserDetails getUserFromToken(String token) {
        mLock.readLock().lock();
        try {
            return findUserByToken(UUID.fromString(token));
        } finally {
            mLock.readLock().unlock();
        }
    }

    @Override
    public void invalidateToken(UserDetails userDetails) {
        mLock.writeLock().lock();
        try {
            mTokenMap.remove(userDetails);
        } finally {
            mLock.writeLock().unlock();
        }
    }

    private UserDetails findUserByToken(UUID token) {
        for (Map.Entry<UserDetails, UUID> entry : mTokenMap.entrySet()) {
            if (entry.getValue().equals(token)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
