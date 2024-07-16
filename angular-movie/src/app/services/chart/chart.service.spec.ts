import { TestBed } from '@angular/core/testing';

import { ChartService } from './chart.service';
import { HttpClient, HttpHandler } from '@angular/common/http';

describe('ChartService', () => {
    let service: ChartService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [HttpClient, HttpHandler],
        });
        service = TestBed.inject(ChartService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
