import { TestBed } from '@angular/core/testing';

import { PingService } from './ping.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('PingService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule
    ]
  }));

  it('should be created', () => {
    const service: PingService = TestBed.get(PingService);
    expect(service).toBeTruthy();
  });

  it('should monitor whether a internet connection is available or not', () => {

  });
});
