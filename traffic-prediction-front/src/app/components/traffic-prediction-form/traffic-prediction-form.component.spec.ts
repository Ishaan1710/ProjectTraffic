import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrafficPredictionFormComponent } from './traffic-prediction-form.component';

describe('TrafficPredictionFormComponent', () => {
  let component: TrafficPredictionFormComponent;
  let fixture: ComponentFixture<TrafficPredictionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrafficPredictionFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrafficPredictionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
