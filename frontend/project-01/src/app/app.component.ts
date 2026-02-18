import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
 imports: [RouterOutlet],
  styleUrl: './app.component.scss'
})
export class AppComponent {
 
  constructor(private client:HttpClient){
    this.getAll();
  }

  getAll():any{
     this.client.get("http://localhost:8080/").subscribe((values)=> {
    console.log("===>", values)      
    })
    return ""
  }
}
