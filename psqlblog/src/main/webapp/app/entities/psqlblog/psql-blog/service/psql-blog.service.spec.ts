import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IPsqlBlog } from '../psql-blog.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../psql-blog.test-samples';

import { PsqlBlogService } from './psql-blog.service';

const requireRestSample: IPsqlBlog = {
  ...sampleWithRequiredData,
};

describe('PsqlBlog Service', () => {
  let service: PsqlBlogService;
  let httpMock: HttpTestingController;
  let expectedResult: IPsqlBlog | IPsqlBlog[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PsqlBlogService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PsqlBlog', () => {
      const psqlBlog = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(psqlBlog).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PsqlBlog', () => {
      const psqlBlog = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(psqlBlog).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PsqlBlog', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PsqlBlog', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PsqlBlog', () => {
      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addPsqlBlogToCollectionIfMissing', () => {
      it('should add a PsqlBlog to an empty array', () => {
        const psqlBlog: IPsqlBlog = sampleWithRequiredData;
        expectedResult = service.addPsqlBlogToCollectionIfMissing([], psqlBlog);
        expect(expectedResult).toEqual([psqlBlog]);
      });

      it('should not add a PsqlBlog to an array that contains it', () => {
        const psqlBlog: IPsqlBlog = sampleWithRequiredData;
        const psqlBlogCollection: IPsqlBlog[] = [
          {
            ...psqlBlog,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPsqlBlogToCollectionIfMissing(psqlBlogCollection, psqlBlog);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PsqlBlog to an array that doesn't contain it", () => {
        const psqlBlog: IPsqlBlog = sampleWithRequiredData;
        const psqlBlogCollection: IPsqlBlog[] = [sampleWithPartialData];
        expectedResult = service.addPsqlBlogToCollectionIfMissing(psqlBlogCollection, psqlBlog);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(psqlBlog);
      });

      it('should add only unique PsqlBlog to an array', () => {
        const psqlBlogArray: IPsqlBlog[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const psqlBlogCollection: IPsqlBlog[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlBlogToCollectionIfMissing(psqlBlogCollection, ...psqlBlogArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const psqlBlog: IPsqlBlog = sampleWithRequiredData;
        const psqlBlog2: IPsqlBlog = sampleWithPartialData;
        expectedResult = service.addPsqlBlogToCollectionIfMissing([], psqlBlog, psqlBlog2);
        expect(expectedResult).toEqual([psqlBlog, psqlBlog2]);
      });

      it('should accept null and undefined values', () => {
        const psqlBlog: IPsqlBlog = sampleWithRequiredData;
        expectedResult = service.addPsqlBlogToCollectionIfMissing([], null, psqlBlog, undefined);
        expect(expectedResult).toEqual([psqlBlog]);
      });

      it('should return initial array if no PsqlBlog is added', () => {
        const psqlBlogCollection: IPsqlBlog[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlBlogToCollectionIfMissing(psqlBlogCollection, undefined, null);
        expect(expectedResult).toEqual(psqlBlogCollection);
      });
    });

    describe('comparePsqlBlog', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePsqlBlog(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };
        const entity2 = null;

        const compareResult1 = service.comparePsqlBlog(entity1, entity2);
        const compareResult2 = service.comparePsqlBlog(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };
        const entity2 = { id: 'bd89b5d2-fe35-41a6-b81c-17a7f1afe1eb' };

        const compareResult1 = service.comparePsqlBlog(entity1, entity2);
        const compareResult2 = service.comparePsqlBlog(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };
        const entity2 = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };

        const compareResult1 = service.comparePsqlBlog(entity1, entity2);
        const compareResult2 = service.comparePsqlBlog(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
