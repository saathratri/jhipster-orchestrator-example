import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';

import { lastValueFrom, of } from 'rxjs';

import { ICassBlog } from '../cass-blog.model';
import { sampleWithRequiredData } from '../cass-blog.test-samples';
import { CassBlogService } from '../service/cass-blog.service';

import cassBlogResolve from './cass-blog-routing-resolve.service';

describe('CassBlog routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CassBlogService;

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
    service = TestBed.inject(CassBlogService);
  });

  describe('resolve', () => {
    it('should return ICassBlog returned by find', async () => {
      // GIVEN
      service.find = vitest.fn(() => of(new HttpResponse({ body: sampleWithRequiredData })));
      mockActivatedRouteSnapshot.params = { category: 'val-1', blogId: 'val-2' };

      // WHEN
      await new Promise<void>(resolve => {
        TestBed.runInInjectionContext(() => {
          cassBlogResolve(mockActivatedRouteSnapshot).subscribe({
            next(result) {
              // THEN
              expect(service.find).toHaveBeenCalledWith('val-1', 'val-2');
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
          cassBlogResolve(mockActivatedRouteSnapshot).subscribe({
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
      service.find = vitest.fn(() => of(new HttpResponse<ICassBlog>({ body: null })));
      mockActivatedRouteSnapshot.params = { category: 'val-1', blogId: 'val-2' };

      // WHEN
      await TestBed.runInInjectionContext(async () => {
        await expect(lastValueFrom(cassBlogResolve(mockActivatedRouteSnapshot))).rejects.toThrowError('no elements in sequence');
        // THEN
        expect(service.find).toHaveBeenCalledWith('val-1', 'val-2');
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
