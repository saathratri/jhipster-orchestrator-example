import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassTajUser } from '../cass-taj-user.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cass-taj-user.test-samples';

import { CassTajUserService } from './cass-taj-user.service';

const requireRestSample: ICassTajUser = {
  ...sampleWithRequiredData,
};

describe('CassTajUser Service', () => {
  let service: CassTajUserService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassTajUser | ICassTajUser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassTajUserService);
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

    it('should create a CassTajUser', () => {
      const cassTajUser = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassTajUser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassTajUser', () => {
      const cassTajUser = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassTajUser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassTajUser', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassTajUser', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassTajUser', () => {
      service.delete(sampleWithRequiredData.id).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCassTajUserToCollectionIfMissing', () => {
      it('should add a CassTajUser to an empty array', () => {
        const cassTajUser: ICassTajUser = sampleWithRequiredData;
        expectedResult = service.addCassTajUserToCollectionIfMissing([], cassTajUser);
        expect(expectedResult).toEqual([cassTajUser]);
      });

      it('should not add a CassTajUser to an array that contains it', () => {
        const cassTajUser: ICassTajUser = sampleWithRequiredData;
        const cassTajUserCollection: ICassTajUser[] = [
          {
            ...cassTajUser,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassTajUserToCollectionIfMissing(cassTajUserCollection, cassTajUser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassTajUser to an array that doesn't contain it", () => {
        const cassTajUser: ICassTajUser = sampleWithRequiredData;
        const cassTajUserCollection: ICassTajUser[] = [sampleWithPartialData];
        expectedResult = service.addCassTajUserToCollectionIfMissing(cassTajUserCollection, cassTajUser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassTajUser);
      });

      it('should add only unique CassTajUser to an array', () => {
        const cassTajUserArray: ICassTajUser[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassTajUserCollection: ICassTajUser[] = [sampleWithRequiredData];
        expectedResult = service.addCassTajUserToCollectionIfMissing(cassTajUserCollection, ...cassTajUserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassTajUser: ICassTajUser = sampleWithRequiredData;
        const cassTajUser2: ICassTajUser = sampleWithPartialData;
        expectedResult = service.addCassTajUserToCollectionIfMissing([], cassTajUser, cassTajUser2);
        expect(expectedResult).toEqual([cassTajUser, cassTajUser2]);
      });

      it('should accept null and undefined values', () => {
        const cassTajUser: ICassTajUser = sampleWithRequiredData;
        expectedResult = service.addCassTajUserToCollectionIfMissing([], null, cassTajUser, undefined);
        expect(expectedResult).toEqual([cassTajUser]);
      });

      it('should return initial array if no CassTajUser is added', () => {
        const cassTajUserCollection: ICassTajUser[] = [sampleWithRequiredData];
        expectedResult = service.addCassTajUserToCollectionIfMissing(cassTajUserCollection, undefined, null);
        expect(expectedResult).toEqual(cassTajUserCollection);
      });
    });

    describe('compareCassTajUser', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassTajUser(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = null;

        const compareResult1 = service.compareCassTajUser(entity1, entity2);
        const compareResult2 = service.compareCassTajUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = { id: sampleWithPartialData.id };

        const compareResult1 = service.compareCassTajUser(entity1, entity2);
        const compareResult2 = service.compareCassTajUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = { id: sampleWithRequiredData.id };

        const compareResult1 = service.compareCassTajUser(entity1, entity2);
        const compareResult2 = service.compareCassTajUser(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
