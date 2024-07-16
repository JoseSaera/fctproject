import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { MovieDTO } from '../../interfaces/dto';

@Component({
    selector: 'app-user-favorites',
    templateUrl: './user-favorites.component.html',
    styleUrl: './user-favorites.component.scss',
})
export class UserFavoritesComponent {
    favorites: number[] = [];
    favoriteMovies: MovieDTO[] = [];

    constructor(private userService: UserService) {}

    ngOnInit(): void {
        this.userService.favorites$.subscribe((favorites) => {
            this.favorites = favorites ?? [];
        });
        this.userService.favoriteMovies$.subscribe((favoritesMovies) => {
            this.favoriteMovies = favoritesMovies ?? [];
        });
    }

    toggleFavorite(movieId: number): void {
        this.userService.toggleFavorites(movieId);
    }
}
