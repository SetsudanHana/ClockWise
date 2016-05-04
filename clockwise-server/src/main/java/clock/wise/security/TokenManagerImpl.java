package clock.wise.security;

import clock.wise.security.interfaces.TokenManager;
import clock.wise.security.model.Token;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TokenManagerImpl implements TokenManager {

    private static final long CLEAN_UP_TASK_INTERVAL = TimeUnit.MINUTES.toMillis(10);

    private Map<UserDetails, Token> mTokenMap = new HashMap<>();

    private final ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();

    public TokenManagerImpl() {
        Timer mCleanUpTimer = new Timer();
        mCleanUpTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mLock.writeLock().lock();
                try {
                    List<UserDetails> expired = new ArrayList<>();
                    for (Map.Entry<UserDetails, Token> entry : mTokenMap.entrySet()) {
                        if (entry.getValue().isExpired()) {
                            expired.add(entry.getKey());
                        }
                    }

                    for (UserDetails userDetails : expired) {
                        mTokenMap.remove(userDetails);
                    }
                } finally {
                    mLock.writeLock().unlock();
                }
            }
        }, 0, CLEAN_UP_TASK_INTERVAL);
    }

    @Override
    public Token getToken(UserDetails userDetails) {
        mLock.writeLock().lock();
        try {
            mTokenMap.remove(userDetails);
            Token token;
            do {
                token = new Token(UUID.randomUUID());
            } while (mTokenMap.containsValue(token));
            mTokenMap.put(userDetails, token);
            return token;
        } finally {
            mLock.writeLock().unlock();
        }
    }

    @Override
    public Token getToken(UserDetails userDetails, Long expiration) {
        mLock.writeLock().lock();
        try {
            mTokenMap.remove(userDetails);
            Token token;
            do {
                token = new Token(UUID.randomUUID(), expiration);
            } while (mTokenMap.containsValue(token));
            mTokenMap.put(userDetails, token);
            return token;
        } finally {
            mLock.writeLock().unlock();
        }
    }

    @Override
    public boolean validate(Token token) {
        mLock.readLock().lock();
        try {
            Map.Entry<UserDetails, Token> entry = findEntryByToken(token);
            return entry != null && !entry.getValue().isExpired();
        } finally {
            mLock.readLock().unlock();
        }
    }

    @Override
    public UserDetails getUserFromToken(Token token) {
        mLock.readLock().lock();
        try {
            return findUserByToken(token);
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

    @Override
    public void invalidateToken(Token token) {
        mLock.writeLock().lock();
        try {
            mTokenMap.remove(token);
        } finally {
            mLock.writeLock().unlock();
        }
    }

    private UserDetails findUserByToken(Token token) {
        Map.Entry<UserDetails, Token> entry = findEntryByToken(token);
        return entry == null ? null : entry.getKey();
    }

    private Map.Entry<UserDetails, Token> findEntryByToken(Token token) {
        for (Map.Entry<UserDetails, Token> entry : mTokenMap.entrySet()) {
            if (entry.getValue().equals(token)) {
                return entry;
            }
        }
        return null;
    }
}
