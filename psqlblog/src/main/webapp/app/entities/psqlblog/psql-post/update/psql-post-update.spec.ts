import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IPsqlBlog } from 'app/entities/psqlblog/psql-blog/psql-blog.model';
import { PsqlBlogService } from 'app/entities/psqlblog/psql-blog/service/psql-blog.service';
import { IPsqlTag } from 'app/entities/psqlblog/psql-tag/psql-tag.model';
import { PsqlTagService } from 'app/entities/psqlblog/psql-tag/service/psql-tag.service';
import { IPsqlPost } from '../psql-post.model';
import { PsqlPostService } from '../service/psql-post.service';

import { PsqlPostFormService } from './psql-post-form.service';
import { PsqlPostUpdate } from './psql-post-update';

describe('PsqlPost Management Update Component', () => {
  let comp: PsqlPostUpdate;
  let fixture: ComponentFixture<PsqlPostUpdate>;
  let activatedRoute: ActivatedRoute;
  let psqlPostFormService: PsqlPostFormService;
  let psqlPostService: PsqlPostService;
  let psqlBlogService: PsqlBlogService;
  let psqlTagService: PsqlTagService;

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

    fixture = TestBed.createComponent(PsqlPostUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    psqlPostFormService = TestBed.inject(PsqlPostFormService);
    psqlPostService = TestBed.inject(PsqlPostService);
    psqlBlogService = TestBed.inject(PsqlBlogService);
    psqlTagService = TestBed.inject(PsqlTagService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call PsqlBlog query and add missing value', () => {
      const psqlPost: IPsqlPost = { id: '65e57101-2025-434a-ba76-944e34e9c0fb' };
      const blog: IPsqlBlog = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };
      psqlPost.blog = blog;

      const psqlBlogCollection: IPsqlBlog[] = [{ id: '126971d1-2552-470f-b163-e06cf6bfbda4' }];
      vitest.spyOn(psqlBlogService, 'query').mockReturnValue(of(new HttpResponse({ body: psqlBlogCollection })));
      const additionalPsqlBlogs = [blog];
      const expectedCollection: IPsqlBlog[] = [...additionalPsqlBlogs, ...psqlBlogCollection];
      vitest.spyOn(psqlBlogService, 'addPsqlBlogToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ psqlPost });
      comp.ngOnInit();

      expect(psqlBlogService.query).toHaveBeenCalled();
      expect(psqlBlogService.addPsqlBlogToCollectionIfMissing).toHaveBeenCalledWith(
        psqlBlogCollection,
        ...additionalPsqlBlogs.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.psqlBlogsSharedCollection()).toEqual(expectedCollection);
    });

    it('should call PsqlTag query and add missing value', () => {
      const psqlPost: IPsqlPost = { id: '65e57101-2025-434a-ba76-944e34e9c0fb' };
      const tags: IPsqlTag[] = [{ id: 'a284050f-e85e-4f01-a461-b9c22e586516' }];
      psqlPost.tags = tags;

      const psqlTagCollection: IPsqlTag[] = [{ id: 'a284050f-e85e-4f01-a461-b9c22e586516' }];
      vitest.spyOn(psqlTagService, 'query').mockReturnValue(of(new HttpResponse({ body: psqlTagCollection })));
      const additionalPsqlTags = [...tags];
      const expectedCollection: IPsqlTag[] = [...additionalPsqlTags, ...psqlTagCollection];
      vitest.spyOn(psqlTagService, 'addPsqlTagToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ psqlPost });
      comp.ngOnInit();

      expect(psqlTagService.query).toHaveBeenCalled();
      expect(psqlTagService.addPsqlTagToCollectionIfMissing).toHaveBeenCalledWith(
        psqlTagCollection,
        ...additionalPsqlTags.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.psqlTagsSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const psqlPost: IPsqlPost = { id: '65e57101-2025-434a-ba76-944e34e9c0fb' };
      const blog: IPsqlBlog = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };
      psqlPost.blog = blog;
      const tag: IPsqlTag = { id: 'a284050f-e85e-4f01-a461-b9c22e586516' };
      psqlPost.tags = [tag];

      activatedRoute.data = of({ psqlPost });
      comp.ngOnInit();

      expect(comp.psqlBlogsSharedCollection()).toContainEqual(blog);
      expect(comp.psqlTagsSharedCollection()).toContainEqual(tag);
      expect(comp.psqlPost).toEqual(psqlPost);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlPost>();
      const psqlPost = { id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' };
      vitest.spyOn(psqlPostFormService, 'getPsqlPost').mockReturnValue(psqlPost);
      vitest.spyOn(psqlPostService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlPost });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlPost);
      saveSubject.complete();

      // THEN
      expect(psqlPostFormService.getPsqlPost).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(psqlPostService.update).toHaveBeenCalledWith(expect.objectContaining(psqlPost));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlPost>();
      const psqlPost = { id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' };
      vitest.spyOn(psqlPostFormService, 'getPsqlPost').mockReturnValue({ id: null });
      vitest.spyOn(psqlPostService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlPost: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(psqlPost);
      saveSubject.complete();

      // THEN
      expect(psqlPostFormService.getPsqlPost).toHaveBeenCalled();
      expect(psqlPostService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IPsqlPost>();
      const psqlPost = { id: 'd5e9bde4-66e1-4c16-a5c8-29f7c8766362' };
      vitest.spyOn(psqlPostService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ psqlPost });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(psqlPostService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePsqlBlog', () => {
      it('should forward to psqlBlogService', () => {
        const entity = { id: '126971d1-2552-470f-b163-e06cf6bfbda4' };
        const entity2 = { id: 'bd89b5d2-fe35-41a6-b81c-17a7f1afe1eb' };
        vitest.spyOn(psqlBlogService, 'comparePsqlBlog');
        comp.comparePsqlBlog(entity, entity2);
        expect(psqlBlogService.comparePsqlBlog).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePsqlTag', () => {
      it('should forward to psqlTagService', () => {
        const entity = { id: 'a284050f-e85e-4f01-a461-b9c22e586516' };
        const entity2 = { id: '2599c684-97f7-4f14-98a3-40b2c54998bc' };
        vitest.spyOn(psqlTagService, 'comparePsqlTag');
        comp.comparePsqlTag(entity, entity2);
        expect(psqlTagService.comparePsqlTag).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
