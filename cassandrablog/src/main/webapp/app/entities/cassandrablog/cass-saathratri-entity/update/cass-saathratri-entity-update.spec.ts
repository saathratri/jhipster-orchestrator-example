import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassSaathratriEntity } from '../cass-saathratri-entity.model';
import { sampleWithRequiredData } from '../cass-saathratri-entity.test-samples';
import { CassSaathratriEntityService } from '../service/cass-saathratri-entity.service';

import { CassSaathratriEntityFormService } from './cass-saathratri-entity-form.service';
import { CassSaathratriEntityUpdateComponent } from './cass-saathratri-entity-update';

describe('CassSaathratriEntity Management Update Component', () => {
  let comp: CassSaathratriEntityUpdateComponent;
  let fixture: ComponentFixture<CassSaathratriEntityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassSaathratriEntityFormService: CassSaathratriEntityFormService;
  let cassSaathratriEntityService: CassSaathratriEntityService;

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

    fixture = TestBed.createComponent(CassSaathratriEntityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassSaathratriEntityFormService = TestBed.inject(CassSaathratriEntityFormService);
    cassSaathratriEntityService = TestBed.inject(CassSaathratriEntityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassSaathratriEntity: ICassSaathratriEntity = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassSaathratriEntity });
      comp.ngOnInit();

      expect(comp.cassSaathratriEntity).toEqual(cassSaathratriEntity);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity>>();
      const cassSaathratriEntity = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntityFormService, 'getCassSaathratriEntity').mockReturnValue(cassSaathratriEntity);
      vitest.spyOn(cassSaathratriEntityService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassSaathratriEntity });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSaathratriEntity }));
      saveSubject.complete();

      // THEN
      expect(cassSaathratriEntityFormService.getCassSaathratriEntity).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassSaathratriEntityService.update).toHaveBeenCalledWith(expect.objectContaining(cassSaathratriEntity));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity>>();
      const cassSaathratriEntity = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntityFormService, 'getCassSaathratriEntity').mockReturnValue(cassSaathratriEntity);
      vitest.spyOn(cassSaathratriEntityService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassSaathratriEntity: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSaathratriEntity }));
      saveSubject.complete();

      // THEN
      expect(cassSaathratriEntityFormService.getCassSaathratriEntity).toHaveBeenCalled();
      expect(cassSaathratriEntityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSaathratriEntity>>();
      const cassSaathratriEntity = { ...sampleWithRequiredData };
      vitest.spyOn(cassSaathratriEntityService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassSaathratriEntity });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassSaathratriEntityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
