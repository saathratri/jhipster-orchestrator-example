import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassSaathratriEntity3 } from '../cass-saathratri-entity-3.model';
import { sampleWithRequiredData } from '../cass-saathratri-entity-3.test-samples';
import { CassSaathratriEntity3Service } from '../service/cass-saathratri-entity-3.service';

import { CassSaathratriEntity3FormService } from './cass-saathratri-entity-3-form.service';
import { CassSaathratriEntity3UpdateComponent } from './cass-saathratri-entity-3-update';

describe('CassSaathratriEntity3 Management Update Component', () => {
  let comp: CassSaathratriEntity3UpdateComponent;
  let fixture: ComponentFixture<CassSaathratriEntity3UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassSaathratriEntity3FormService: CassSaathratriEntity3FormService;
  let cassSaathratriEntity3Service: CassSaathratriEntity3Service;

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

    fixture = TestBed.createComponent(CassSaathratriEntity3UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassSaathratriEntity3FormService = TestBed.inject(CassSaathratriEntity3FormService);
    cassSaathratriEntity3Service = TestBed.inject(CassSaathratriEntity3Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassSaathratriEntity3: ICassSaathratriEntity3 = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassSaathratriEntity3 });
      comp.ngOnInit();

      expect(comp.cassSaathratriEntity3).toEqual(cassSaathratriEntity3);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity3>>();
      const cassSaathratriEntity3 = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntity3FormService, 'getCassSaathratriEntity3').mockReturnValue(cassSaathratriEntity3);
      vitest.spyOn(cassSaathratriEntity3Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassSaathratriEntity3 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSaathratriEntity3 }));
      saveSubject.complete();

      // THEN
      expect(cassSaathratriEntity3FormService.getCassSaathratriEntity3).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassSaathratriEntity3Service.update).toHaveBeenCalledWith(expect.objectContaining(cassSaathratriEntity3));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity3>>();
      const cassSaathratriEntity3 = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntity3FormService, 'getCassSaathratriEntity3').mockReturnValue(cassSaathratriEntity3);
      vitest.spyOn(cassSaathratriEntity3Service, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassSaathratriEntity3: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSaathratriEntity3 }));
      saveSubject.complete();

      // THEN
      expect(cassSaathratriEntity3FormService.getCassSaathratriEntity3).toHaveBeenCalled();
      expect(cassSaathratriEntity3Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity3>>();
      const cassSaathratriEntity3 = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntity3Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassSaathratriEntity3 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassSaathratriEntity3Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
