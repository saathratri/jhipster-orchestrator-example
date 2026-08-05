import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IPsqlProduct } from '../psql-product.model';
import { PsqlProductService } from '../service/psql-product.service';

import { PsqlProductFormService } from './psql-product-form.service';
import { PsqlProductUpdate } from './psql-product-update';

describe('PsqlProduct Management Update Component', () => {
  let comp: PsqlProductUpdate;
  let fixture: ComponentFixture<PsqlProductUpdate>;
  let activatedRoute: ActivatedRoute;
  let psqlProductFormService: PsqlProductFormService;
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

    fixture = TestBed.createComponent(PsqlProductUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    psqlProductFormService = TestBed.inject(PsqlProductFormService);
    psqlProductService = TestBed.inject(PsqlProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const psqlProduct: IPsqlProduct = { id: '4827651f-81f9-4588-b671-f48e6bc9ce0b' };

      activatedRoute.data = of({ psqlProduct });
      comp.ngOnInit();

      expect(comp.psqlProduct).toEqual(psqlProduct);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlProduct>();
      const psqlProduct = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };
      vitest.spyOn(psqlProductFormService, 'getPsqlProduct').mockReturnValue(psqlProduct);
      vitest.spyOn(psqlProductService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlProduct });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlProduct);
      saveSubject.complete();

      // THEN
      expect(psqlProductFormService.getPsqlProduct).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(psqlProductService.update).toHaveBeenCalledWith(expect.objectContaining(psqlProduct));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlProduct>();
      const psqlProduct = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };
      vitest.spyOn(psqlProductFormService, 'getPsqlProduct').mockReturnValue({ id: null });
      vitest.spyOn(psqlProductService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlProduct: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlProduct);
      saveSubject.complete();

      // THEN
      expect(psqlProductFormService.getPsqlProduct).toHaveBeenCalled();
      expect(psqlProductService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlProduct>();
      const psqlProduct = { id: '27c299d6-3e62-4e45-b862-4c6b629af87a' };
      vitest.spyOn(psqlProductService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlProduct });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(psqlProductService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
