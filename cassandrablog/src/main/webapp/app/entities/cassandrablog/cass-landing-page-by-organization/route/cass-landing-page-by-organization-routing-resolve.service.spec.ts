import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';

import { lastValueFrom, of } from 'rxjs';

import { ICassLandingPageByOrganization } from '../cass-landing-page-by-organization.model';
import { sampleWithRequiredData } from '../cass-landing-page-by-organization.test-samples';
import { CassLandingPageByOrganizationService } from '../service/cass-landing-page-by-organization.service';

import cassLandingPageByOrganizationResolve from './cass-landing-page-by-organization-routing-resolve.service';

describe('CassLandingPageByOrganization routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CassLandingPageByOrganizationService;

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
    service = TestBed.inject(CassLandingPageByOrganizationService);
  });

  describe('resolve', () => {
    it('should return ICassLandingPageByOrganization returned by find', async () => {
      // GIVEN
      service.find = vitest.fn(() => of(new HttpResponse({ body: sampleWithRequiredData })));
      mockActivatedRouteSnapshot.params = { organizationId: 'val-1' };

      // WHEN
      await new Promise<void>(resolve => {
        TestBed.runInInjectionContext(() => {
          cassLandingPageByOrganizationResolve(mockActivatedRouteSnapshot).subscribe({
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
          cassLandingPageByOrganizationResolve(mockActivatedRouteSnapshot).subscribe({
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
      service.find = vitest.fn(() => of(new HttpResponse<ICassLandingPageByOrganization>({ body: null })));
      mockActivatedRouteSnapshot.params = { organizationId: 'val-1' };

      // WHEN
      await TestBed.runInInjectionContext(async () => {
        await expect(lastValueFrom(cassLandingPageByOrganizationResolve(mockActivatedRouteSnapshot))).rejects.toThrowError(
          'no elements in sequence',
        );
        // THEN
        expect(service.find).toHaveBeenCalledWith('val-1');
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
