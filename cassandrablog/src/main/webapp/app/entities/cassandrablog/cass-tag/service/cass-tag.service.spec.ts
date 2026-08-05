import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassTag } from '../cass-tag.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cass-tag.test-samples';

import { CassTagService } from './cass-tag.service';

const requireRestSample: ICassTag = {
  ...sampleWithRequiredData,
};

describe('CassTag Service', () => {
  let service: CassTagService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassTag | ICassTag[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassTagService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('id').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CassTag', () => {
      const cassTag = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassTag).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassTag', () => {
      const cassTag = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassTag).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassTag', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassTag', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassTag', () => {
      service.delete(sampleWithRequiredData.id).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCassTagToCollectionIfMissing', () => {
      it('should add a CassTag to an empty array', () => {
        const cassTag: ICassTag = sampleWithRequiredData;
        expectedResult = service.addCassTagToCollectionIfMissing([], cassTag);
        expect(expectedResult).toEqual([cassTag]);
      });

      it('should not add a CassTag to an array that contains it', () => {
        const cassTag: ICassTag = sampleWithRequiredData;
        const cassTagCollection: ICassTag[] = [
          {
            ...cassTag,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassTagToCollectionIfMissing(cassTagCollection, cassTag);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassTag to an array that doesn't contain it", () => {
        const cassTag: ICassTag = sampleWithRequiredData;
        const cassTagCollection: ICassTag[] = [sampleWithPartialData];
        expectedResult = service.addCassTagToCollectionIfMissing(cassTagCollection, cassTag);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassTag);
      });

      it('should add only unique CassTag to an array', () => {
        const cassTagArray: ICassTag[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassTagCollection: ICassTag[] = [sampleWithRequiredData];
        expectedResult = service.addCassTagToCollectionIfMissing(cassTagCollection, ...cassTagArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassTag: ICassTag = sampleWithRequiredData;
        const cassTag2: ICassTag = sampleWithPartialData;
        expectedResult = service.addCassTagToCollectionIfMissing([], cassTag, cassTag2);
        expect(expectedResult).toEqual([cassTag, cassTag2]);
      });

      it('should accept null and undefined values', () => {
        const cassTag: ICassTag = sampleWithRequiredData;
        expectedResult = service.addCassTagToCollectionIfMissing([], null, cassTag, undefined);
        expect(expectedResult).toEqual([cassTag]);
      });

      it('should return initial array if no CassTag is added', () => {
        const cassTagCollection: ICassTag[] = [sampleWithRequiredData];
        expectedResult = service.addCassTagToCollectionIfMissing(cassTagCollection, undefined, null);
        expect(expectedResult).toEqual(cassTagCollection);
      });
    });

    describe('compareCassTag', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassTag(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = null;

        const compareResult1 = service.compareCassTag(entity1, entity2);
        const compareResult2 = service.compareCassTag(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = { id: sampleWithPartialData.id };

        const compareResult1 = service.compareCassTag(entity1, entity2);
        const compareResult2 = service.compareCassTag(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = { id: sampleWithRequiredData.id };

        const compareResult1 = service.compareCassTag(entity1, entity2);
        const compareResult2 = service.compareCassTag(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
