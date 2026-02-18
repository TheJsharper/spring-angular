import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { UserService } from '../services/users.service';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { User } from '../models/user.models';
import { ActivatedRoute, Router } from '@angular/router';
import { mergeMap, Subscription } from 'rxjs';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
    selector: 'app-user-list',
    templateUrl: './user-add.component.html',
    imports: [MatFormFieldModule, MatInputModule, MatSelectModule, ReactiveFormsModule],
    styleUrls: ['./user-add.component.scss']
})
export class UserAddComponent implements OnInit, OnDestroy {

    private route = inject(ActivatedRoute);

    private router: Router = inject(Router);

    private userService: UserService = inject(UserService);

    form!: FormGroup;

    private subscriptions: Subscription = new Subscription();


    ngOnInit(): void {
        this.form = new FormGroup({
            firstName: new FormControl<string>(''),
            lastName: new FormControl<String>('')
        })
    }

    submit(): void {
        if (this.form) {

            const payload: Omit<User, 'id'> = this.form.value;

            this.subscriptions.add(this.userService.createUser(payload)
                .pipe(mergeMap((value: User) => this.router.navigate(['../list'], { relativeTo: this.route })))

                .subscribe((value) => console.log("save",)));
        }
    }

    ngOnDestroy(): void {
        this.subscriptions.unsubscribe();
    }

}
