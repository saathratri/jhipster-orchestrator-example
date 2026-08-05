import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassSaathratriEntity2 } from '../cass-saathratri-entity-2.model';
import { sampleWithRequiredData } from '../cass-saathratri-entity-2.test-samples';
import { CassSaathratriEntity2Service } from '../service/cass-saathratri-entity-2.service';

import { CassSaathratriEntity2FormService } from './cass-saathratri-entity-2-form.service';
import { CassSaathratriEntity2UpdateComponent } from './cass-saathratri-entity-2-update';

describe('CassSaathratriEntity2 Management Update Component', () => {
  let comp: CassSaathratriEntity2UpdateComponent;
  let fixture: ComponentFixture<CassSaathratriEntity2UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassSaathratriEntity2FormService: CassSaathratriEntity2FormService;
  let cassSaathratriEntity2Service: CassSaathratriEntity2Service;

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

    fixture = TestBed.createComponent(CassSaathratriEntity2UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassSaathratriEntity2FormService = TestBed.inject(CassSaathratriEntity2FormService);
    cassSaathratriEntity2Service = TestBed.inject(CassSaathratriEntity2Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassSaathratriEntity2: ICassSaathratriEntity2 = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassSaathratriEntity2 });
      comp.ngOnInit();

      expect(comp.cassSaathratriEntity2).toEqual(cassSaathratriEntity2);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity2>>();
      const cassSaathratriEntity2 = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntity2FormService, 'getCassSaathratriEntity2').mockReturnValue(cassSaathratriEntity2);
      vitest.spyOn(cassSaathratriEntity2Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassSaathratriEntity2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSaathratriEntity2 }));
      saveSubject.complete();

      // THEN
      expect(cassSaathratriEntity2FormService.getCassSaathratriEntity2).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassSaathratriEntity2Service.update).toHaveBeenCalledWith(expect.objectContaining(cassSaathratriEntity2));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity2>>();
      const cassSaathratriEntity2 = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntity2FormService, 'getCassSaathratriEntity2').mockReturnValue(cassSaathratriEntity2);
      vitest.spyOn(cassSaathratriEntity2Service, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassSaathratriEntity2: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSaathratriEntity2 }));
      saveSubject.complete();

      // THEN
      expect(cassSaathratriEntity2FormService.getCassSaathratriEntity2).toHaveBeenCalled();
      expect(cassSaathratriEntity2Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity2>>();
      const cassSaathratriEntity2 = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntity2Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassSaathratriEntity2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassSaathratriEntity2Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
