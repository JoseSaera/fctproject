import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { GenreYearChartComponent } from './genre-year-chart.component';
import { AppModule } from '../../app.module';

describe('GenreYearChartComponent', () => {
    let component: GenreYearChartComponent;
    let fixture: ComponentFixture<GenreYearChartComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AppModule, HttpClientTestingModule],
            teardown: { destroyAfterEach: false },
        }).compileComponents();

        fixture = TestBed.createComponent(GenreYearChartComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
