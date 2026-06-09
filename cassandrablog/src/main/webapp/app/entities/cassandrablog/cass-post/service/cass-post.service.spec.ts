import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassPost } from '../cass-post.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cass-post.test-samples';

import { CassPostService } from './cass-post.service';

const requireRestSample: ICassPost = {
  ...sampleWithRequiredData,
};

describe('CassPost Service', () => {
  let service: CassPostService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassPost | ICassPost[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassPostService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(1, 1, 'postId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CassPost', () => {
      const cassPost = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassPost).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassPost', () => {
      const cassPost = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassPost).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassPost', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassPost', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassPost', () => {
      service.delete(sampleWithRequiredData).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('composite-key search methods', () => {
      it('should call findAllByCompositeIdCreatedDatePageable', () => {
        const returnedFromService = { ...requireRestSample };

        service.findAllByCompositeIdCreatedDatePageable(1).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable', () => {
        const returnedFromService = { ...requireRestSample };

        service.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable(1, 1).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostIdPageable(1, 1, 'postId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable(1, 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable(1, 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable(1, 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable(1, 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(1, 1, 'postId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addCassPostToCollectionIfMissing', () => {
      it('should add a CassPost to an empty array', () => {
        const cassPost: ICassPost = sampleWithRequiredData;
        expectedResult = service.addCassPostToCollectionIfMissing([], cassPost);
        expect(expectedResult).toEqual([cassPost]);
      });

      it('should not add a CassPost to an array that contains it', () => {
        const cassPost: ICassPost = sampleWithRequiredData;
        const cassPostCollection: ICassPost[] = [
          {
            ...cassPost,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassPostToCollectionIfMissing(cassPostCollection, cassPost);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassPost to an array that doesn't contain it", () => {
        const cassPost: ICassPost = sampleWithRequiredData;
        const cassPostCollection: ICassPost[] = [sampleWithPartialData];
        expectedResult = service.addCassPostToCollectionIfMissing(cassPostCollection, cassPost);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassPost);
      });

      it('should add only unique CassPost to an array', () => {
        const cassPostArray: ICassPost[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassPostCollection: ICassPost[] = [sampleWithRequiredData];
        expectedResult = service.addCassPostToCollectionIfMissing(cassPostCollection, ...cassPostArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassPost: ICassPost = sampleWithRequiredData;
        const cassPost2: ICassPost = sampleWithPartialData;
        expectedResult = service.addCassPostToCollectionIfMissing([], cassPost, cassPost2);
        expect(expectedResult).toEqual([cassPost, cassPost2]);
      });

      it('should accept null and undefined values', () => {
        const cassPost: ICassPost = sampleWithRequiredData;
        expectedResult = service.addCassPostToCollectionIfMissing([], null, cassPost, undefined);
        expect(expectedResult).toEqual([cassPost]);
      });

      it('should return initial array if no CassPost is added', () => {
        const cassPostCollection: ICassPost[] = [sampleWithRequiredData];
        expectedResult = service.addCassPostToCollectionIfMissing(cassPostCollection, undefined, null);
        expect(expectedResult).toEqual(cassPostCollection);
      });
    });

    describe('compareCassPost', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassPost(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareCassPost(entity1, entity2);
        const compareResult2 = service.compareCassPost(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareCassPost(entity1, entity2);
        const compareResult2 = service.compareCassPost(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareCassPost(entity1, entity2);
        const compareResult2 = service.compareCassPost(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
