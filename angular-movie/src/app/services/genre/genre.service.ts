import { Injectable } from '@angular/core';
import { environment } from '../../../environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GenreDTO } from '../../interfaces/dto';

@Injectable({
    providedIn: 'root',
})
export class GenreService {
    private genresUrl = environment.genresUrl;

    constructor(private http: HttpClient) {}

    getGenres(): Observable<GenreDTO[]> {
        return this.http.get<GenreDTO[]>(this.genresUrl);
    }
}
