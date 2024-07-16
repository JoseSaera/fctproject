import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MostViewedComponent } from './most-viewed.component';
import { AppModule } from '../../app.module';

describe('MostViewedComponent', () => {
    let component: MostViewedComponent;
    let fixture: ComponentFixture<MostViewedComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AppModule],
        }).compileComponents();

        fixture = TestBed.createComponent(MostViewedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
