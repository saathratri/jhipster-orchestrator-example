import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassTajUser } from '../cass-taj-user.model';
import { sampleWithRequiredData } from '../cass-taj-user.test-samples';
import { CassTajUserService } from '../service/cass-taj-user.service';

import { CassTajUserFormService } from './cass-taj-user-form.service';
import { CassTajUserUpdateComponent } from './cass-taj-user-update';

describe('CassTajUser Management Update Component', () => {
  let comp: CassTajUserUpdateComponent;
  let fixture: ComponentFixture<CassTajUserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassTajUserFormService: CassTajUserFormService;
  let cassTajUserService: CassTajUserService;

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

    fixture = TestBed.createComponent(CassTajUserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassTajUserFormService = TestBed.inject(CassTajUserFormService);
    cassTajUserService = TestBed.inject(CassTajUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassTajUser: ICassTajUser = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassTajUser });
      comp.ngOnInit();

      expect(comp.cassTajUser).toEqual(cassTajUser);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassTajUser>>();
      const cassTajUser = { ...sampleWithRequiredData };
      vitest.spyOn(cassTajUserFormService, 'getCassTajUser').mockReturnValue(cassTajUser);
      vitest.spyOn(cassTajUserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassTajUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassTajUser }));
      saveSubject.complete();

      // THEN
      expect(cassTajUserFormService.getCassTajUser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassTajUserService.update).toHaveBeenCalledWith(expect.objectContaining(cassTajUser));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassTajUser>>();
      const cassTajUser = { ...sampleWithRequiredData };
      vitest.spyOn(cassTajUserFormService, 'getCassTajUser').mockReturnValue(cassTajUser);
      vitest.spyOn(cassTajUserService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassTajUser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassTajUser }));
      saveSubject.complete();

      // THEN
      expect(cassTajUserFormService.getCassTajUser).toHaveBeenCalled();
      expect(cassTajUserService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassTajUser>>();
      const cassTajUser = { ...sampleWithRequiredData };
      vitest.spyOn(cassTajUserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassTajUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassTajUserService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
