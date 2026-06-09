import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IPsqlReport } from '../psql-report.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../psql-report.test-samples';

import { PsqlReportService, RestPsqlReport } from './psql-report.service';

const requireRestSample: RestPsqlReport = {
  ...sampleWithRequiredData,
  createDate: sampleWithRequiredData.createDate?.toJSON(),
};

describe('PsqlReport Service', () => {
  let service: PsqlReportService;
  let httpMock: HttpTestingController;
  let expectedResult: IPsqlReport | IPsqlReport[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PsqlReportService);
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

    it('should create a PsqlReport', () => {
      const psqlReport = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(psqlReport).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PsqlReport', () => {
      const psqlReport = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(psqlReport).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PsqlReport', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PsqlReport', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PsqlReport', () => {
      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addPsqlReportToCollectionIfMissing', () => {
      it('should add a PsqlReport to an empty array', () => {
        const psqlReport: IPsqlReport = sampleWithRequiredData;
        expectedResult = service.addPsqlReportToCollectionIfMissing([], psqlReport);
        expect(expectedResult).toEqual([psqlReport]);
      });

      it('should not add a PsqlReport to an array that contains it', () => {
        const psqlReport: IPsqlReport = sampleWithRequiredData;
        const psqlReportCollection: IPsqlReport[] = [
          {
            ...psqlReport,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPsqlReportToCollectionIfMissing(psqlReportCollection, psqlReport);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PsqlReport to an array that doesn't contain it", () => {
        const psqlReport: IPsqlReport = sampleWithRequiredData;
        const psqlReportCollection: IPsqlReport[] = [sampleWithPartialData];
        expectedResult = service.addPsqlReportToCollectionIfMissing(psqlReportCollection, psqlReport);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(psqlReport);
      });

      it('should add only unique PsqlReport to an array', () => {
        const psqlReportArray: IPsqlReport[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const psqlReportCollection: IPsqlReport[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlReportToCollectionIfMissing(psqlReportCollection, ...psqlReportArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const psqlReport: IPsqlReport = sampleWithRequiredData;
        const psqlReport2: IPsqlReport = sampleWithPartialData;
        expectedResult = service.addPsqlReportToCollectionIfMissing([], psqlReport, psqlReport2);
        expect(expectedResult).toEqual([psqlReport, psqlReport2]);
      });

      it('should accept null and undefined values', () => {
        const psqlReport: IPsqlReport = sampleWithRequiredData;
        expectedResult = service.addPsqlReportToCollectionIfMissing([], null, psqlReport, undefined);
        expect(expectedResult).toEqual([psqlReport]);
      });

      it('should return initial array if no PsqlReport is added', () => {
        const psqlReportCollection: IPsqlReport[] = [sampleWithRequiredData];
        expectedResult = service.addPsqlReportToCollectionIfMissing(psqlReportCollection, undefined, null);
        expect(expectedResult).toEqual(psqlReportCollection);
      });
    });

    describe('comparePsqlReport', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePsqlReport(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: '5859172c-f653-437a-85a1-f103f3f9c4e0' };
        const entity2 = null;

        const compareResult1 = service.comparePsqlReport(entity1, entity2);
        const compareResult2 = service.comparePsqlReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: '5859172c-f653-437a-85a1-f103f3f9c4e0' };
        const entity2 = { id: '31118f7e-ce9a-48b1-835c-2c680c67a6da' };

        const compareResult1 = service.comparePsqlReport(entity1, entity2);
        const compareResult2 = service.comparePsqlReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: '5859172c-f653-437a-85a1-f103f3f9c4e0' };
        const entity2 = { id: '5859172c-f653-437a-85a1-f103f3f9c4e0' };

        const compareResult1 = service.comparePsqlReport(entity1, entity2);
        const compareResult2 = service.comparePsqlReport(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
