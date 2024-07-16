import { Injectable } from '@angular/core';
import { environment } from '../../../environment';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { MovieDTO } from '../../interfaces/dto';

@Injectable({
    providedIn: 'root',
})
export class UserService {
    private userUrl = `${environment.apiUrl}/user`;

    private favoritesSubject: BehaviorSubject<number[]> = new BehaviorSubject<
        number[]
    >([]);
    public favorites$ = this.favoritesSubject.asObservable();

    private favoriteMoviesSubject: BehaviorSubject<MovieDTO[]> =
        new BehaviorSubject<MovieDTO[]>([]);
    public favoriteMovies$ = this.favoriteMoviesSubject.asObservable();

    private seeLaterSubject: BehaviorSubject<number[]> = new BehaviorSubject<
        number[]
    >([]);
    public seeLater$ = this.seeLaterSubject.asObservable();

    private seeLaterMoviesSubject: BehaviorSubject<MovieDTO[]> =
        new BehaviorSubject<MovieDTO[]>([]);
    public seeLaterMovies$ = this.seeLaterMoviesSubject.asObservable();

    constructor(private http: HttpClient, private authService: AuthService) {
        const currentUser = this.authService.getCurrentUser();
        if (currentUser) {
            this.getFavorites(currentUser.id).subscribe((favorites) => {
                this.favoritesSubject.next(favorites);
            });
            this.getFavoritesMovies(currentUser.id).subscribe(
                (favoritesMovies) => {
                    this.favoriteMoviesSubject.next(favoritesMovies);
                }
            );
            this.getSeeLater(currentUser.id).subscribe((seeLater) => {
                this.seeLaterSubject.next(seeLater);
            });
        }
    }

    getFavorites(userId: number): Observable<number[]> {
        return this.http
            .get<number[]>(`${this.userUrl}/${userId}/favorites`)
            .pipe(
                tap((favorites) => {
                    this.favoritesSubject.next(favorites);
                }),
                catchError(this.handleError)
            );
    }

    getFavoritesMovies(userId: number): Observable<MovieDTO[]> {
        return this.http
            .get<MovieDTO[]>(`${this.userUrl}/${userId}/favorites-movies`)
            .pipe(
                tap((favoriteMovies) => {
                    this.favoriteMoviesSubject.next(favoriteMovies);
                }),
                catchError(this.handleError)
            );
    }

    getSeeLater(userId: number): Observable<number[]> {
        return this.http
            .get<number[]>(`${this.userUrl}/${userId}/see-later`)
            .pipe(
                tap((seeLater) => {
                    this.seeLaterSubject.next(seeLater);
                }),
                catchError(this.handleError)
            );
    }

    getSeeLaterMovies(userId: number): Observable<MovieDTO[]> {
        return this.http.get<MovieDTO[]>(
            `${this.userUrl}/${userId}/see-later-movies`
        );
    }

    toggleFavorites(movieId: number): void {
        const userId = this.authService.getCurrentUserId();
        if (!userId) return;

        const currentFavorites = this.favoritesSubject.value;
        if (currentFavorites.includes(movieId)) {
            this.removeFromFavorites(userId, movieId).subscribe(() => {
                const updatedFavorites = currentFavorites.filter(
                    (id) => id !== movieId
                );
                this.favoritesSubject.next(updatedFavorites);
                this.updateFavoritesMovies(userId);
            });
        } else {
            this.addToFavorites(userId, movieId).subscribe(() => {
                const updatedFavorites = [...currentFavorites, movieId];
                this.favoritesSubject.next(updatedFavorites);
                this.updateFavoritesMovies(userId);
            });
        }
    }

    toggleSeeLater(movieId: number): void {
        const userId = this.authService.getCurrentUserId();
        if (!userId) return;

        const currentSeeLater = this.seeLaterSubject.value;
        if (currentSeeLater.includes(movieId)) {
            this.removeFromSeeLater(userId, movieId).subscribe(() => {
                const updatedSeeLater = currentSeeLater.filter(
                    (id) => id !== movieId
                );
                this.seeLaterSubject.next(updatedSeeLater);
                this.updateSeeLaterMovies(userId);
            });
        } else {
            this.addToSeeLater(userId, movieId).subscribe(() => {
                const updatedSeeLater = [...currentSeeLater, movieId];
                this.seeLaterSubject.next(updatedSeeLater);
                this.updateSeeLaterMovies(userId);
            });
        }
    }

    private updateFavoritesMovies(userId: number): void {
        this.getFavoritesMovies(userId).subscribe((favoritesMovies) => {
            this.favoriteMoviesSubject.next(favoritesMovies);
        });
    }

    private updateSeeLaterMovies(userId: number): void {
        this.getSeeLaterMovies(userId).subscribe((seeLaterMovies) => {
            this.seeLaterMoviesSubject.next(seeLaterMovies);
        });
    }

    addToFavorites(userId: number, movieId: number): Observable<void> {
        return this.http
            .post<void>(`${this.userUrl}/${userId}/favorites/${movieId}`, {})
            .pipe(catchError(this.handleError));
    }

    removeFromFavorites(userId: number, movieId: number): Observable<void> {
        return this.http
            .delete<void>(`${this.userUrl}/${userId}/favorites/${movieId}`)
            .pipe(catchError(this.handleError));
    }

    addToSeeLater(userId: number, movieId: number): Observable<void> {
        return this.http
            .post<void>(`${this.userUrl}/${userId}/see-later/${movieId}`, {})
            .pipe(catchError(this.handleError));
    }

    removeFromSeeLater(userId: number, movieId: number): Observable<void> {
        return this.http
            .delete<void>(`${this.userUrl}/${userId}/see-later/${movieId}`)
            .pipe(catchError(this.handleError));
    }

    private handleError(error: any): Observable<never> {
        return throwError(() => new Error(error));
    }
}
