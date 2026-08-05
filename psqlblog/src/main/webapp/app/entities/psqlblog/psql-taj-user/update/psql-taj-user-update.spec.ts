import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IPsqlTajUser } from '../psql-taj-user.model';
import { PsqlTajUserService } from '../service/psql-taj-user.service';

import { PsqlTajUserFormService } from './psql-taj-user-form.service';
import { PsqlTajUserUpdate } from './psql-taj-user-update';

describe('PsqlTajUser Management Update Component', () => {
  let comp: PsqlTajUserUpdate;
  let fixture: ComponentFixture<PsqlTajUserUpdate>;
  let activatedRoute: ActivatedRoute;
  let psqlTajUserFormService: PsqlTajUserFormService;
  let psqlTajUserService: PsqlTajUserService;

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

    fixture = TestBed.createComponent(PsqlTajUserUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    psqlTajUserFormService = TestBed.inject(PsqlTajUserFormService);
    psqlTajUserService = TestBed.inject(PsqlTajUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const psqlTajUser: IPsqlTajUser = { id: '5d007a04-df5d-4ff8-b846-f9bf5f07ee94' };

      activatedRoute.data = of({ psqlTajUser });
      comp.ngOnInit();

      expect(comp.psqlTajUser).toEqual(psqlTajUser);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlTajUser>();
      const psqlTajUser = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };
      vitest.spyOn(psqlTajUserFormService, 'getPsqlTajUser').mockReturnValue(psqlTajUser);
      vitest.spyOn(psqlTajUserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlTajUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlTajUser);
      saveSubject.complete();

      // THEN
      expect(psqlTajUserFormService.getPsqlTajUser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(psqlTajUserService.update).toHaveBeenCalledWith(expect.objectContaining(psqlTajUser));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlTajUser>();
      const psqlTajUser = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };
      vitest.spyOn(psqlTajUserFormService, 'getPsqlTajUser').mockReturnValue({ id: null });
      vitest.spyOn(psqlTajUserService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlTajUser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlTajUser);
      saveSubject.complete();

      // THEN
      expect(psqlTajUserFormService.getPsqlTajUser).toHaveBeenCalled();
      expect(psqlTajUserService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlTajUser>();
      const psqlTajUser = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };
      vitest.spyOn(psqlTajUserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlTajUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(psqlTajUserService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
