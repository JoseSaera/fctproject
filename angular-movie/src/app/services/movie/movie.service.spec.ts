import { TestBed } from '@angular/core/testing';

import { MovieService } from './movie.service';
import { HttpClient, HttpHandler } from '@angular/common/http';

describe('MovieService', () => {
    let service: MovieService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [HttpClient, HttpHandler],
        });
        service = TestBed.inject(MovieService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
