import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassLandingPageByOrganization } from '../cass-landing-page-by-organization.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../cass-landing-page-by-organization.test-samples';

import { CassLandingPageByOrganizationService } from './cass-landing-page-by-organization.service';

const requireRestSample: ICassLandingPageByOrganization = {
  ...sampleWithRequiredData,
};

describe('CassLandingPageByOrganization Service', () => {
  let service: CassLandingPageByOrganizationService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassLandingPageByOrganization | ICassLandingPageByOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassLandingPageByOrganizationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('organizationId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CassLandingPageByOrganization', () => {
      const cassLandingPageByOrganization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassLandingPageByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassLandingPageByOrganization', () => {
      const cassLandingPageByOrganization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassLandingPageByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassLandingPageByOrganization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassLandingPageByOrganization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassLandingPageByOrganization', () => {
      service.delete(sampleWithRequiredData.organizationId).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCassLandingPageByOrganizationToCollectionIfMissing', () => {
      it('should add a CassLandingPageByOrganization to an empty array', () => {
        const cassLandingPageByOrganization: ICassLandingPageByOrganization = sampleWithRequiredData;
        expectedResult = service.addCassLandingPageByOrganizationToCollectionIfMissing([], cassLandingPageByOrganization);
        expect(expectedResult).toEqual([cassLandingPageByOrganization]);
      });

      it('should not add a CassLandingPageByOrganization to an array that contains it', () => {
        const cassLandingPageByOrganization: ICassLandingPageByOrganization = sampleWithRequiredData;
        const cassLandingPageByOrganizationCollection: ICassLandingPageByOrganization[] = [
          {
            ...cassLandingPageByOrganization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassLandingPageByOrganizationToCollectionIfMissing(
          cassLandingPageByOrganizationCollection,
          cassLandingPageByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassLandingPageByOrganization to an array that doesn't contain it", () => {
        const cassLandingPageByOrganization: ICassLandingPageByOrganization = sampleWithRequiredData;
        const cassLandingPageByOrganizationCollection: ICassLandingPageByOrganization[] = [sampleWithPartialData];
        expectedResult = service.addCassLandingPageByOrganizationToCollectionIfMissing(
          cassLandingPageByOrganizationCollection,
          cassLandingPageByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassLandingPageByOrganization);
      });

      it('should add only unique CassLandingPageByOrganization to an array', () => {
        const cassLandingPageByOrganizationArray: ICassLandingPageByOrganization[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const cassLandingPageByOrganizationCollection: ICassLandingPageByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addCassLandingPageByOrganizationToCollectionIfMissing(
          cassLandingPageByOrganizationCollection,
          ...cassLandingPageByOrganizationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassLandingPageByOrganization: ICassLandingPageByOrganization = sampleWithRequiredData;
        const cassLandingPageByOrganization2: ICassLandingPageByOrganization = sampleWithPartialData;
        expectedResult = service.addCassLandingPageByOrganizationToCollectionIfMissing(
          [],
          cassLandingPageByOrganization,
          cassLandingPageByOrganization2,
        );
        expect(expectedResult).toEqual([cassLandingPageByOrganization, cassLandingPageByOrganization2]);
      });

      it('should accept null and undefined values', () => {
        const cassLandingPageByOrganization: ICassLandingPageByOrganization = sampleWithRequiredData;
        expectedResult = service.addCassLandingPageByOrganizationToCollectionIfMissing([], null, cassLandingPageByOrganization, undefined);
        expect(expectedResult).toEqual([cassLandingPageByOrganization]);
      });

      it('should return initial array if no CassLandingPageByOrganization is added', () => {
        const cassLandingPageByOrganizationCollection: ICassLandingPageByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addCassLandingPageByOrganizationToCollectionIfMissing(
          cassLandingPageByOrganizationCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(cassLandingPageByOrganizationCollection);
      });
    });

    describe('compareCassLandingPageByOrganization', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassLandingPageByOrganization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { organizationId: sampleWithRequiredData.organizationId };
        const entity2 = null;

        const compareResult1 = service.compareCassLandingPageByOrganization(entity1, entity2);
        const compareResult2 = service.compareCassLandingPageByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { organizationId: sampleWithRequiredData.organizationId };
        const entity2 = { organizationId: sampleWithPartialData.organizationId };

        const compareResult1 = service.compareCassLandingPageByOrganization(entity1, entity2);
        const compareResult2 = service.compareCassLandingPageByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { organizationId: sampleWithRequiredData.organizationId };
        const entity2 = { organizationId: sampleWithRequiredData.organizationId };

        const compareResult1 = service.compareCassLandingPageByOrganization(entity1, entity2);
        const compareResult2 = service.compareCassLandingPageByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
