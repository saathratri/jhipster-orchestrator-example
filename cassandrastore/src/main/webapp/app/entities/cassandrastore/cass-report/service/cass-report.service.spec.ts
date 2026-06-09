import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICassReport } from '../cass-report.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cass-report.test-samples';

import { CassReportService } from './cass-report.service';

const requireRestSample: ICassReport = {
  ...sampleWithRequiredData,
};

describe('CassReport Service', () => {
  let service: CassReportService;
  let httpMock: HttpTestingController;
  let expectedResult: ICassReport | ICassReport[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CassReportService);
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

    it('should create a CassReport', () => {
      const cassReport = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cassReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CassReport', () => {
      const cassReport = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cassReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CassReport', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CassReport', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CassReport', () => {
      service.delete(sampleWithRequiredData.id).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCassReportToCollectionIfMissing', () => {
      it('should add a CassReport to an empty array', () => {
        const cassReport: ICassReport = sampleWithRequiredData;
        expectedResult = service.addCassReportToCollectionIfMissing([], cassReport);
        expect(expectedResult).toEqual([cassReport]);
      });

      it('should not add a CassReport to an array that contains it', () => {
        const cassReport: ICassReport = sampleWithRequiredData;
        const cassReportCollection: ICassReport[] = [
          {
            ...cassReport,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCassReportToCollectionIfMissing(cassReportCollection, cassReport);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CassReport to an array that doesn't contain it", () => {
        const cassReport: ICassReport = sampleWithRequiredData;
        const cassReportCollection: ICassReport[] = [sampleWithPartialData];
        expectedResult = service.addCassReportToCollectionIfMissing(cassReportCollection, cassReport);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cassReport);
      });

      it('should add only unique CassReport to an array', () => {
        const cassReportArray: ICassReport[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cassReportCollection: ICassReport[] = [sampleWithRequiredData];
        expectedResult = service.addCassReportToCollectionIfMissing(cassReportCollection, ...cassReportArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cassReport: ICassReport = sampleWithRequiredData;
        const cassReport2: ICassReport = sampleWithPartialData;
        expectedResult = service.addCassReportToCollectionIfMissing([], cassReport, cassReport2);
        expect(expectedResult).toEqual([cassReport, cassReport2]);
      });

      it('should accept null and undefined values', () => {
        const cassReport: ICassReport = sampleWithRequiredData;
        expectedResult = service.addCassReportToCollectionIfMissing([], null, cassReport, undefined);
        expect(expectedResult).toEqual([cassReport]);
      });

      it('should return initial array if no CassReport is added', () => {
        const cassReportCollection: ICassReport[] = [sampleWithRequiredData];
        expectedResult = service.addCassReportToCollectionIfMissing(cassReportCollection, undefined, null);
        expect(expectedResult).toEqual(cassReportCollection);
      });
    });

    describe('compareCassReport', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCassReport(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = null;

        const compareResult1 = service.compareCassReport(entity1, entity2);
        const compareResult2 = service.compareCassReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = { id: sampleWithPartialData.id };

        const compareResult1 = service.compareCassReport(entity1, entity2);
        const compareResult2 = service.compareCassReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { id: sampleWithRequiredData.id };
        const entity2 = { id: sampleWithRequiredData.id };

        const compareResult1 = service.compareCassReport(entity1, entity2);
        const compareResult2 = service.compareCassReport(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
