import { Component } from '@angular/core';
import { MovieService } from '../../services/movie/movie.service';
import { MovieDTO } from '../../interfaces/dto';
import { Page } from '../../interfaces/page';
import { Observable, Subject, debounceTime, distinctUntilChanged } from 'rxjs';

@Component({
    selector: 'app-searchbox',
    templateUrl: './search.component.html',
    styleUrl: './search.component.scss',
})
export class SearchComponent {
    public searchTitle$ = new Subject<string>();
    public showMore$ = new Subject<void>();

    searchTitle: string = '';

    movies: MovieDTO[] = [];
    firstPage: number = 0;
    currentPage!: number;

    isButtonVisible: boolean = false;

    searchPlaceholder: string = 'Search...';
    currentSearchPlaceholder: string;

    constructor(private movieService: MovieService) {
        this.currentSearchPlaceholder = this.searchPlaceholder;
    }

    ngOnInit(): void {}

    onSearch(event: Event) {
        this.currentPage = this.firstPage;
        const target = event.target as HTMLInputElement;
        this.movieService
            .searchMoviesByTitle(target.value, this.currentPage)
            .subscribe((response) => {
                this.movies = [];
                this.movies = response.content;
                this.isButtonVisible =
                    this.currentPage < response.totalPages &&
                    target.value != '';
            });
    }

    showMore() {
        this.currentPage += 1;
        this.movieService
            .searchMoviesByTitle(this.searchTitle, this.currentPage)
            .subscribe((response) => {
                this.movies = this.movies.concat(response.content);
                this.isButtonVisible = this.currentPage < response.totalPages;
            });
    }

    searchByTitle(title: string, limit: number): Observable<Page<MovieDTO>> {
        return this.movieService.searchMoviesByTitle(title, limit);
    }

    onSearchFocus(): void {
        this.currentSearchPlaceholder = '';
    }

    onSearchBlur(): void {
        this.currentSearchPlaceholder = this.searchPlaceholder;
    }
}
