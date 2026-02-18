import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../services/users.service';
import { mergeMap, Subscription } from 'rxjs';
import { User } from '../models/user.models';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
    selector: 'app-user-list',
    templateUrl: './user-update.component.html',
    imports: [MatFormFieldModule, MatInputModule, MatSelectModule, ReactiveFormsModule],
    styleUrls: ['./user-update.component.scss']
})
export class UserUpdateComponent implements OnInit, OnDestroy {

    private fb: FormBuilder = inject(FormBuilder);

    private route: ActivatedRoute = inject(ActivatedRoute);

    private router: Router = inject(Router);

    private userService: UserService = inject(UserService);

    public form!: FormGroup;

    private subscription: Subscription = new Subscription();

    constructor() { }

    ngOnInit(): void {
        const id: number = Number(this.route.snapshot.params['id']);

        this.form = this.fb.group({
            id: new FormControl<number>(id),
            firstName: new FormControl<string>(''),
            lastName: new FormControl<string>('')
        });
        this.subscription.add(this.userService.getUserById(id).subscribe((value: User) => {
            console.log("USER UI ", value)
            this.form?.setValue({ ...value }, { emitEvent: true, }

            )
        }));
    }

    submit(): void {
        if (this.form) {

            const payload = this.form.value;
            this.subscription.add(this.userService.updateUser(payload, payload.id)
                .pipe(mergeMap((value: User) => this.router.navigate(['../../list'], { relativeTo: this.route })))
                .subscribe((value) => console.log("Savae", value)));

        }
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
