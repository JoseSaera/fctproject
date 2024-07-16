import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { MovieDTO } from '../../interfaces/dto';

@Component({
    selector: 'app-user-see-later',
    templateUrl: './user-see-later.component.html',
    styleUrl: './user-see-later.component.scss',
})
export class UserSeeLaterComponent {
    seeLater: number[] = [];
    seeLaterMovies: MovieDTO[] = [];

    constructor(private userService: UserService) {}

    ngOnInit(): void {
        this.userService.seeLater$.subscribe((seeLater) => {
            this.seeLater = seeLater ?? [];
        });
        this.userService.seeLaterMovies$.subscribe((seeLaterMovies) => {
            this.seeLaterMovies = seeLaterMovies ?? [];
        });
    }

    toggleSeeLater(movieId: number): void {
        this.userService.toggleSeeLater(movieId);
    }
}
