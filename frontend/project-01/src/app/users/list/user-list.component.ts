import { LiveAnnouncer } from '@angular/cdk/a11y';
import { AsyncPipe, NgIf } from '@angular/common';
import { AfterViewInit, Component, inject, ViewChild } from '@angular/core';
import { MatSort, MatSortModule, Sort } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { User } from '../models/user.models';
import { UserService } from '../services/users.service';

@Component({
    selector: 'app-user-list',
    templateUrl: './user-list.component.html',
    imports: [MatTableModule, MatSortModule, AsyncPipe, NgIf],
    styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements AfterViewInit {

    private _liveAnnouncer = inject(LiveAnnouncer);

    private route = inject(ActivatedRoute);

    displayedColumns: string[] = ['id', 'firstName', 'lastName', 'actions'];

    dataSource!: Observable<MatTableDataSource<User>>

    @ViewChild(MatSort) sort!: MatSort;


    constructor(private router: Router, private userService: UserService) { }


    ngAfterViewInit(): void {
        this.dataSource = this.userService.getAllUser().pipe(map((values: User[]) => {
            const source = new MatTableDataSource<User>();
            source.data = values.map(u => ({ ...u, id: u.id }));
            source.sort = this.sort;
            return source;
        }))
    }



    announceSortChange(sortState: Sort) {
        if (sortState.direction) {
            this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
        } else {
            this._liveAnnouncer.announce('Sorting cleared');
        }
    }


    async createHandler(): Promise<void> {
        await this.router.navigate(['../add'], { relativeTo: this.route })
    }

    async editHandler(id: number): Promise<void> {
        this.router.navigate(['../update', id], { relativeTo: this.route })

    }
    async deleteHandler(id: number): Promise<void> {
        this.router.navigate(['../delete', id], { relativeTo: this.route })

    }
    async detailsHandler(id: number): Promise<void> {
        this.router.navigate(['../view', id], { relativeTo: this.route })

    }
}
