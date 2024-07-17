import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GenreDTO } from '../../interfaces/dto';
import { environment } from '../../../environment';

@Injectable({
    providedIn: 'root',
})
export class GenreService {
    private genresUrl = `${environment.apiUrl}/genres`;

    constructor(private http: HttpClient) {}

    getGenres(): Observable<GenreDTO[]> {
        return this.http.get<GenreDTO[]>(this.genresUrl);
    }
}
