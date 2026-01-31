import { Component } from "@angular/core";
import { MatCheckboxModule } from "@angular/material/checkbox";
import {MatExpansionModule} from '@angular/material/expansion';

@Component({
    selector: "app-settings",
    templateUrl: "./settings.component.html",
    styleUrls: ["./settings.component.scss"],
    imports: [MatExpansionModule, MatCheckboxModule],
})
export class SettingsComponent {
    title = "Account Settings";
}