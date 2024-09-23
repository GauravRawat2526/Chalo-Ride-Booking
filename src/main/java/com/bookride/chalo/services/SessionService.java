package com.bookride.chalo.services;

import com.bookride.chalo.entities.Session;
import com.bookride.chalo.entities.User;
import com.bookride.chalo.exceptions.SessionLimitReachedException;
import com.bookride.chalo.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 1;

    public void generateNewSession(User user , String refreshToken){

        List<Session> sessionList = sessionRepository.findByUser(user);
        if (sessionList.size() == SESSION_LIMIT){
            throw new SessionLimitReachedException("You can't login as session limit is reached " + SESSION_LIMIT);
        }
        Session newSession = Session.builder().
                    user(user)
                    .refreshToken(refreshToken)
                    .build();
        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found for refreshToken " + refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public void deleteSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow( () -> new SessionAuthenticationException("Session not found for refreshToken " + refreshToken));
        sessionRepository.delete(session);
    }
}
