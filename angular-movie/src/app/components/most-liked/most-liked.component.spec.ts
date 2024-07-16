import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MostLikedComponent } from './most-liked.component';
import { AppModule } from '../../app.module';

describe('MostLikedComponent', () => {
    let component: MostLikedComponent;
    let fixture: ComponentFixture<MostLikedComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [AppModule],
        }).compileComponents();

        fixture = TestBed.createComponent(MostLikedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
