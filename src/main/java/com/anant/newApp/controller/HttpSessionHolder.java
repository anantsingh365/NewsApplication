package com.anant.newApp.controller;

import java.util.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.anant.newApp.Model.NewsCardModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HttpSessionHolder {

    private static final Map<String, HttpSession> sessions = new HashMap<>();
    private static int numOfSessions = 0;

    public List<HttpSession> getActiveSessions() {
        return new ArrayList<>(sessions.values());
    }

    public void getSessions(){
        Set<Map.Entry<String, HttpSession>> set = sessions.entrySet();

    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent hse) {
                sessions.put(hse.getSession().getId(), hse.getSession());
                ++numOfSessions;
                System.out.println("A Session is Created, total num of sessions " + numOfSessions);
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent hse) {
                sessions.remove(hse.getSession().getId());
                --numOfSessions;
                System.out.println("A session is destroyed, total num of sessions, " + numOfSessions);
            }
        };
    }
}
