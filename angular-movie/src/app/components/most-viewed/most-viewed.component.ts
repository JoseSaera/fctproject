import { Component, OnInit } from '@angular/core';
import { MovieDTO } from '../../interfaces/dto';
import { Page } from '../../interfaces/page';
import { MovieService } from '../../services/movie/movie.service';

@Component({
    selector: 'app-most-viewed',
    templateUrl: './most-viewed.component.html',
    styleUrl: './most-viewed.component.scss',
})
export class MostViewedComponent implements OnInit {
    moviePage: Page<MovieDTO> = {} as Page<MovieDTO>;

    constructor(private movieService: MovieService) {}

    ngOnInit(): void {
        this.movieService
            .searchMostViewed()
            .subscribe((page: Page<MovieDTO>) => {
                this.moviePage = page;
            });
    }
}
