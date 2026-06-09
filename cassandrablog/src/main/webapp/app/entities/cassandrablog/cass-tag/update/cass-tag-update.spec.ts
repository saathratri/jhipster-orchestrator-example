import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassTag } from '../cass-tag.model';
import { sampleWithRequiredData } from '../cass-tag.test-samples';
import { CassTagService } from '../service/cass-tag.service';

import { CassTagFormService } from './cass-tag-form.service';
import { CassTagUpdateComponent } from './cass-tag-update';

describe('CassTag Management Update Component', () => {
  let comp: CassTagUpdateComponent;
  let fixture: ComponentFixture<CassTagUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassTagFormService: CassTagFormService;
  let cassTagService: CassTagService;

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

    fixture = TestBed.createComponent(CassTagUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassTagFormService = TestBed.inject(CassTagFormService);
    cassTagService = TestBed.inject(CassTagService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassTag: ICassTag = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassTag });
      comp.ngOnInit();

      expect(comp.cassTag).toEqual(cassTag);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassTag>>();
      const cassTag = { ...sampleWithRequiredData };
      vitest.spyOn(cassTagFormService, 'getCassTag').mockReturnValue(cassTag);
      vitest.spyOn(cassTagService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassTag });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassTag }));
      saveSubject.complete();

      // THEN
      expect(cassTagFormService.getCassTag).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassTagService.update).toHaveBeenCalledWith(expect.objectContaining(cassTag));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassTag>>();
      const cassTag = { ...sampleWithRequiredData };
      vitest.spyOn(cassTagFormService, 'getCassTag').mockReturnValue(cassTag);
      vitest.spyOn(cassTagService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassTag: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassTag }));
      saveSubject.complete();

      // THEN
      expect(cassTagFormService.getCassTag).toHaveBeenCalled();
      expect(cassTagService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassTag>>();
      const cassTag = { ...sampleWithRequiredData };
      vitest.spyOn(cassTagService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassTag });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassTagService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
