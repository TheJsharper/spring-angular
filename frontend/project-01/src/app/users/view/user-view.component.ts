import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { UserService } from '../services/users.service';
import { Observable, Subscription } from 'rxjs';
import { User } from '../models/user.models';
import { AsyncPipe, NgIf } from '@angular/common';

@Component({
    selector: 'app-user-list',
    templateUrl: './user-view.component.html',
    imports: [NgIf, AsyncPipe],
    styleUrls: ['./user-view.component.scss']
})
export class UserViewComponent implements OnInit, OnDestroy {

    private route: ActivatedRoute = inject(ActivatedRoute);

    private router: Router = inject(Router);

    private userService: UserService = inject(UserService);

    private subscription: Subscription = new Subscription();

    user!: Observable<User>;


    private id!: number;

    ngOnInit(): void {
        this.id = +this.route.snapshot.params['id'];

        this.user = this.userService.getUserById(this.id);
    }

    async backHandler(): Promise<void> {
        await this.router.navigate(['../../list'], { relativeTo: this.route })
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
