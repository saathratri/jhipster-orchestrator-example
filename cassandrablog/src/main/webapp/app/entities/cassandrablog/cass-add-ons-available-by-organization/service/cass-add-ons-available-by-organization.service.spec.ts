import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassAddOnsAvailableByOrganization } from '../cass-add-ons-available-by-organization.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../cass-add-ons-available-by-organization.test-samples';

import { CassAddOnsAvailableByOrganizationService } from './cass-add-ons-available-by-organization.service';

const requireRestSample: ICassAddOnsAvailableByOrganization = {
  ...sampleWithRequiredData,
};

describe('CassAddOnsAvailableByOrganization Service', () => {
  let service: CassAddOnsAvailableByOrganizationService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassAddOnsAvailableByOrganization | ICassAddOnsAvailableByOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassAddOnsAvailableByOrganizationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('organizationId', 'entityType', 'entityId', 'addOnId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CassAddOnsAvailableByOrganization', () => {
      const cassAddOnsAvailableByOrganization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassAddOnsAvailableByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassAddOnsAvailableByOrganization', () => {
      const cassAddOnsAvailableByOrganization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassAddOnsAvailableByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassAddOnsAvailableByOrganization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassAddOnsAvailableByOrganization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassAddOnsAvailableByOrganization', () => {
      service.delete(sampleWithRequiredData).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('composite-key search methods', () => {
      it('should call findAllByCompositeIdOrganizationIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service.findAllByCompositeIdOrganizationIdPageable('organizationId').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable('organizationId', 'entityType')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable(
            'organizationId',
            'entityType',
            'entityId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnIdPageable(
            'organizationId',
            'entityType',
            'entityId',
            'addOnId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
            'organizationId',
            'entityType',
            'entityId',
            'addOnId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addCassAddOnsAvailableByOrganizationToCollectionIfMissing', () => {
      it('should add a CassAddOnsAvailableByOrganization to an empty array', () => {
        const cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization = sampleWithRequiredData;
        expectedResult = service.addCassAddOnsAvailableByOrganizationToCollectionIfMissing([], cassAddOnsAvailableByOrganization);
        expect(expectedResult).toEqual([cassAddOnsAvailableByOrganization]);
      });

      it('should not add a CassAddOnsAvailableByOrganization to an array that contains it', () => {
        const cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization = sampleWithRequiredData;
        const cassAddOnsAvailableByOrganizationCollection: ICassAddOnsAvailableByOrganization[] = [
          {
            ...cassAddOnsAvailableByOrganization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassAddOnsAvailableByOrganizationToCollectionIfMissing(
          cassAddOnsAvailableByOrganizationCollection,
          cassAddOnsAvailableByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassAddOnsAvailableByOrganization to an array that doesn't contain it", () => {
        const cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization = sampleWithRequiredData;
        const cassAddOnsAvailableByOrganizationCollection: ICassAddOnsAvailableByOrganization[] = [sampleWithPartialData];
        expectedResult = service.addCassAddOnsAvailableByOrganizationToCollectionIfMissing(
          cassAddOnsAvailableByOrganizationCollection,
          cassAddOnsAvailableByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassAddOnsAvailableByOrganization);
      });

      it('should add only unique CassAddOnsAvailableByOrganization to an array', () => {
        const cassAddOnsAvailableByOrganizationArray: ICassAddOnsAvailableByOrganization[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const cassAddOnsAvailableByOrganizationCollection: ICassAddOnsAvailableByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addCassAddOnsAvailableByOrganizationToCollectionIfMissing(
          cassAddOnsAvailableByOrganizationCollection,
          ...cassAddOnsAvailableByOrganizationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization = sampleWithRequiredData;
        const cassAddOnsAvailableByOrganization2: ICassAddOnsAvailableByOrganization = sampleWithPartialData;
        expectedResult = service.addCassAddOnsAvailableByOrganizationToCollectionIfMissing(
          [],
          cassAddOnsAvailableByOrganization,
          cassAddOnsAvailableByOrganization2,
        );
        expect(expectedResult).toEqual([cassAddOnsAvailableByOrganization, cassAddOnsAvailableByOrganization2]);
      });

      it('should accept null and undefined values', () => {
        const cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization = sampleWithRequiredData;
        expectedResult = service.addCassAddOnsAvailableByOrganizationToCollectionIfMissing(
          [],
          null,
          cassAddOnsAvailableByOrganization,
          undefined,
        );
        expect(expectedResult).toEqual([cassAddOnsAvailableByOrganization]);
      });

      it('should return initial array if no CassAddOnsAvailableByOrganization is added', () => {
        const cassAddOnsAvailableByOrganizationCollection: ICassAddOnsAvailableByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addCassAddOnsAvailableByOrganizationToCollectionIfMissing(
          cassAddOnsAvailableByOrganizationCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(cassAddOnsAvailableByOrganizationCollection);
      });
    });

    describe('compareCassAddOnsAvailableByOrganization', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassAddOnsAvailableByOrganization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareCassAddOnsAvailableByOrganization(entity1, entity2);
        const compareResult2 = service.compareCassAddOnsAvailableByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareCassAddOnsAvailableByOrganization(entity1, entity2);
        const compareResult2 = service.compareCassAddOnsAvailableByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareCassAddOnsAvailableByOrganization(entity1, entity2);
        const compareResult2 = service.compareCassAddOnsAvailableByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
