import { TestBed } from '@angular/core/testing';

import { TrainingSpecialistService } from './training-specialist.service';

describe('TrainingSpecialistService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TrainingSpecialistService = TestBed.get(TrainingSpecialistService);
    expect(service).toBeTruthy();
  });
});
