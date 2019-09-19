import { TestBed } from '@angular/core/testing';

import { TrainingSpecialistSettingsService } from './training-specialist-settings.service';

describe('TrainingSpecialistSettingsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TrainingSpecialistSettingsService = TestBed.get(TrainingSpecialistSettingsService);
    expect(service).toBeTruthy();
  });
});
