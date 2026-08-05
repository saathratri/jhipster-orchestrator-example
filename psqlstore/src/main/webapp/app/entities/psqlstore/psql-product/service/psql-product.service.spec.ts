import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IPsqlProduct } from '../psql-product.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../psql-product.test-samples';

import { PsqlProductService } from './psql-product.service';

const requireRestSample: IPsqlProduct = {
  ...sampleWithRequiredData,
};

describe('PsqlProduct Service', () => {
  let service: PsqlProductService;
  let httpMock: HttpTestingController;
  let expectedResult: IPsqlProduct | IPsqlProduct[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PsqlProductService);
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

    it('should create a PsqlProduct', () => {
      const psqlProduct = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(psqlProduct).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PsqlProduct', () => {
      const psqlProduct = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(psqlProduct).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PsqlProduct', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PsqlProduct', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PsqlProduct', () => {
      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addPsqlProductToCollectionIfMissing', () => {
      it('should add a PsqlProduct to an empty array', () => {
        const psqlProduct: IPsqlProduct = sampleWithRequiredData;
        expectedResult = service.addPsqlProductToCollectionIfMissing([], psqlProduct);
        expect(expectedResult).toEqual([psqlProduct]);
      });

      it('should not add a PsqlProduct to an array that contains it', () => {
        const psqlProduct: IPsqlProduct = sampleWithRequiredData;
        const psqlProductCollection: IPsqlProduct[] = [
          {
            ...psqlProduct,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPsqlProductToCollectionIfMissing(psqlProductCollection, psqlProduct);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PsqlProduct to an array that doesn't contain it", () => {
        const psqlProduct: IPsqlProduct = sampleWithRequiredData;
        const psqlProductCollection: IPsqlProduct[] = [sampleWithPartialData];
        expectedResult = service.addPsqlProductToCollectionIfMissing(psqlProductCollection, psqlProduct);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(psqlProduct);
      });

      it('should add only unique PsqlProduct to an array', () => {
        const psqlProductArray: IPsqlProduct[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const psqlProductCollection: IPsqlProduct[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlProductToCollectionIfMissing(psqlProductCollection, ...psqlProductArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const psqlProduct: IPsqlProduct = sampleWithRequiredData;
        const psqlProduct2: IPsqlProduct = sampleWithPartialData;
        expectedResult = service.addPsqlProductToCollectionIfMissing([], psqlProduct, psqlProduct2);
        expect(expectedResult).toEqual([psqlProduct, psqlProduct2]);
      });

      it('should accept null and undefined values', () => {
        const psqlProduct: IPsqlProduct = sampleWithRequiredData;
        expectedResult = service.addPsqlProductToCollectionIfMissing([], null, psqlProduct, undefined);
        expect(expectedResult).toEqual([psqlProduct]);
      });

      it('should return initial array if no PsqlProduct is added', () => {
        const psqlProductCollection: IPsqlProduct[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlProductToCollectionIfMissing(psqlProductCollection, undefined, null);
        expect(expectedResult).toEqual(psqlProductCollection);
      });
    });

    describe('comparePsqlProduct', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePsqlProduct(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };
        const entity2 = null;

        const compareResult1 = service.comparePsqlProduct(entity1, entity2);
        const compareResult2 = service.comparePsqlProduct(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };
        const entity2 = { id: '4827651f-81f9-4588-b671-f48e6bc9ce0b' };

        const compareResult1 = service.comparePsqlProduct(entity1, entity2);
        const compareResult2 = service.comparePsqlProduct(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };
        const entity2 = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };

        const compareResult1 = service.comparePsqlProduct(entity1, entity2);
        const compareResult2 = service.comparePsqlProduct(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
