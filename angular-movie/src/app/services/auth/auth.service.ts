import { Injectable } from '@angular/core';
import { environment } from '../../../environment';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { AuthenticationResponse, User } from '../../interfaces/dto';

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private loginUrl = `${environment.apiUrl}/login`;
    private registerUrl = `${environment.apiUrl}/register`;

    private usernameSubject: BehaviorSubject<string | null>;
    public username$: Observable<string | null>;
    private isLoggedInSubject: BehaviorSubject<boolean>;
    public isLoggedIn$: Observable<boolean>;

    constructor(private http: HttpClient) {
        const currentUser = JSON.parse(localStorage.getItem('currentUser')!);
        const initialUsername = currentUser?.username ?? null;

        this.usernameSubject = new BehaviorSubject<string | null>(
            initialUsername
        );
        this.username$ = this.usernameSubject.asObservable();

        this.isLoggedInSubject = new BehaviorSubject<boolean>(!!currentUser);
        this.isLoggedIn$ = this.isLoggedInSubject.asObservable();
    }

    authenticate(
        username: string,
        password: string
    ): Observable<AuthenticationResponse> {
        return this.http
            .post<AuthenticationResponse>(`${this.loginUrl}`, {
                username,
                password,
            })
            .pipe(
                tap((response) => {
                    if (response && response.token) {
                        const user: User = {
                            id: response.id,
                            username: username,
                            token: response.token,
                            favorites: response.favorites,
                            seeLater: response.seeLater,
                        };
                        localStorage.setItem(
                            'currentUser',
                            JSON.stringify(user)
                        );
                        this.usernameSubject.next(username);
                        this.isLoggedInSubject.next(true);
                    }
                }),
                catchError(this.handleError)
            );
    }

    register(
        name: string,
        username: string,
        password: string
    ): Observable<any> {
        return this.http
            .post<any>(`${this.registerUrl}`, { name, username, password })
            .pipe(catchError(this.handleError));
    }

    logout(): void {
        localStorage.removeItem('currentUser');
        this.usernameSubject.next(null);
        this.isLoggedInSubject.next(false);
    }

    getToken(): string | null {
        const currentUser = JSON.parse(localStorage.getItem('currentUser')!);
        return currentUser?.token ?? null;
    }

    getCurrentUser(): User | null {
        return JSON.parse(localStorage.getItem('currentUser')!);
    }

    getCurrentUserId(): number | null {
        const currentUser = this.getCurrentUser();
        return currentUser ? currentUser.id : null;
    }

    private handleError(error: any): Observable<never> {
        console.error('An error occurred', error);
        return throwError(() => new Error(error));
    }
}
