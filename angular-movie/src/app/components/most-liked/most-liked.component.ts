import { Component, OnInit } from '@angular/core';
import { Page } from '../../interfaces/page';
import { MovieDTO } from '../../interfaces/dto';
import { MovieService } from '../../services/movie/movie.service';

@Component({
    selector: 'app-most-liked',
    templateUrl: './most-liked.component.html',
    styleUrl: './most-liked.component.scss',
})
export class MostLikedComponent implements OnInit {
    moviePage: Page<MovieDTO> = {} as Page<MovieDTO>;

    constructor(private movieService: MovieService) {}

    ngOnInit(): void {
        this.movieService
            .searchMostLiked()
            .subscribe((page: Page<MovieDTO>) => {
                this.moviePage = page;
            });
    }
}
