import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassSetEntityByOrganization } from '../cass-set-entity-by-organization.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../cass-set-entity-by-organization.test-samples';

import { CassSetEntityByOrganizationService } from './cass-set-entity-by-organization.service';

const requireRestSample: ICassSetEntityByOrganization = {
  ...sampleWithRequiredData,
};

describe('CassSetEntityByOrganization Service', () => {
  let service: CassSetEntityByOrganizationService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassSetEntityByOrganization | ICassSetEntityByOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassSetEntityByOrganizationService);
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

    it('should create a CassSetEntityByOrganization', () => {
      const cassSetEntityByOrganization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassSetEntityByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassSetEntityByOrganization', () => {
      const cassSetEntityByOrganization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassSetEntityByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassSetEntityByOrganization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassSetEntityByOrganization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassSetEntityByOrganization', () => {
      service.delete(sampleWithRequiredData.organizationId).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCassSetEntityByOrganizationToCollectionIfMissing', () => {
      it('should add a CassSetEntityByOrganization to an empty array', () => {
        const cassSetEntityByOrganization: ICassSetEntityByOrganization = sampleWithRequiredData;
        expectedResult = service.addCassSetEntityByOrganizationToCollectionIfMissing([], cassSetEntityByOrganization);
        expect(expectedResult).toEqual([cassSetEntityByOrganization]);
      });

      it('should not add a CassSetEntityByOrganization to an array that contains it', () => {
        const cassSetEntityByOrganization: ICassSetEntityByOrganization = sampleWithRequiredData;
        const cassSetEntityByOrganizationCollection: ICassSetEntityByOrganization[] = [
          {
            ...cassSetEntityByOrganization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassSetEntityByOrganizationToCollectionIfMissing(
          cassSetEntityByOrganizationCollection,
          cassSetEntityByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassSetEntityByOrganization to an array that doesn't contain it", () => {
        const cassSetEntityByOrganization: ICassSetEntityByOrganization = sampleWithRequiredData;
        const cassSetEntityByOrganizationCollection: ICassSetEntityByOrganization[] = [sampleWithPartialData];
        expectedResult = service.addCassSetEntityByOrganizationToCollectionIfMissing(
          cassSetEntityByOrganizationCollection,
          cassSetEntityByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassSetEntityByOrganization);
      });

      it('should add only unique CassSetEntityByOrganization to an array', () => {
        const cassSetEntityByOrganizationArray: ICassSetEntityByOrganization[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const cassSetEntityByOrganizationCollection: ICassSetEntityByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addCassSetEntityByOrganizationToCollectionIfMissing(
          cassSetEntityByOrganizationCollection,
          ...cassSetEntityByOrganizationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassSetEntityByOrganization: ICassSetEntityByOrganization = sampleWithRequiredData;
        const cassSetEntityByOrganization2: ICassSetEntityByOrganization = sampleWithPartialData;
        expectedResult = service.addCassSetEntityByOrganizationToCollectionIfMissing(
          [],
          cassSetEntityByOrganization,
          cassSetEntityByOrganization2,
        );
        expect(expectedResult).toEqual([cassSetEntityByOrganization, cassSetEntityByOrganization2]);
      });

      it('should accept null and undefined values', () => {
        const cassSetEntityByOrganization: ICassSetEntityByOrganization = sampleWithRequiredData;
        expectedResult = service.addCassSetEntityByOrganizationToCollectionIfMissing([], null, cassSetEntityByOrganization, undefined);
        expect(expectedResult).toEqual([cassSetEntityByOrganization]);
      });

      it('should return initial array if no CassSetEntityByOrganization is added', () => {
        const cassSetEntityByOrganizationCollection: ICassSetEntityByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addCassSetEntityByOrganizationToCollectionIfMissing(
          cassSetEntityByOrganizationCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(cassSetEntityByOrganizationCollection);
      });
    });

    describe('compareCassSetEntityByOrganization', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassSetEntityByOrganization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { organizationId: sampleWithRequiredData.organizationId };
        const entity2 = null;

        const compareResult1 = service.compareCassSetEntityByOrganization(entity1, entity2);
        const compareResult2 = service.compareCassSetEntityByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { organizationId: sampleWithRequiredData.organizationId };
        const entity2 = { organizationId: sampleWithPartialData.organizationId };

        const compareResult1 = service.compareCassSetEntityByOrganization(entity1, entity2);
        const compareResult2 = service.compareCassSetEntityByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { organizationId: sampleWithRequiredData.organizationId };
        const entity2 = { organizationId: sampleWithRequiredData.organizationId };

        const compareResult1 = service.compareCassSetEntityByOrganization(entity1, entity2);
        const compareResult2 = service.compareCassSetEntityByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
