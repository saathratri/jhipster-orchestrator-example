import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassSaathratriEntity3 } from '../cass-saathratri-entity-3.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../cass-saathratri-entity-3.test-samples';

import { CassSaathratriEntity3Service } from './cass-saathratri-entity-3.service';

const requireRestSample: ICassSaathratriEntity3 = {
  ...sampleWithRequiredData,
};

describe('CassSaathratriEntity3 Service', () => {
  let service: CassSaathratriEntity3Service;
  let httpMock: HttpTestingController;
  let expectedResult: ICassSaathratriEntity3 | ICassSaathratriEntity3[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassSaathratriEntity3Service);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('entityType', 'createdTimeId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CassSaathratriEntity3', () => {
      const cassSaathratriEntity3 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassSaathratriEntity3).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassSaathratriEntity3', () => {
      const cassSaathratriEntity3 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassSaathratriEntity3).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassSaathratriEntity3', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassSaathratriEntity3', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassSaathratriEntity3', () => {
      service.delete(sampleWithRequiredData).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('composite-key search methods', () => {
      it('should call findAllByCompositeIdEntityTypePageable', () => {
        const returnedFromService = { ...requireRestSample };

        service.findAllByCompositeIdEntityTypePageable('entityType').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addCassSaathratriEntity3ToCollectionIfMissing', () => {
      it('should add a CassSaathratriEntity3 to an empty array', () => {
        const cassSaathratriEntity3: ICassSaathratriEntity3 = sampleWithRequiredData;
        expectedResult = service.addCassSaathratriEntity3ToCollectionIfMissing([], cassSaathratriEntity3);
        expect(expectedResult).toEqual([cassSaathratriEntity3]);
      });

      it('should not add a CassSaathratriEntity3 to an array that contains it', () => {
        const cassSaathratriEntity3: ICassSaathratriEntity3 = sampleWithRequiredData;
        const cassSaathratriEntity3Collection: ICassSaathratriEntity3[] = [
          {
            ...cassSaathratriEntity3,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassSaathratriEntity3ToCollectionIfMissing(cassSaathratriEntity3Collection, cassSaathratriEntity3);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassSaathratriEntity3 to an array that doesn't contain it", () => {
        const cassSaathratriEntity3: ICassSaathratriEntity3 = sampleWithRequiredData;
        const cassSaathratriEntity3Collection: ICassSaathratriEntity3[] = [sampleWithPartialData];
        expectedResult = service.addCassSaathratriEntity3ToCollectionIfMissing(cassSaathratriEntity3Collection, cassSaathratriEntity3);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassSaathratriEntity3);
      });

      it('should add only unique CassSaathratriEntity3 to an array', () => {
        const cassSaathratriEntity3Array: ICassSaathratriEntity3[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassSaathratriEntity3Collection: ICassSaathratriEntity3[] = [sampleWithRequiredData];
        expectedResult = service.addCassSaathratriEntity3ToCollectionIfMissing(
          cassSaathratriEntity3Collection,
          ...cassSaathratriEntity3Array,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassSaathratriEntity3: ICassSaathratriEntity3 = sampleWithRequiredData;
        const cassSaathratriEntity32: ICassSaathratriEntity3 = sampleWithPartialData;
        expectedResult = service.addCassSaathratriEntity3ToCollectionIfMissing([], cassSaathratriEntity3, cassSaathratriEntity32);
        expect(expectedResult).toEqual([cassSaathratriEntity3, cassSaathratriEntity32]);
      });

      it('should accept null and undefined values', () => {
        const cassSaathratriEntity3: ICassSaathratriEntity3 = sampleWithRequiredData;
        expectedResult = service.addCassSaathratriEntity3ToCollectionIfMissing([], null, cassSaathratriEntity3, undefined);
        expect(expectedResult).toEqual([cassSaathratriEntity3]);
      });

      it('should return initial array if no CassSaathratriEntity3 is added', () => {
        const cassSaathratriEntity3Collection: ICassSaathratriEntity3[] = [sampleWithRequiredData];
        expectedResult = service.addCassSaathratriEntity3ToCollectionIfMissing(cassSaathratriEntity3Collection, undefined, null);
        expect(expectedResult).toEqual(cassSaathratriEntity3Collection);
      });
    });

    describe('compareCassSaathratriEntity3', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassSaathratriEntity3(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareCassSaathratriEntity3(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity3(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareCassSaathratriEntity3(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity3(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareCassSaathratriEntity3(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity3(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
