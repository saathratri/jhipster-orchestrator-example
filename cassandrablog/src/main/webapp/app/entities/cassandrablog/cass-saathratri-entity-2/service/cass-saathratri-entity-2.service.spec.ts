import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassSaathratriEntity2 } from '../cass-saathratri-entity-2.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../cass-saathratri-entity-2.test-samples';

import { CassSaathratriEntity2Service } from './cass-saathratri-entity-2.service';

const requireRestSample: ICassSaathratriEntity2 = {
  ...sampleWithRequiredData,
};

describe('CassSaathratriEntity2 Service', () => {
  let service: CassSaathratriEntity2Service;
  let httpMock: HttpTestingController;
  let expectedResult: ICassSaathratriEntity2 | ICassSaathratriEntity2[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassSaathratriEntity2Service);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('entityTypeId', 1, 1, 'blogId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CassSaathratriEntity2', () => {
      const cassSaathratriEntity2 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassSaathratriEntity2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassSaathratriEntity2', () => {
      const cassSaathratriEntity2 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassSaathratriEntity2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassSaathratriEntity2', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassSaathratriEntity2', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassSaathratriEntity2', () => {
      service.delete(sampleWithRequiredData).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('composite-key search methods', () => {
      it('should call findAllByCompositeIdEntityTypeIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service.findAllByCompositeIdEntityTypeIdPageable('entityTypeId').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedPageable('entityTypeId', 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDatePageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDatePageable('entityTypeId', 1, 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdPageable(
            'entityTypeId',
            1,
            1,
            'blogId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanPageable('entityTypeId', 1, 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqualPageable('entityTypeId', 1, 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanPageable('entityTypeId', 1, 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqualPageable(
            'entityTypeId',
            1,
            1,
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanPageable(
            'entityTypeId',
            1,
            1,
            'blogId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqualPageable(
            'entityTypeId',
            1,
            1,
            'blogId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanPageable(
            'entityTypeId',
            1,
            1,
            'blogId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqualPageable(
            'entityTypeId',
            1,
            1,
            'blogId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
            'entityTypeId',
            1,
            1,
            'blogId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addCassSaathratriEntity2ToCollectionIfMissing', () => {
      it('should add a CassSaathratriEntity2 to an empty array', () => {
        const cassSaathratriEntity2: ICassSaathratriEntity2 = sampleWithRequiredData;
        expectedResult = service.addCassSaathratriEntity2ToCollectionIfMissing([], cassSaathratriEntity2);
        expect(expectedResult).toEqual([cassSaathratriEntity2]);
      });

      it('should not add a CassSaathratriEntity2 to an array that contains it', () => {
        const cassSaathratriEntity2: ICassSaathratriEntity2 = sampleWithRequiredData;
        const cassSaathratriEntity2Collection: ICassSaathratriEntity2[] = [
          {
            ...cassSaathratriEntity2,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassSaathratriEntity2ToCollectionIfMissing(cassSaathratriEntity2Collection, cassSaathratriEntity2);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassSaathratriEntity2 to an array that doesn't contain it", () => {
        const cassSaathratriEntity2: ICassSaathratriEntity2 = sampleWithRequiredData;
        const cassSaathratriEntity2Collection: ICassSaathratriEntity2[] = [sampleWithPartialData];
        expectedResult = service.addCassSaathratriEntity2ToCollectionIfMissing(cassSaathratriEntity2Collection, cassSaathratriEntity2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassSaathratriEntity2);
      });

      it('should add only unique CassSaathratriEntity2 to an array', () => {
        const cassSaathratriEntity2Array: ICassSaathratriEntity2[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassSaathratriEntity2Collection: ICassSaathratriEntity2[] = [sampleWithRequiredData];
        expectedResult = service.addCassSaathratriEntity2ToCollectionIfMissing(
          cassSaathratriEntity2Collection,
          ...cassSaathratriEntity2Array,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassSaathratriEntity2: ICassSaathratriEntity2 = sampleWithRequiredData;
        const cassSaathratriEntity22: ICassSaathratriEntity2 = sampleWithPartialData;
        expectedResult = service.addCassSaathratriEntity2ToCollectionIfMissing([], cassSaathratriEntity2, cassSaathratriEntity22);
        expect(expectedResult).toEqual([cassSaathratriEntity2, cassSaathratriEntity22]);
      });

      it('should accept null and undefined values', () => {
        const cassSaathratriEntity2: ICassSaathratriEntity2 = sampleWithRequiredData;
        expectedResult = service.addCassSaathratriEntity2ToCollectionIfMissing([], null, cassSaathratriEntity2, undefined);
        expect(expectedResult).toEqual([cassSaathratriEntity2]);
      });

      it('should return initial array if no CassSaathratriEntity2 is added', () => {
        const cassSaathratriEntity2Collection: ICassSaathratriEntity2[] = [sampleWithRequiredData];
        expectedResult = service.addCassSaathratriEntity2ToCollectionIfMissing(cassSaathratriEntity2Collection, undefined, null);
        expect(expectedResult).toEqual(cassSaathratriEntity2Collection);
      });
    });

    describe('compareCassSaathratriEntity2', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassSaathratriEntity2(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareCassSaathratriEntity2(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareCassSaathratriEntity2(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareCassSaathratriEntity2(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity2(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
