import { AsyncPipe } from '@angular/common';
import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { mergeMap, Observable, Subscription } from 'rxjs';
import { User } from '../models/user.models';
import { UserService } from '../services/users.service';

@Component({
    selector: 'app-user-list',
    templateUrl: './user-delete.component.html',
    imports: [AsyncPipe],
    styleUrls: ['./user-delete.component.scss']
})
export class UserDeleteComponent implements OnInit, OnDestroy {
    private route: ActivatedRoute = inject(ActivatedRoute);

    private router: Router = inject(Router);

    private userService: UserService = inject(UserService);

    private id!: number;

    user!: Observable<User>;

    private subscription: Subscription = new Subscription();

    ngOnInit(): void {
        this.id = +this.route.snapshot.params['id'];

        this.user = this.userService.getUserById(this.id);
    }


    deleteHandler(): void {
        this.subscription.add(this.userService.deteleUser(this.id)
            .pipe(mergeMap((u) => this.router.navigate(['../../list'], { relativeTo: this.route }))).subscribe());
    }

    async cancelHandler(): Promise<void> {
        await this.router.navigate(['../../list'], { relativeTo: this.route })
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
