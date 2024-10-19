import { TestBed } from '@angular/core/testing';

import { TrafficPredictionService } from './traffic-prediction.service';

describe('TrafficPredictionService', () => {
  let service: TrafficPredictionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrafficPredictionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
