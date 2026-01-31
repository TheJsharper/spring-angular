import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { Component } from "@angular/core";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
    imports: [MatCardModule, MatButtonModule]
})
export class HomeComponent {
    title = 'web-spa';
}