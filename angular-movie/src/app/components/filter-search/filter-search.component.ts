import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../services/movie/movie.service';
import { GenreService } from '../../services/genre/genre.service';
import { MovieDTO, YearRangeDTO } from '../../interfaces/dto';
import { Subject } from 'rxjs';
import { Genre } from '../../interfaces/genre.model';

@Component({
    selector: 'app-filter-search',
    templateUrl: './filter-search.component.html',
    styleUrl: './filter-search.component.scss',
})
export class FilterSearchComponent implements OnInit {
    searchText: string = '';
    genres!: Genre[];
    yearRange: YearRangeDTO = { minYear: 0, maxYear: 0 };

    selectedGenres: number[] = [];
    selectedMinYear: number = 0;
    selectedMaxYear: number = 0;

    dataLoaded: boolean = false;

    private searchTextSubject: Subject<string> = new Subject<string>();
    movies: MovieDTO[] = [];

    searchPlaceholder: string = 'Search...';
    currentSearchPlaceholder: string;

    constructor(
        private movieService: MovieService,
        private genreService: GenreService
    ) {
        this.currentSearchPlaceholder = this.searchPlaceholder;
    }

    ngOnInit(): void {
        this.genreService.getGenres().subscribe((genres) => {
            this.genres = genres.map((genre) => ({
                ...genre,
                isSelected: true,
            }));
        });

        this.movieService.getYearRange().subscribe((yearRange) => {
            this.yearRange = yearRange;
            this.selectedMinYear = yearRange.minYear;
            this.selectedMaxYear = yearRange.maxYear;
        });
    }
    onSearchInput(): void {
        this.searchTextSubject.next(this.searchText);
        this.search();
    }

    onFilterChange(filters: any): void {
        this.selectedGenres = filters.selectedGenres;
        this.selectedMinYear = filters.selectedMinYear;
        this.selectedMaxYear = filters.selectedMaxYear;
        this.search();
    }

    search(): void {
        this.movieService
            .searchMoviesByFilter(
                this.searchText,
                this.selectedGenres,
                this.selectedMinYear,
                this.selectedMaxYear
            )
            .subscribe((response) => (this.movies = response.content));
    }

    onSearchFocus(): void {
        this.currentSearchPlaceholder = '';
    }

    onSearchBlur(): void {
        this.currentSearchPlaceholder = this.searchPlaceholder;
    }
}
