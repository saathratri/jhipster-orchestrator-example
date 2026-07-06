import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassPost } from '../cass-post.model';
import { sampleWithRequiredData } from '../cass-post.test-samples';
import { CassPostService } from '../service/cass-post.service';

import { CassPostFormService } from './cass-post-form.service';
import { CassPostUpdateComponent } from './cass-post-update';

describe('CassPost Management Update Component', () => {
  let comp: CassPostUpdateComponent;
  let fixture: ComponentFixture<CassPostUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassPostFormService: CassPostFormService;
  let cassPostService: CassPostService;

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

    fixture = TestBed.createComponent(CassPostUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassPostFormService = TestBed.inject(CassPostFormService);
    cassPostService = TestBed.inject(CassPostService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassPost: ICassPost = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassPost });
      comp.ngOnInit();

      expect(comp.cassPost).toEqual(cassPost);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassPost>>();
      const cassPost = { ...sampleWithRequiredData };
      vitest.spyOn(cassPostFormService, 'getCassPost').mockReturnValue(cassPost);
      vitest.spyOn(cassPostService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassPost });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassPost }));
      saveSubject.complete();

      // THEN
      expect(cassPostFormService.getCassPost).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassPostService.update).toHaveBeenCalledWith(expect.objectContaining(cassPost));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassPost>>();
      const cassPost = { ...sampleWithRequiredData };
      vitest.spyOn(cassPostFormService, 'getCassPost').mockReturnValue(cassPost);
      vitest.spyOn(cassPostService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassPost: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassPost }));
      saveSubject.complete();

      // THEN
      expect(cassPostFormService.getCassPost).toHaveBeenCalled();
      expect(cassPostService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassPost>>();
      const cassPost = { ...sampleWithRequiredData };
      vitest.spyOn(cassPostService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassPost });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassPostService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
