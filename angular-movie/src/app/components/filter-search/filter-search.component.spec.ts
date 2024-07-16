import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterSearchComponent } from './filter-search.component';
import { AppModule } from '../../app.module';

describe('FilterSearchComponent', () => {
  let component: FilterSearchComponent;
  let fixture: ComponentFixture<FilterSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
  }).compileComponents();
    
    fixture = TestBed.createComponent(FilterSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
