import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IPsqlPost } from '../psql-post.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../psql-post.test-samples';

import { PsqlPostService, RestPsqlPost } from './psql-post.service';

const requireRestSample: RestPsqlPost = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.toJSON(),
};

describe('PsqlPost Service', () => {
  let service: PsqlPostService;
  let httpMock: HttpTestingController;
  let expectedResult: IPsqlPost | IPsqlPost[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PsqlPostService);
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

    it('should create a PsqlPost', () => {
      const psqlPost = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(psqlPost).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PsqlPost', () => {
      const psqlPost = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(psqlPost).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PsqlPost', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PsqlPost', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PsqlPost', () => {
      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addPsqlPostToCollectionIfMissing', () => {
      it('should add a PsqlPost to an empty array', () => {
        const psqlPost: IPsqlPost = sampleWithRequiredData;
        expectedResult = service.addPsqlPostToCollectionIfMissing([], psqlPost);
        expect(expectedResult).toEqual([psqlPost]);
      });

      it('should not add a PsqlPost to an array that contains it', () => {
        const psqlPost: IPsqlPost = sampleWithRequiredData;
        const psqlPostCollection: IPsqlPost[] = [
          {
            ...psqlPost,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPsqlPostToCollectionIfMissing(psqlPostCollection, psqlPost);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PsqlPost to an array that doesn't contain it", () => {
        const psqlPost: IPsqlPost = sampleWithRequiredData;
        const psqlPostCollection: IPsqlPost[] = [sampleWithPartialData];
        expectedResult = service.addPsqlPostToCollectionIfMissing(psqlPostCollection, psqlPost);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(psqlPost);
      });

      it('should add only unique PsqlPost to an array', () => {
        const psqlPostArray: IPsqlPost[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const psqlPostCollection: IPsqlPost[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlPostToCollectionIfMissing(psqlPostCollection, ...psqlPostArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const psqlPost: IPsqlPost = sampleWithRequiredData;
        const psqlPost2: IPsqlPost = sampleWithPartialData;
        expectedResult = service.addPsqlPostToCollectionIfMissing([], psqlPost, psqlPost2);
        expect(expectedResult).toEqual([psqlPost, psqlPost2]);
      });

      it('should accept null and undefined values', () => {
        const psqlPost: IPsqlPost = sampleWithRequiredData;
        expectedResult = service.addPsqlPostToCollectionIfMissing([], null, psqlPost, undefined);
        expect(expectedResult).toEqual([psqlPost]);
      });

      it('should return initial array if no PsqlPost is added', () => {
        const psqlPostCollection: IPsqlPost[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlPostToCollectionIfMissing(psqlPostCollection, undefined, null);
        expect(expectedResult).toEqual(psqlPostCollection);
      });
    });

    describe('comparePsqlPost', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePsqlPost(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' };
        const entity2 = null;

        const compareResult1 = service.comparePsqlPost(entity1, entity2);
        const compareResult2 = service.comparePsqlPost(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' };
        const entity2 = { id: '65e57101-2025-434a-ba76-944e34e9c0fb' };

        const compareResult1 = service.comparePsqlPost(entity1, entity2);
        const compareResult2 = service.comparePsqlPost(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' };
        const entity2 = { id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' };

        const compareResult1 = service.comparePsqlPost(entity1, entity2);
        const compareResult2 = service.comparePsqlPost(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
