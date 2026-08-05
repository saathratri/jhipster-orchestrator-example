import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassSaathratriEntity4 } from '../cass-saathratri-entity-4.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../cass-saathratri-entity-4.test-samples';

import { CassSaathratriEntity4Service } from './cass-saathratri-entity-4.service';

const requireRestSample: ICassSaathratriEntity4 = {
  ...sampleWithRequiredData,
};

describe('CassSaathratriEntity4 Service', () => {
  let service: CassSaathratriEntity4Service;
  let httpMock: HttpTestingController;
  let expectedResult: ICassSaathratriEntity4 | ICassSaathratriEntity4[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassSaathratriEntity4Service);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('organizationId', 'attributeKey').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CassSaathratriEntity4', () => {
      const cassSaathratriEntity4 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassSaathratriEntity4).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassSaathratriEntity4', () => {
      const cassSaathratriEntity4 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassSaathratriEntity4).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassSaathratriEntity4', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassSaathratriEntity4', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassSaathratriEntity4', () => {
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
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdAttributeKeyPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdAttributeKeyPageable('organizationId', 'attributeKey')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdOrganizationIdAndCompositeIdAttributeKey', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findByCompositeIdOrganizationIdAndCompositeIdAttributeKey('organizationId', 'attributeKey')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addCassSaathratriEntity4ToCollectionIfMissing', () => {
      it('should add a CassSaathratriEntity4 to an empty array', () => {
        const cassSaathratriEntity4: ICassSaathratriEntity4 = sampleWithRequiredData;
        expectedResult = service.addCassSaathratriEntity4ToCollectionIfMissing([], cassSaathratriEntity4);
        expect(expectedResult).toEqual([cassSaathratriEntity4]);
      });

      it('should not add a CassSaathratriEntity4 to an array that contains it', () => {
        const cassSaathratriEntity4: ICassSaathratriEntity4 = sampleWithRequiredData;
        const cassSaathratriEntity4Collection: ICassSaathratriEntity4[] = [
          {
            ...cassSaathratriEntity4,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassSaathratriEntity4ToCollectionIfMissing(cassSaathratriEntity4Collection, cassSaathratriEntity4);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassSaathratriEntity4 to an array that doesn't contain it", () => {
        const cassSaathratriEntity4: ICassSaathratriEntity4 = sampleWithRequiredData;
        const cassSaathratriEntity4Collection: ICassSaathratriEntity4[] = [sampleWithPartialData];
        expectedResult = service.addCassSaathratriEntity4ToCollectionIfMissing(cassSaathratriEntity4Collection, cassSaathratriEntity4);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassSaathratriEntity4);
      });

      it('should add only unique CassSaathratriEntity4 to an array', () => {
        const cassSaathratriEntity4Array: ICassSaathratriEntity4[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassSaathratriEntity4Collection: ICassSaathratriEntity4[] = [sampleWithRequiredData];
        expectedResult = service.addCassSaathratriEntity4ToCollectionIfMissing(
          cassSaathratriEntity4Collection,
          ...cassSaathratriEntity4Array,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassSaathratriEntity4: ICassSaathratriEntity4 = sampleWithRequiredData;
        const cassSaathratriEntity42: ICassSaathratriEntity4 = sampleWithPartialData;
        expectedResult = service.addCassSaathratriEntity4ToCollectionIfMissing([], cassSaathratriEntity4, cassSaathratriEntity42);
        expect(expectedResult).toEqual([cassSaathratriEntity4, cassSaathratriEntity42]);
      });

      it('should accept null and undefined values', () => {
        const cassSaathratriEntity4: ICassSaathratriEntity4 = sampleWithRequiredData;
        expectedResult = service.addCassSaathratriEntity4ToCollectionIfMissing([], null, cassSaathratriEntity4, undefined);
        expect(expectedResult).toEqual([cassSaathratriEntity4]);
      });

      it('should return initial array if no CassSaathratriEntity4 is added', () => {
        const cassSaathratriEntity4Collection: ICassSaathratriEntity4[] = [sampleWithRequiredData];
        expectedResult = service.addCassSaathratriEntity4ToCollectionIfMissing(cassSaathratriEntity4Collection, undefined, null);
        expect(expectedResult).toEqual(cassSaathratriEntity4Collection);
      });
    });

    describe('compareCassSaathratriEntity4', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassSaathratriEntity4(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareCassSaathratriEntity4(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity4(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareCassSaathratriEntity4(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity4(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareCassSaathratriEntity4(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity4(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
