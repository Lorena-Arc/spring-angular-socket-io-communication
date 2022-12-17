package org.example;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CmdLineRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Override
    public void run(String... args) {
        server.start();
    }
}
