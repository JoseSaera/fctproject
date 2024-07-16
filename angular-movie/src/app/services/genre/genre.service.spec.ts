import { TestBed } from '@angular/core/testing';

import { GenreService } from './genre.service';
import { HttpClient, HttpHandler } from '@angular/common/http';

describe('GenreService', () => {
    let service: GenreService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [HttpClient, HttpHandler],
        });
        service = TestBed.inject(GenreService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
