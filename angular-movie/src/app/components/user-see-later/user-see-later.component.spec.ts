import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSeeLaterComponent } from './user-see-later.component';

describe('UserSeeLaterComponent', () => {
  let component: UserSeeLaterComponent;
  let fixture: ComponentFixture<UserSeeLaterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserSeeLaterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserSeeLaterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
