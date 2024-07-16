import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieCardComponent } from './movie-card.component';
import { AppModule } from '../../app.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('MovieCardComponent', () => {
    let component: MovieCardComponent;
    let fixture: ComponentFixture<MovieCardComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AppModule],
        }).compileComponents();

        fixture = TestBed.createComponent(MovieCardComponent);
        component = fixture.componentInstance;
        component.movie = {
            adult: false,
            backdrop_path: 'example',
            genre_ids: [],
            id: 0,
            original_language: 'example',
            original_title: 'example',
            overview: 'example',
            popularity: 0,
            poster_path: 'example',
            release_date: 'example',
            title: 'example',
            video: false,
            vote_average: 0,
            vote_count: 0,
            poster_url: 'example',
            genresDTO: [],
        };
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
