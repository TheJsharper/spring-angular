import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user.models';

@Injectable()
export class UserService {

    private static readonly host: string = "http://localhost:8080";

    constructor(private httpClient: HttpClient) {

    }


    public getAllUser(): Observable<User[]> {
        return this.httpClient.get<User[]>(`${UserService.host}/`)
    }

    public getUserById(id: number): Observable<User> {
        return this.httpClient.get<User>(`${UserService.host}/`+id)
    }

    public createUser(user: Omit<User, 'id'>): Observable<User> {
        console.log("Service", user);
        return this.httpClient.post<User>(`${UserService.host}/`, user)
    }

    public updateUser(user: User, id: number): Observable<User> {
        return this.httpClient.put<User>(`${UserService.host}/${id}`, user)
    }

    public deteleUser(id: number): Observable<User> {
        return this.httpClient.delete<User>(`${UserService.host}/`+ id);
    }

}