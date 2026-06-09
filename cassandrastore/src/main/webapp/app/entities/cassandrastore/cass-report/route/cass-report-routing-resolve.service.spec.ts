import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';

import { lastValueFrom, of } from 'rxjs';

import { ICassReport } from '../cass-report.model';
import { sampleWithRequiredData } from '../cass-report.test-samples';
import { CassReportService } from '../service/cass-report.service';

import cassReportResolve from './cass-report-routing-resolve.service';

describe('CassReport routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CassReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    vitest.spyOn(mockRouter, 'navigate');
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(CassReportService);
  });

  describe('resolve', () => {
    it('should return ICassReport returned by find', async () => {
      // GIVEN
      service.find = vitest.fn(() => of(new HttpResponse({ body: sampleWithRequiredData })));
      mockActivatedRouteSnapshot.params = { id: 'val-1' };

      // WHEN
      await new Promise<void>(resolve => {
        TestBed.runInInjectionContext(() => {
          cassReportResolve(mockActivatedRouteSnapshot).subscribe({
            next(result) {
              // THEN
              expect(service.find).toHaveBeenCalledWith('val-1');
              expect(result).toEqual(sampleWithRequiredData);
              resolve();
            },
          });
        });
      });
    });

    it('should return null if id is not provided', async () => {
      // GIVEN
      service.find = vitest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      await new Promise<void>(resolve => {
        TestBed.runInInjectionContext(() => {
          cassReportResolve(mockActivatedRouteSnapshot).subscribe({
            next(result) {
              // THEN
              expect(service.find).not.toHaveBeenCalled();
              expect(result).toEqual(null);
              resolve();
            },
          });
        });
      });
    });

    it('should navigate to 404 when find returns an empty body', async () => {
      // GIVEN — the resolver navigates to 404 (and completes empty) when the entity is not found
      service.find = vitest.fn(() => of(new HttpResponse<ICassReport>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'val-1' };

      // WHEN
      await TestBed.runInInjectionContext(async () => {
        await expect(lastValueFrom(cassReportResolve(mockActivatedRouteSnapshot))).rejects.toThrowError('no elements in sequence');
        // THEN
        expect(service.find).toHaveBeenCalledWith('val-1');
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
