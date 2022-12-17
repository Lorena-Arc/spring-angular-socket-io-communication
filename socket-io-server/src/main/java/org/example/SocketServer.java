package org.example;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SocketServer {

    private final SocketIOServer socketIOServer;
    private final List<SocketIOClient> listeners = new ArrayList<>();


    @Autowired
    public SocketServer(SocketIOServer server) {
        log.info("created socket server");
        this.socketIOServer = server;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("message", Message.class, new Listener());
    }

    class Listener implements DataListener<Message> {
        @Override
        public void onData(SocketIOClient client, Message data, AckRequest ackRequest) throws Exception {
            log.info("Client[{}] - Received chat message '{}'", client.getSessionId().toString(), data);
            listeners.forEach( (SocketIOClient currentClient) -> {
                currentClient.sendEvent("message", data);
            });
        }
    }

    private ConnectListener onConnected() {
        return new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                listeners.add(client);
                HandshakeData handshakeData = client.getHandshakeData();
                log.info("Client[{}] - Connected to chat module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
            }
        };
    }

    private DisconnectListener onDisconnected() {
        return new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                listeners.remove(client);
                log.info("Client[{}] - Disconnected from chat module.", client.getSessionId().toString());

            }
        };
    }
}

@Data
class Message {
    String subject;
    String content;
}
