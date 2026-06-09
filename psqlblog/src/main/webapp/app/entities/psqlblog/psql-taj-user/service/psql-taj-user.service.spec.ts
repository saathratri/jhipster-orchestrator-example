import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IPsqlTajUser } from '../psql-taj-user.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../psql-taj-user.test-samples';

import { PsqlTajUserService } from './psql-taj-user.service';

const requireRestSample: IPsqlTajUser = {
  ...sampleWithRequiredData,
};

describe('PsqlTajUser Service', () => {
  let service: PsqlTajUserService;
  let httpMock: HttpTestingController;
  let expectedResult: IPsqlTajUser | IPsqlTajUser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PsqlTajUserService);
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

    it('should create a PsqlTajUser', () => {
      const psqlTajUser = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(psqlTajUser).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PsqlTajUser', () => {
      const psqlTajUser = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(psqlTajUser).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PsqlTajUser', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PsqlTajUser', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PsqlTajUser', () => {
      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addPsqlTajUserToCollectionIfMissing', () => {
      it('should add a PsqlTajUser to an empty array', () => {
        const psqlTajUser: IPsqlTajUser = sampleWithRequiredData;
        expectedResult = service.addPsqlTajUserToCollectionIfMissing([], psqlTajUser);
        expect(expectedResult).toEqual([psqlTajUser]);
      });

      it('should not add a PsqlTajUser to an array that contains it', () => {
        const psqlTajUser: IPsqlTajUser = sampleWithRequiredData;
        const psqlTajUserCollection: IPsqlTajUser[] = [
          {
            ...psqlTajUser,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPsqlTajUserToCollectionIfMissing(psqlTajUserCollection, psqlTajUser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PsqlTajUser to an array that doesn't contain it", () => {
        const psqlTajUser: IPsqlTajUser = sampleWithRequiredData;
        const psqlTajUserCollection: IPsqlTajUser[] = [sampleWithPartialData];
        expectedResult = service.addPsqlTajUserToCollectionIfMissing(psqlTajUserCollection, psqlTajUser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(psqlTajUser);
      });

      it('should add only unique PsqlTajUser to an array', () => {
        const psqlTajUserArray: IPsqlTajUser[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const psqlTajUserCollection: IPsqlTajUser[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlTajUserToCollectionIfMissing(psqlTajUserCollection, ...psqlTajUserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const psqlTajUser: IPsqlTajUser = sampleWithRequiredData;
        const psqlTajUser2: IPsqlTajUser = sampleWithPartialData;
        expectedResult = service.addPsqlTajUserToCollectionIfMissing([], psqlTajUser, psqlTajUser2);
        expect(expectedResult).toEqual([psqlTajUser, psqlTajUser2]);
      });

      it('should accept null and undefined values', () => {
        const psqlTajUser: IPsqlTajUser = sampleWithRequiredData;
        expectedResult = service.addPsqlTajUserToCollectionIfMissing([], null, psqlTajUser, undefined);
        expect(expectedResult).toEqual([psqlTajUser]);
      });

      it('should return initial array if no PsqlTajUser is added', () => {
        const psqlTajUserCollection: IPsqlTajUser[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlTajUserToCollectionIfMissing(psqlTajUserCollection, undefined, null);
        expect(expectedResult).toEqual(psqlTajUserCollection);
      });
    });

    describe('comparePsqlTajUser', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePsqlTajUser(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };
        const entity2 = null;

        const compareResult1 = service.comparePsqlTajUser(entity1, entity2);
        const compareResult2 = service.comparePsqlTajUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };
        const entity2 = { id: '5d007a04-df5d-4ff8-b846-f9bf5f07ee94' };

        const compareResult1 = service.comparePsqlTajUser(entity1, entity2);
        const compareResult2 = service.comparePsqlTajUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };
        const entity2 = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };

        const compareResult1 = service.comparePsqlTajUser(entity1, entity2);
        const compareResult2 = service.comparePsqlTajUser(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
