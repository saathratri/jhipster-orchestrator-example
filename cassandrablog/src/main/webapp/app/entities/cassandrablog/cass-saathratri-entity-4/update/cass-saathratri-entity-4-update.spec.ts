import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassSaathratriEntity4 } from '../cass-saathratri-entity-4.model';
import { sampleWithRequiredData } from '../cass-saathratri-entity-4.test-samples';
import { CassSaathratriEntity4Service } from '../service/cass-saathratri-entity-4.service';

import { CassSaathratriEntity4FormService } from './cass-saathratri-entity-4-form.service';
import { CassSaathratriEntity4UpdateComponent } from './cass-saathratri-entity-4-update';

describe('CassSaathratriEntity4 Management Update Component', () => {
  let comp: CassSaathratriEntity4UpdateComponent;
  let fixture: ComponentFixture<CassSaathratriEntity4UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassSaathratriEntity4FormService: CassSaathratriEntity4FormService;
  let cassSaathratriEntity4Service: CassSaathratriEntity4Service;

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

    fixture = TestBed.createComponent(CassSaathratriEntity4UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassSaathratriEntity4FormService = TestBed.inject(CassSaathratriEntity4FormService);
    cassSaathratriEntity4Service = TestBed.inject(CassSaathratriEntity4Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassSaathratriEntity4: ICassSaathratriEntity4 = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassSaathratriEntity4 });
      comp.ngOnInit();

      expect(comp.cassSaathratriEntity4).toEqual(cassSaathratriEntity4);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity4>>();
      const cassSaathratriEntity4 = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntity4FormService, 'getCassSaathratriEntity4').mockReturnValue(cassSaathratriEntity4);
      vitest.spyOn(cassSaathratriEntity4Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassSaathratriEntity4 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSaathratriEntity4 }));
      saveSubject.complete();

      // THEN
      expect(cassSaathratriEntity4FormService.getCassSaathratriEntity4).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassSaathratriEntity4Service.update).toHaveBeenCalledWith(expect.objectContaining(cassSaathratriEntity4));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity4>>();
      const cassSaathratriEntity4 = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntity4FormService, 'getCassSaathratriEntity4').mockReturnValue(cassSaathratriEntity4);
      vitest.spyOn(cassSaathratriEntity4Service, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassSaathratriEntity4: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSaathratriEntity4 }));
      saveSubject.complete();

      // THEN
      expect(cassSaathratriEntity4FormService.getCassSaathratriEntity4).toHaveBeenCalled();
      expect(cassSaathratriEntity4Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity4>>();
      const cassSaathratriEntity4 = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntity4Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassSaathratriEntity4 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassSaathratriEntity4Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
