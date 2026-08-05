import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IPsqlTajUser } from 'app/entities/psqlblog/psql-taj-user/psql-taj-user.model';
import { PsqlTajUserService } from 'app/entities/psqlblog/psql-taj-user/service/psql-taj-user.service';
import { IPsqlBlog } from '../psql-blog.model';
import { PsqlBlogService } from '../service/psql-blog.service';

import { PsqlBlogFormService } from './psql-blog-form.service';
import { PsqlBlogUpdate } from './psql-blog-update';

describe('PsqlBlog Management Update Component', () => {
  let comp: PsqlBlogUpdate;
  let fixture: ComponentFixture<PsqlBlogUpdate>;
  let activatedRoute: ActivatedRoute;
  let psqlBlogFormService: PsqlBlogFormService;
  let psqlBlogService: PsqlBlogService;
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

    fixture = TestBed.createComponent(PsqlBlogUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    psqlBlogFormService = TestBed.inject(PsqlBlogFormService);
    psqlBlogService = TestBed.inject(PsqlBlogService);
    psqlTajUserService = TestBed.inject(PsqlTajUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call PsqlTajUser query and add missing value', () => {
      const psqlBlog: IPsqlBlog = { id: 'bd89b5d2-fe35-41a6-b81c-17a7f1afe1eb' };
      const tajUser: IPsqlTajUser = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };
      psqlBlog.tajUser = tajUser;

      const psqlTajUserCollection: IPsqlTajUser[] = [{ id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' }];
      vitest.spyOn(psqlTajUserService, 'query').mockReturnValue(of(new HttpResponse({ body: psqlTajUserCollection })));
      const additionalPsqlTajUsers = [tajUser];
      const expectedCollection: IPsqlTajUser[] = [...additionalPsqlTajUsers, ...psqlTajUserCollection];
      vitest.spyOn(psqlTajUserService, 'addPsqlTajUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ psqlBlog });
      comp.ngOnInit();

      expect(psqlTajUserService.query).toHaveBeenCalled();
      expect(psqlTajUserService.addPsqlTajUserToCollectionIfMissing).toHaveBeenCalledWith(
        psqlTajUserCollection,
        ...additionalPsqlTajUsers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.psqlTajUsersSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const psqlBlog: IPsqlBlog = { id: 'bd89b5d2-fe35-41a6-b81c-17a7f1afe1eb' };
      const tajUser: IPsqlTajUser = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };
      psqlBlog.tajUser = tajUser;

      activatedRoute.data = of({ psqlBlog });
      comp.ngOnInit();

      expect(comp.psqlTajUsersSharedCollection()).toContainEqual(tajUser);
      expect(comp.psqlBlog).toEqual(psqlBlog);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlBlog>();
      const psqlBlog = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };
      vitest.spyOn(psqlBlogFormService, 'getPsqlBlog').mockReturnValue(psqlBlog);
      vitest.spyOn(psqlBlogService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlBlog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlBlog);
      saveSubject.complete();

      // THEN
      expect(psqlBlogFormService.getPsqlBlog).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(psqlBlogService.update).toHaveBeenCalledWith(expect.objectContaining(psqlBlog));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlBlog>();
      const psqlBlog = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };
      vitest.spyOn(psqlBlogFormService, 'getPsqlBlog').mockReturnValue({ id: null });
      vitest.spyOn(psqlBlogService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlBlog: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlBlog);
      saveSubject.complete();

      // THEN
      expect(psqlBlogFormService.getPsqlBlog).toHaveBeenCalled();
      expect(psqlBlogService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlBlog>();
      const psqlBlog = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };
      vitest.spyOn(psqlBlogService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlBlog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(psqlBlogService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePsqlTajUser', () => {
      it('should forward to psqlTajUserService', () => {
        const entity = { id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' };
        const entity2 = { id: '5d007a04-df5d-4ff8-b846-f9bf5f07ee94' };
        vitest.spyOn(psqlTajUserService, 'comparePsqlTajUser');
        comp.comparePsqlTajUser(entity, entity2);
        expect(psqlTajUserService.comparePsqlTajUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
