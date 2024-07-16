import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MovieDTO, YearRangeDTO } from '../../interfaces/dto';
import { Page } from '../../interfaces/page';
import { environment } from '../../../environment';

@Injectable({
    providedIn: 'root',
})
export class MovieService {
    private moviesUrl = environment.moviesUrl;

    constructor(private http: HttpClient) {}

    getMovies(): Observable<Page<MovieDTO>> {
        return this.http.get<Page<MovieDTO>>(this.moviesUrl);
    }

    getYearRange(): Observable<YearRangeDTO> {
        const url = `${this.moviesUrl}/year-range`;
        return this.http.get<YearRangeDTO>(url);
    }

    searchMoviesByTitle(
        title: string,
        skip: number
    ): Observable<Page<MovieDTO>> {
        const url = `${this.moviesUrl}/search/title?title=${title}&skip=${skip}`;
        return this.http.get<Page<MovieDTO>>(url);
    }

    searchMoviesByFilter(
        title: string,
        genreIds: number[],
        minYear: number,
        maxYear: number
    ): Observable<Page<MovieDTO>> {
        const url = `${this.moviesUrl}/search/filters?title=${title}&genreIds=${genreIds}&minYear=${minYear}&maxYear=${maxYear}`;
        return this.http.get<Page<MovieDTO>>(url);
    }

    searchMostViewed(): Observable<Page<MovieDTO>> {
        const url = `${this.moviesUrl}/popularity/top`;
        return this.http.get<Page<MovieDTO>>(url);
    }

    searchMostLiked(): Observable<Page<MovieDTO>> {
        const url = `${this.moviesUrl}/rating/top`;
        return this.http.get<Page<MovieDTO>>(url);
    }
}
