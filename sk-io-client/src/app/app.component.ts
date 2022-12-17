import { Component, OnDestroy, OnInit } from '@angular/core';
import { SocketService } from "./socket.service";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { Socket } from "ngx-socket-io";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'socket-io-client';

  form: FormGroup;

  list: Message[] = [];

  socketService: SocketService;

  constructor(private socket: Socket, private fb: FormBuilder) {
  }


  ngOnInit(): void {
    this.form = this.fb.group({
      subject: '',
      content: '',
    });
    this.socketService = new SocketService(this.socket);
    this.socketService.receiveMessage().subscribe((message) => {
      this.list.push(message as Message);
    })
  }


  send() {
    this.socketService.sendMessage({...this.form.getRawValue()});
  }

  ngOnDestroy() {
    this.socketService.disconnect();
  }
}


interface Message {
  subject: string;
  content: string;
}
