package com.softwaremill.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Service
public class ServerDetailsService {

    private String host;
    private final int port;

    public ServerDetailsService(@Value("${server.port}") int port) {
        this.port = port;
        assignHostAddress();
    }

    private void assignHostAddress() {
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("Cannot recognize application host.");
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printAddress() {
        log.info("Battleship application is running on: {}:{}", host, port);
    }

    public String getApplicationAddress() {
        return host + ":" + port;
    }
}
