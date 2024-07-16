import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterBarComponent } from './filter-bar.component';
import { AppModule } from '../../app.module';

describe('FilterBarComponent', () => {
    let component: FilterBarComponent;
    let fixture: ComponentFixture<FilterBarComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AppModule],
        }).compileComponents();

        fixture = TestBed.createComponent(FilterBarComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
