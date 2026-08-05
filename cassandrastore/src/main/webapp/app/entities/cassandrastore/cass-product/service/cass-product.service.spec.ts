import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassProduct } from '../cass-product.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cass-product.test-samples';

import { CassProductService } from './cass-product.service';

const requireRestSample: ICassProduct = {
  ...sampleWithRequiredData,
};

describe('CassProduct Service', () => {
  let service: CassProductService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassProduct | ICassProduct[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassProductService);
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

    it('should create a CassProduct', () => {
      const cassProduct = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassProduct).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassProduct', () => {
      const cassProduct = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassProduct).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassProduct', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassProduct', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassProduct', () => {
      service.delete(sampleWithRequiredData.id).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCassProductToCollectionIfMissing', () => {
      it('should add a CassProduct to an empty array', () => {
        const cassProduct: ICassProduct = sampleWithRequiredData;
        expectedResult = service.addCassProductToCollectionIfMissing([], cassProduct);
        expect(expectedResult).toEqual([cassProduct]);
      });

      it('should not add a CassProduct to an array that contains it', () => {
        const cassProduct: ICassProduct = sampleWithRequiredData;
        const cassProductCollection: ICassProduct[] = [
          {
            ...cassProduct,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassProductToCollectionIfMissing(cassProductCollection, cassProduct);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassProduct to an array that doesn't contain it", () => {
        const cassProduct: ICassProduct = sampleWithRequiredData;
        const cassProductCollection: ICassProduct[] = [sampleWithPartialData];
        expectedResult = service.addCassProductToCollectionIfMissing(cassProductCollection, cassProduct);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassProduct);
      });

      it('should add only unique CassProduct to an array', () => {
        const cassProductArray: ICassProduct[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassProductCollection: ICassProduct[] = [sampleWithRequiredData];
        expectedResult = service.addCassProductToCollectionIfMissing(cassProductCollection, ...cassProductArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassProduct: ICassProduct = sampleWithRequiredData;
        const cassProduct2: ICassProduct = sampleWithPartialData;
        expectedResult = service.addCassProductToCollectionIfMissing([], cassProduct, cassProduct2);
        expect(expectedResult).toEqual([cassProduct, cassProduct2]);
      });

      it('should accept null and undefined values', () => {
        const cassProduct: ICassProduct = sampleWithRequiredData;
        expectedResult = service.addCassProductToCollectionIfMissing([], null, cassProduct, undefined);
        expect(expectedResult).toEqual([cassProduct]);
      });

      it('should return initial array if no CassProduct is added', () => {
        const cassProductCollection: ICassProduct[] = [sampleWithRequiredData];
        expectedResult = service.addCassProductToCollectionIfMissing(cassProductCollection, undefined, null);
        expect(expectedResult).toEqual(cassProductCollection);
      });
    });

    describe('compareCassProduct', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassProduct(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = null;

        const compareResult1 = service.compareCassProduct(entity1, entity2);
        const compareResult2 = service.compareCassProduct(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = { id: sampleWithPartialData.id };

        const compareResult1 = service.compareCassProduct(entity1, entity2);
        const compareResult2 = service.compareCassProduct(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = { id: sampleWithRequiredData.id };

        const compareResult1 = service.compareCassProduct(entity1, entity2);
        const compareResult2 = service.compareCassProduct(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
