import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchComponent } from './search.component';
import { AppModule } from '../../app.module';
import { MovieService } from '../../services/movie/movie.service';
import { FormsModule } from '@angular/forms';
import {
    HttpClientTestingModule,
    HttpTestingController,
} from '@angular/common/http/testing';

describe('SearchComponent', () => {
    let component: SearchComponent;
    let fixture: ComponentFixture<SearchComponent>;
    let httpTestingController: HttpTestingController;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AppModule, FormsModule, HttpClientTestingModule],
            providers: [],
        }).compileComponents();

        fixture = TestBed.createComponent(SearchComponent);
        component = fixture.componentInstance;
        httpTestingController = TestBed.inject(HttpTestingController);

        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('when search bar changes should retrieve 10 movies', () => {
        const event = { target: { value: 'star' } } as unknown as Event;
        component.onSearch(event);

        const req = httpTestingController.expectOne(
            'http://localhost:8080/movies/search/title?title=star&skip=0'
        );
        expect(req.request.method).toBe('GET');

        const mock = Array(10).fill({});
        const mockPage = {
            content: mock,
            totalPages: 1,
            totalElements: 10,
            size: 10,
            number: 0,
        };
        req.flush(mockPage);

        expect(component.movies.length).toBe(10);
    });

    it('when press show more (and exist more result) should retrieve more movies', () => {
        component.currentPage = 0;
        component.searchTitle = 'star';
        component.movies = Array(10).fill({});
        component.showMore();

        const req = httpTestingController.expectOne(
            'http://localhost:8080/movies/search/title?title=star&skip=1'
        );
        expect(req.request.method).toBe('GET');
        const mock = Array(5).fill({});
        const mockPage = {
            content: mock,
            totalPages: 2,
            totalElements: 15,
            size: 10,
            number: 1,
        };
        req.flush(mockPage);

        expect(component.movies.length).toBeGreaterThan(10);
    });
});
