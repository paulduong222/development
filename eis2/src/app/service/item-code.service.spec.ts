import { TestBed } from '@angular/core/testing';

import { ItemCodeService } from './item-code.service';

describe('ItemCodeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ItemCodeService = TestBed.get(ItemCodeService);
    expect(service).toBeTruthy();
  });
});
