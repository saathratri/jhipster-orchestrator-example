import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IPsqlPost } from 'app/entities/psqlblog/psql-post/psql-post.model';
import { PsqlPostService } from 'app/entities/psqlblog/psql-post/service/psql-post.service';
import { IPsqlTag } from '../psql-tag.model';
import { PsqlTagService } from '../service/psql-tag.service';

import { PsqlTagFormService } from './psql-tag-form.service';
import { PsqlTagUpdate } from './psql-tag-update';

describe('PsqlTag Management Update Component', () => {
  let comp: PsqlTagUpdate;
  let fixture: ComponentFixture<PsqlTagUpdate>;
  let activatedRoute: ActivatedRoute;
  let psqlTagFormService: PsqlTagFormService;
  let psqlTagService: PsqlTagService;
  let psqlPostService: PsqlPostService;

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

    fixture = TestBed.createComponent(PsqlTagUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    psqlTagFormService = TestBed.inject(PsqlTagFormService);
    psqlTagService = TestBed.inject(PsqlTagService);
    psqlPostService = TestBed.inject(PsqlPostService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call PsqlPost query and add missing value', () => {
      const psqlTag: IPsqlTag = { id: '2599c684-97f7-4f14-98a3-40b2c54998bc' };
      const posts: IPsqlPost[] = [{ id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' }];
      psqlTag.posts = posts;

      const psqlPostCollection: IPsqlPost[] = [{ id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' }];
      vitest.spyOn(psqlPostService, 'query').mockReturnValue(of(new HttpResponse({ body: psqlPostCollection })));
      const additionalPsqlPosts = [...posts];
      const expectedCollection: IPsqlPost[] = [...additionalPsqlPosts, ...psqlPostCollection];
      vitest.spyOn(psqlPostService, 'addPsqlPostToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ psqlTag });
      comp.ngOnInit();

      expect(psqlPostService.query).toHaveBeenCalled();
      expect(psqlPostService.addPsqlPostToCollectionIfMissing).toHaveBeenCalledWith(
        psqlPostCollection,
        ...additionalPsqlPosts.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.psqlPostsSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const psqlTag: IPsqlTag = { id: '2599c684-97f7-4f14-98a3-40b2c54998bc' };
      const post: IPsqlPost = { id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' };
      psqlTag.posts = [post];

      activatedRoute.data = of({ psqlTag });
      comp.ngOnInit();

      expect(comp.psqlPostsSharedCollection()).toContainEqual(post);
      expect(comp.psqlTag).toEqual(psqlTag);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlTag>();
      const psqlTag = { id: 'a284050f-e85e-4f01-a461-b9c22e586516' };
      vitest.spyOn(psqlTagFormService, 'getPsqlTag').mockReturnValue(psqlTag);
      vitest.spyOn(psqlTagService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlTag });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlTag);
      saveSubject.complete();

      // THEN
      expect(psqlTagFormService.getPsqlTag).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(psqlTagService.update).toHaveBeenCalledWith(expect.objectContaining(psqlTag));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlTag>();
      const psqlTag = { id: 'a284050f-e85e-4f01-a461-b9c22e586516' };
      vitest.spyOn(psqlTagFormService, 'getPsqlTag').mockReturnValue({ id: null });
      vitest.spyOn(psqlTagService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlTag: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlTag);
      saveSubject.complete();

      // THEN
      expect(psqlTagFormService.getPsqlTag).toHaveBeenCalled();
      expect(psqlTagService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlTag>();
      const psqlTag = { id: 'a284050f-e85e-4f01-a461-b9c22e586516' };
      vitest.spyOn(psqlTagService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlTag });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(psqlTagService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePsqlPost', () => {
      it('should forward to psqlPostService', () => {
        const entity = { id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' };
        const entity2 = { id: '65e57101-2025-434a-ba76-944e34e9c0fb' };
        vitest.spyOn(psqlPostService, 'comparePsqlPost');
        comp.comparePsqlPost(entity, entity2);
        expect(psqlPostService.comparePsqlPost).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
