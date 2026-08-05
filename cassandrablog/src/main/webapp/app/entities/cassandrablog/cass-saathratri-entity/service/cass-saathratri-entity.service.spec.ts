import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassSaathratriEntity } from '../cass-saathratri-entity.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../cass-saathratri-entity.test-samples';

import { CassSaathratriEntityService } from './cass-saathratri-entity.service';

const requireRestSample: ICassSaathratriEntity = {
  ...sampleWithRequiredData,
};

describe('CassSaathratriEntity Service', () => {
  let service: CassSaathratriEntityService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassSaathratriEntity | ICassSaathratriEntity[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassSaathratriEntityService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('entityId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CassSaathratriEntity', () => {
      const cassSaathratriEntity = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassSaathratriEntity).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassSaathratriEntity', () => {
      const cassSaathratriEntity = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassSaathratriEntity).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassSaathratriEntity', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassSaathratriEntity', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassSaathratriEntity', () => {
      service.delete(sampleWithRequiredData.entityId).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCassSaathratriEntityToCollectionIfMissing', () => {
      it('should add a CassSaathratriEntity to an empty array', () => {
        const cassSaathratriEntity: ICassSaathratriEntity = sampleWithRequiredData;
        expectedResult = service.addCassSaathratriEntityToCollectionIfMissing([], cassSaathratriEntity);
        expect(expectedResult).toEqual([cassSaathratriEntity]);
      });

      it('should not add a CassSaathratriEntity to an array that contains it', () => {
        const cassSaathratriEntity: ICassSaathratriEntity = sampleWithRequiredData;
        const cassSaathratriEntityCollection: ICassSaathratriEntity[] = [
          {
            ...cassSaathratriEntity,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassSaathratriEntityToCollectionIfMissing(cassSaathratriEntityCollection, cassSaathratriEntity);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassSaathratriEntity to an array that doesn't contain it", () => {
        const cassSaathratriEntity: ICassSaathratriEntity = sampleWithRequiredData;
        const cassSaathratriEntityCollection: ICassSaathratriEntity[] = [sampleWithPartialData];
        expectedResult = service.addCassSaathratriEntityToCollectionIfMissing(cassSaathratriEntityCollection, cassSaathratriEntity);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassSaathratriEntity);
      });

      it('should add only unique CassSaathratriEntity to an array', () => {
        const cassSaathratriEntityArray: ICassSaathratriEntity[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassSaathratriEntityCollection: ICassSaathratriEntity[] = [sampleWithRequiredData];
        expectedResult = service.addCassSaathratriEntityToCollectionIfMissing(cassSaathratriEntityCollection, ...cassSaathratriEntityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassSaathratriEntity: ICassSaathratriEntity = sampleWithRequiredData;
        const cassSaathratriEntity2: ICassSaathratriEntity = sampleWithPartialData;
        expectedResult = service.addCassSaathratriEntityToCollectionIfMissing([], cassSaathratriEntity, cassSaathratriEntity2);
        expect(expectedResult).toEqual([cassSaathratriEntity, cassSaathratriEntity2]);
      });

      it('should accept null and undefined values', () => {
        const cassSaathratriEntity: ICassSaathratriEntity = sampleWithRequiredData;
        expectedResult = service.addCassSaathratriEntityToCollectionIfMissing([], null, cassSaathratriEntity, undefined);
        expect(expectedResult).toEqual([cassSaathratriEntity]);
      });

      it('should return initial array if no CassSaathratriEntity is added', () => {
        const cassSaathratriEntityCollection: ICassSaathratriEntity[] = [sampleWithRequiredData];
        expectedResult = service.addCassSaathratriEntityToCollectionIfMissing(cassSaathratriEntityCollection, undefined, null);
        expect(expectedResult).toEqual(cassSaathratriEntityCollection);
      });
    });

    describe('compareCassSaathratriEntity', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassSaathratriEntity(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { entityId: sampleWithRequiredData.entityId };
        const entity2 = null;

        const compareResult1 = service.compareCassSaathratriEntity(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { entityId: sampleWithRequiredData.entityId };
        const entity2 = { entityId: sampleWithPartialData.entityId };

        const compareResult1 = service.compareCassSaathratriEntity(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { entityId: sampleWithRequiredData.entityId };
        const entity2 = { entityId: sampleWithRequiredData.entityId };

        const compareResult1 = service.compareCassSaathratriEntity(entity1, entity2);
        const compareResult2 = service.compareCassSaathratriEntity(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
