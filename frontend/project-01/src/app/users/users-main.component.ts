import { Component, OnInit } from '@angular/core';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-users-main',
  templateUrl: './users-main.component.html',
  imports: [MatSlideToggleModule, RouterOutlet],
  styleUrls: ['./users-main.component.scss']
})
export class UserComponent implements OnInit {
  constructor() { }

  ngOnInit(): void { }
}
