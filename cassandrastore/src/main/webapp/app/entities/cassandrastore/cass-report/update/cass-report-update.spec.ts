import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassReport } from '../cass-report.model';
import { sampleWithRequiredData } from '../cass-report.test-samples';
import { CassReportService } from '../service/cass-report.service';

import { CassReportFormService } from './cass-report-form.service';
import { CassReportUpdateComponent } from './cass-report-update';

describe('CassReport Management Update Component', () => {
  let comp: CassReportUpdateComponent;
  let fixture: ComponentFixture<CassReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassReportFormService: CassReportFormService;
  let cassReportService: CassReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
            // ngOnInit reads snapshot.routeConfig?.path to decide isNew
            snapshot: { routeConfig: { path: '' } },
          },
        },
      ],
    });

    fixture = TestBed.createComponent(CassReportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassReportFormService = TestBed.inject(CassReportFormService);
    cassReportService = TestBed.inject(CassReportService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassReport: ICassReport = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassReport });
      comp.ngOnInit();

      expect(comp.cassReport).toEqual(cassReport);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassReport>>();
      const cassReport = { ...sampleWithRequiredData };
      vitest.spyOn(cassReportFormService, 'getCassReport').mockReturnValue(cassReport);
      vitest.spyOn(cassReportService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassReport }));
      saveSubject.complete();

      // THEN
      expect(cassReportFormService.getCassReport).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassReportService.update).toHaveBeenCalledWith(expect.objectContaining(cassReport));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassReport>>();
      const cassReport = { ...sampleWithRequiredData };
      vitest.spyOn(cassReportFormService, 'getCassReport').mockReturnValue(cassReport);
      vitest.spyOn(cassReportService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassReport: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassReport }));
      saveSubject.complete();

      // THEN
      expect(cassReportFormService.getCassReport).toHaveBeenCalled();
      expect(cassReportService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassReport>>();
      const cassReport = { ...sampleWithRequiredData };
      vitest.spyOn(cassReportService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassReportService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
