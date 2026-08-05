import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassBlog } from '../cass-blog.model';
import { sampleWithRequiredData } from '../cass-blog.test-samples';
import { CassBlogService } from '../service/cass-blog.service';

import { CassBlogFormService } from './cass-blog-form.service';
import { CassBlogUpdateComponent } from './cass-blog-update';

describe('CassBlog Management Update Component', () => {
  let comp: CassBlogUpdateComponent;
  let fixture: ComponentFixture<CassBlogUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassBlogFormService: CassBlogFormService;
  let cassBlogService: CassBlogService;

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

    fixture = TestBed.createComponent(CassBlogUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassBlogFormService = TestBed.inject(CassBlogFormService);
    cassBlogService = TestBed.inject(CassBlogService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassBlog: ICassBlog = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassBlog });
      comp.ngOnInit();

      expect(comp.cassBlog).toEqual(cassBlog);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassBlog>>();
      const cassBlog = { ...sampleWithRequiredData };
      vitest.spyOn(cassBlogFormService, 'getCassBlog').mockReturnValue(cassBlog);
      vitest.spyOn(cassBlogService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassBlog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassBlog }));
      saveSubject.complete();

      // THEN
      expect(cassBlogFormService.getCassBlog).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassBlogService.update).toHaveBeenCalledWith(expect.objectContaining(cassBlog));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassBlog>>();
      const cassBlog = { ...sampleWithRequiredData };
      vitest.spyOn(cassBlogFormService, 'getCassBlog').mockReturnValue(cassBlog);
      vitest.spyOn(cassBlogService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassBlog: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassBlog }));
      saveSubject.complete();

      // THEN
      expect(cassBlogFormService.getCassBlog).toHaveBeenCalled();
      expect(cassBlogService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassBlog>>();
      const cassBlog = { ...sampleWithRequiredData };
      vitest.spyOn(cassBlogService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassBlog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassBlogService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
