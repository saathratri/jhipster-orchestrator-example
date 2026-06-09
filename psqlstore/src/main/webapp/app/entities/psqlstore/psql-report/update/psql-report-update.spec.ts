import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IPsqlProduct } from 'app/entities/psqlstore/psql-product/psql-product.model';
import { PsqlProductService } from 'app/entities/psqlstore/psql-product/service/psql-product.service';
import { IPsqlReport } from '../psql-report.model';
import { PsqlReportService } from '../service/psql-report.service';

import { PsqlReportFormService } from './psql-report-form.service';
import { PsqlReportUpdate } from './psql-report-update';

describe('PsqlReport Management Update Component', () => {
  let comp: PsqlReportUpdate;
  let fixture: ComponentFixture<PsqlReportUpdate>;
  let activatedRoute: ActivatedRoute;
  let psqlReportFormService: PsqlReportFormService;
  let psqlReportService: PsqlReportService;
  let psqlProductService: PsqlProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    });

    fixture = TestBed.createComponent(PsqlReportUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    psqlReportFormService = TestBed.inject(PsqlReportFormService);
    psqlReportService = TestBed.inject(PsqlReportService);
    psqlProductService = TestBed.inject(PsqlProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call PsqlProduct query and add missing value', () => {
      const psqlReport: IPsqlReport = { id: '31118f7e-ce9a-48b1-835c-2c680c67a6da' };
      const product: IPsqlProduct = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };
      psqlReport.product = product;

      const psqlProductCollection: IPsqlProduct[] = [{ id: '27c299d6-3e62-4e45-b862-4c6b629af87a' }];
      vitest.spyOn(psqlProductService, 'query').mockReturnValue(of(new HttpResponse({ body: psqlProductCollection })));
      const additionalPsqlProducts = [product];
      const expectedCollection: IPsqlProduct[] = [...additionalPsqlProducts, ...psqlProductCollection];
      vitest.spyOn(psqlProductService, 'addPsqlProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ psqlReport });
      comp.ngOnInit();

      expect(psqlProductService.query).toHaveBeenCalled();
      expect(psqlProductService.addPsqlProductToCollectionIfMissing).toHaveBeenCalledWith(
        psqlProductCollection,
        ...additionalPsqlProducts.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.psqlProductsSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const psqlReport: IPsqlReport = { id: '31118f7e-ce9a-48b1-835c-2c680c67a6da' };
      const product: IPsqlProduct = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };
      psqlReport.product = product;

      activatedRoute.data = of({ psqlReport });
      comp.ngOnInit();

      expect(comp.psqlProductsSharedCollection()).toContainEqual(product);
      expect(comp.psqlReport).toEqual(psqlReport);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlReport>();
      const psqlReport = { id: '5859172c-f653-437a-85a1-f103f3f9c4e0' };
      vitest.spyOn(psqlReportFormService, 'getPsqlReport').mockReturnValue(psqlReport);
      vitest.spyOn(psqlReportService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlReport);
      saveSubject.complete();

      // THEN
      expect(psqlReportFormService.getPsqlReport).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(psqlReportService.update).toHaveBeenCalledWith(expect.objectContaining(psqlReport));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlReport>();
      const psqlReport = { id: '5859172c-f653-437a-85a1-f103f3f9c4e0' };
      vitest.spyOn(psqlReportFormService, 'getPsqlReport').mockReturnValue({ id: null });
      vitest.spyOn(psqlReportService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlReport: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlReport);
      saveSubject.complete();

      // THEN
      expect(psqlReportFormService.getPsqlReport).toHaveBeenCalled();
      expect(psqlReportService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlReport>();
      const psqlReport = { id: '5859172c-f653-437a-85a1-f103f3f9c4e0' };
      vitest.spyOn(psqlReportService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(psqlReportService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePsqlProduct', () => {
      it('should forward to psqlProductService', () => {
        const entity = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };
        const entity2 = { id: '4827651f-81f9-4588-b671-f48e6bc9ce0b' };
        vitest.spyOn(psqlProductService, 'comparePsqlProduct');
        comp.comparePsqlProduct(entity, entity2);
        expect(psqlProductService.comparePsqlProduct).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
