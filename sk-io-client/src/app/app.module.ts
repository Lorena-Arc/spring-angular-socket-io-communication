import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { SocketIoModule } from "ngx-socket-io";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    // autoConnect false so we can connect it later manually (e.g. when we have the auth bearer token)
    // if no authorization is needed, the options can be deleted and the connexion will be made automatically
    SocketIoModule.forRoot({url: 'http://localhost:8081', options: {autoConnect: false, transports: ['websocket']}}),
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
