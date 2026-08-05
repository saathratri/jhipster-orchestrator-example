import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IPsqlTag } from '../psql-tag.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../psql-tag.test-samples';

import { PsqlTagService } from './psql-tag.service';

const requireRestSample: IPsqlTag = {
  ...sampleWithRequiredData,
};

describe('PsqlTag Service', () => {
  let service: PsqlTagService;
  let httpMock: HttpTestingController;
  let expectedResult: IPsqlTag | IPsqlTag[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PsqlTagService);
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

    it('should create a PsqlTag', () => {
      const psqlTag = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(psqlTag).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PsqlTag', () => {
      const psqlTag = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(psqlTag).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PsqlTag', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PsqlTag', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PsqlTag', () => {
      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addPsqlTagToCollectionIfMissing', () => {
      it('should add a PsqlTag to an empty array', () => {
        const psqlTag: IPsqlTag = sampleWithRequiredData;
        expectedResult = service.addPsqlTagToCollectionIfMissing([], psqlTag);
        expect(expectedResult).toEqual([psqlTag]);
      });

      it('should not add a PsqlTag to an array that contains it', () => {
        const psqlTag: IPsqlTag = sampleWithRequiredData;
        const psqlTagCollection: IPsqlTag[] = [
          {
            ...psqlTag,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPsqlTagToCollectionIfMissing(psqlTagCollection, psqlTag);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PsqlTag to an array that doesn't contain it", () => {
        const psqlTag: IPsqlTag = sampleWithRequiredData;
        const psqlTagCollection: IPsqlTag[] = [sampleWithPartialData];
        expectedResult = service.addPsqlTagToCollectionIfMissing(psqlTagCollection, psqlTag);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(psqlTag);
      });

      it('should add only unique PsqlTag to an array', () => {
        const psqlTagArray: IPsqlTag[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const psqlTagCollection: IPsqlTag[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlTagToCollectionIfMissing(psqlTagCollection, ...psqlTagArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const psqlTag: IPsqlTag = sampleWithRequiredData;
        const psqlTag2: IPsqlTag = sampleWithPartialData;
        expectedResult = service.addPsqlTagToCollectionIfMissing([], psqlTag, psqlTag2);
        expect(expectedResult).toEqual([psqlTag, psqlTag2]);
      });

      it('should accept null and undefined values', () => {
        const psqlTag: IPsqlTag = sampleWithRequiredData;
        expectedResult = service.addPsqlTagToCollectionIfMissing([], null, psqlTag, undefined);
        expect(expectedResult).toEqual([psqlTag]);
      });

      it('should return initial array if no PsqlTag is added', () => {
        const psqlTagCollection: IPsqlTag[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlTagToCollectionIfMissing(psqlTagCollection, undefined, null);
        expect(expectedResult).toEqual(psqlTagCollection);
      });
    });

    describe('comparePsqlTag', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePsqlTag(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 'a284050f-e85e-4f01-a461-b9c22e586516' };
        const entity2 = null;

        const compareResult1 = service.comparePsqlTag(entity1, entity2);
        const compareResult2 = service.comparePsqlTag(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 'a284050f-e85e-4f01-a461-b9c22e586516' };
        const entity2 = { id: '2599c684-97f7-4f14-98a3-40b2c54998bc' };

        const compareResult1 = service.comparePsqlTag(entity1, entity2);
        const compareResult2 = service.comparePsqlTag(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 'a284050f-e85e-4f01-a461-b9c22e586516' };
        const entity2 = { id: 'a284050f-e85e-4f01-a461-b9c22e586516' };

        const compareResult1 = service.comparePsqlTag(entity1, entity2);
        const compareResult2 = service.comparePsqlTag(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });

  // Saathratri modification - AI search service test
  it('should perform an AI search', () => {
    const aiResults = [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }];
    service.aiSearch('hello', 20, ['nameEmbedding']).subscribe(resp => (expectedResult = resp));

    const req = httpMock.expectOne(request => request.method === 'GET' && request.url.endsWith('/ai-search'));
    expect(req.request.params.get('query')).toEqual('hello');
    expect(req.request.params.get('limit')).toEqual('20');
    expect(req.request.params.get('fields')).toEqual('nameEmbedding');
    req.flush(aiResults);

    expect(expectedResult).toEqual(aiResults);
  });
  // End Saathratri modification - AI search service test
});
