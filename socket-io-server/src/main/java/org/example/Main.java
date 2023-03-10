package org.example;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    private final String host = "localhost";
    private final Integer port = 8081;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setHttpCompression(false);
        config.setWebsocketCompression(false);
        return new SocketIOServer(config);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
