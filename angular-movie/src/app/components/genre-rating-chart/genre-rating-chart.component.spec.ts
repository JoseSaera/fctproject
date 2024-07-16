import { ComponentFixture, TestBed } from '@angular/core/testing';
import {
    HttpClientTestingModule,
    HttpTestingController,
} from '@angular/common/http/testing';

import { GenreRatingChartComponent } from './genre-rating-chart.component';
import { AppModule } from '../../app.module';

describe('GenreRatingChartComponent', () => {
    let component: GenreRatingChartComponent;
    let fixture: ComponentFixture<GenreRatingChartComponent>;
    let httpTestingController: HttpTestingController;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AppModule, HttpClientTestingModule],
            teardown: { destroyAfterEach: false },
        }).compileComponents();

        fixture = TestBed.createComponent(GenreRatingChartComponent);
        component = fixture.componentInstance;
        httpTestingController = TestBed.inject(HttpTestingController);

        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('when create should retrieve existing genres', () => {
        const req = httpTestingController.expectOne(
            'http://localhost:8080/genres'
        );
        expect(req.request.method).toBe('GET');
        const mock = Array(5).fill({});
        req.flush(mock);
        expect(component.genres.length).toBeGreaterThan(0);
    });

    it('when select genre should retrieve data for this genre', () => {
        component.selectedGenre = { name: 'example', id: 0 };
        component.onSelectChange();
        const req = httpTestingController.expectOne(
            'http://localhost:8080/movies/count/genreId-rating?id=0'
        );
        expect(req.request.method).toBe('GET');
        const mock = { exampleData: 'exampleData' };
        req.flush(mock);
        expect(component.chartData).toBeDefined();
    });
});
