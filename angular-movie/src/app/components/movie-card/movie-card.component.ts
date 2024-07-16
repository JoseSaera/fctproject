import { Component, Input } from '@angular/core';
import { MovieDTO } from '../../interfaces/dto';
import { environment } from '../../../environment';
import { AuthService } from '../../services/auth/auth.service';
import { Observable } from 'rxjs';
import { UserService } from '../../services/user/user.service';

@Component({
    selector: 'app-movie-card',
    templateUrl: './movie-card.component.html',
    styleUrl: './movie-card.component.scss',
})
export class MovieCardComponent {
    @Input() movie!: MovieDTO;
    private notImageUrl = environment.notImageUrl;
    isLoggedIn$: Observable<boolean>;
    favorites: number[] = [];
    seeLater: number[] = [];

    constructor(
        private authService: AuthService,
        private userService: UserService
    ) {
        this.isLoggedIn$ = this.authService.isLoggedIn$;
    }

    ngOnInit(): void {
        this.isLoggedIn$.subscribe((isLoggedIn) => {
            if (isLoggedIn) {
                this.userService.favorites$.subscribe((favorites) => {
                    this.favorites = favorites ?? [];
                });
                this.userService.seeLater$.subscribe((seeLater) => {
                    this.seeLater = seeLater ?? [];
                });
            }
        });
    }

    getReleaseYear(): string {
        return this.movie.release_date.substring(0, 4);
    }

    getImagePath(): string {
        if (this.movie.poster_path === null) {
            this.movie.poster_url = this.notImageUrl;
        }
        return this.movie.poster_url;
    }

    formatNumber(num: number) {
        return num.toLocaleString('en-US', {
            minimumFractionDigits: 1,
            maximumFractionDigits: 1,
        });
    }

    getColor() {
        if (this.movie.vote_average > 9) {
            return 'hsl(300 100% 50%)';
        } else if (this.movie.vote_average > 8) {
            return 'hsl(120 100% 50%)';
        } else if (this.movie.vote_average > 6) {
            return 'hsl(60 100% 60%)';
        } else if (this.movie.vote_average > 5) {
            return 'hsl(30 100% 60%)';
        } else {
            return 'hsl(0 80% 50%)';
        }
    }

    toggleFavorites(movieId: number): void {
        this.userService.toggleFavorites(movieId);
    }

    isFavorite(movieId: number): boolean {
        return this.favorites.includes(movieId);
    }

    getFavoriteIconClass(movieId: number): string {
        return this.isFavorite(movieId)
            ? 'pi pi-heart-fill icon-heart'
            : 'pi pi-heart icon-heart';
    }

    toggleSeeLater(movieId: number): void {
        this.userService.toggleSeeLater(movieId);
    }

    isSeeLater(movieId: number): boolean {
        return this.seeLater.includes(movieId);
    }

    getSeeLaterIconClass(movieId: number): string {
        return this.isSeeLater(movieId)
            ? 'pi pi-eye-slash icon-eye'
            : 'pi pi-eye icon-eye';
    }
}
