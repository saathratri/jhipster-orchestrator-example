import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassBlog } from '../cass-blog.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cass-blog.test-samples';

import { CassBlogService } from './cass-blog.service';

const requireRestSample: ICassBlog = {
  ...sampleWithRequiredData,
};

describe('CassBlog Service', () => {
  let service: CassBlogService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassBlog | ICassBlog[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassBlogService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('category', 'blogId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CassBlog', () => {
      const cassBlog = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassBlog).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassBlog', () => {
      const cassBlog = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassBlog).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassBlog', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassBlog', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassBlog', () => {
      service.delete(sampleWithRequiredData).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('composite-key search methods', () => {
      it('should call findAllByCompositeIdCategoryPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service.findAllByCompositeIdCategoryPageable('category').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdCategoryAndCompositeIdBlogId', () => {
        const returnedFromService = { ...requireRestSample };

        service.findByCompositeIdCategoryAndCompositeIdBlogId('category', 'blogId').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addCassBlogToCollectionIfMissing', () => {
      it('should add a CassBlog to an empty array', () => {
        const cassBlog: ICassBlog = sampleWithRequiredData;
        expectedResult = service.addCassBlogToCollectionIfMissing([], cassBlog);
        expect(expectedResult).toEqual([cassBlog]);
      });

      it('should not add a CassBlog to an array that contains it', () => {
        const cassBlog: ICassBlog = sampleWithRequiredData;
        const cassBlogCollection: ICassBlog[] = [
          {
            ...cassBlog,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassBlogToCollectionIfMissing(cassBlogCollection, cassBlog);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassBlog to an array that doesn't contain it", () => {
        const cassBlog: ICassBlog = sampleWithRequiredData;
        const cassBlogCollection: ICassBlog[] = [sampleWithPartialData];
        expectedResult = service.addCassBlogToCollectionIfMissing(cassBlogCollection, cassBlog);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassBlog);
      });

      it('should add only unique CassBlog to an array', () => {
        const cassBlogArray: ICassBlog[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassBlogCollection: ICassBlog[] = [sampleWithRequiredData];
        expectedResult = service.addCassBlogToCollectionIfMissing(cassBlogCollection, ...cassBlogArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassBlog: ICassBlog = sampleWithRequiredData;
        const cassBlog2: ICassBlog = sampleWithPartialData;
        expectedResult = service.addCassBlogToCollectionIfMissing([], cassBlog, cassBlog2);
        expect(expectedResult).toEqual([cassBlog, cassBlog2]);
      });

      it('should accept null and undefined values', () => {
        const cassBlog: ICassBlog = sampleWithRequiredData;
        expectedResult = service.addCassBlogToCollectionIfMissing([], null, cassBlog, undefined);
        expect(expectedResult).toEqual([cassBlog]);
      });

      it('should return initial array if no CassBlog is added', () => {
        const cassBlogCollection: ICassBlog[] = [sampleWithRequiredData];
        expectedResult = service.addCassBlogToCollectionIfMissing(cassBlogCollection, undefined, null);
        expect(expectedResult).toEqual(cassBlogCollection);
      });
    });

    describe('compareCassBlog', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassBlog(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareCassBlog(entity1, entity2);
        const compareResult2 = service.compareCassBlog(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareCassBlog(entity1, entity2);
        const compareResult2 = service.compareCassBlog(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareCassBlog(entity1, entity2);
        const compareResult2 = service.compareCassBlog(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
