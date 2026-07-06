import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassProduct } from '../cass-product.model';
import { sampleWithRequiredData } from '../cass-product.test-samples';
import { CassProductService } from '../service/cass-product.service';

import { CassProductFormService } from './cass-product-form.service';
import { CassProductUpdateComponent } from './cass-product-update';

describe('CassProduct Management Update Component', () => {
  let comp: CassProductUpdateComponent;
  let fixture: ComponentFixture<CassProductUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassProductFormService: CassProductFormService;
  let cassProductService: CassProductService;

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

    fixture = TestBed.createComponent(CassProductUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassProductFormService = TestBed.inject(CassProductFormService);
    cassProductService = TestBed.inject(CassProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassProduct: ICassProduct = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassProduct });
      comp.ngOnInit();

      expect(comp.cassProduct).toEqual(cassProduct);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassProduct>>();
      const cassProduct = { ...sampleWithRequiredData };
      vitest.spyOn(cassProductFormService, 'getCassProduct').mockReturnValue(cassProduct);
      vitest.spyOn(cassProductService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassProduct });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassProduct }));
      saveSubject.complete();

      // THEN
      expect(cassProductFormService.getCassProduct).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassProductService.update).toHaveBeenCalledWith(expect.objectContaining(cassProduct));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassProduct>>();
      const cassProduct = { ...sampleWithRequiredData };
      vitest.spyOn(cassProductFormService, 'getCassProduct').mockReturnValue(cassProduct);
      vitest.spyOn(cassProductService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassProduct: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassProduct }));
      saveSubject.complete();

      // THEN
      expect(cassProductFormService.getCassProduct).toHaveBeenCalled();
      expect(cassProductService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassProduct>>();
      const cassProduct = { ...sampleWithRequiredData };
      vitest.spyOn(cassProductService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassProduct });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassProductService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
