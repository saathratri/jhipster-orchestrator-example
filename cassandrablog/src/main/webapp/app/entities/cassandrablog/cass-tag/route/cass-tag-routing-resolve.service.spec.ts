import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';

import { lastValueFrom, of } from 'rxjs';

import { ICassTag } from '../cass-tag.model';
import { sampleWithRequiredData } from '../cass-tag.test-samples';
import { CassTagService } from '../service/cass-tag.service';

import cassTagResolve from './cass-tag-routing-resolve.service';

describe('CassTag routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CassTagService;

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
    service = TestBed.inject(CassTagService);
  });

  describe('resolve', () => {
    it('should return ICassTag returned by find', async () => {
      // GIVEN
      service.find = vitest.fn(() => of(new HttpResponse({ body: sampleWithRequiredData })));
      mockActivatedRouteSnapshot.params = { id: 'val-1' };

      // WHEN
      await new Promise<void>(resolve => {
        TestBed.runInInjectionContext(() => {
          cassTagResolve(mockActivatedRouteSnapshot).subscribe({
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
          cassTagResolve(mockActivatedRouteSnapshot).subscribe({
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
      service.find = vitest.fn(() => of(new HttpResponse<ICassTag>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'val-1' };

      // WHEN
      await TestBed.runInInjectionContext(async () => {
        await expect(lastValueFrom(cassTagResolve(mockActivatedRouteSnapshot))).rejects.toThrowError('no elements in sequence');
        // THEN
        expect(service.find).toHaveBeenCalledWith('val-1');
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
