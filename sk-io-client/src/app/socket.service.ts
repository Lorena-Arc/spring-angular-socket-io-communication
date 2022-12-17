import { Injectable } from "@angular/core";
import { Socket } from "ngx-socket-io";
import { v4 as uuidv4 } from 'uuid';

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  constructor(private socket: Socket) {
    // here we would add the token in case of authorization
    // the token will be verified in the handshake phase
    socket.ioSocket.io.opts.query = { token: 7};
    // socket.ioSocket.io.opts.query.sid = '';
    socket.connect();
  }

  receiveMessage() {
    return this.socket.fromEvent('message');
  }

  sendMessage(message: any) {
    this.socket.emit('message', message);
  }

  disconnect() {
    this.socket.disconnect();
  }
}
