import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MovieCountByYear } from '../../interfaces/dto';
import { environment } from '../../../environment';

@Injectable({
    providedIn: 'root',
})
export class ChartService {
    private moviesUrl = `${environment.apiUrl}/movies`;

    constructor(private http: HttpClient) {}

    getGenreRatingData(genreId: number): Observable<number[]> {
        const url: string = `${this.moviesUrl}/count/genreId-rating?id=${genreId}`;
        return this.http.get<number[]>(url);
    }

    getAverageRatingbyGenre(genreId: number): Observable<number> {
        const url = `${this.moviesUrl}/average/genreId?id=${genreId}`;
        return this.http.get<number>(url);
    }

    getGenreYearsData(genreId: number): Observable<MovieCountByYear[]> {
        const url: string = `${this.moviesUrl}/count/genreId-years?id=${genreId}`;
        return this.http.get<MovieCountByYear[]>(url);
    }
}
