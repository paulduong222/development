import { TestBed } from '@angular/core/testing';

import { TrainingSpecialistReportService } from './training-specialist-report.service';

describe('TrainingSpecialistReportService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TrainingSpecialistReportService = TestBed.get(TrainingSpecialistReportService);
    expect(service).toBeTruthy();
  });
});
