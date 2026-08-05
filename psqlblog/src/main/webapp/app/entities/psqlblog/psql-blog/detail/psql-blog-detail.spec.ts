import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { PsqlBlogDetail } from './psql-blog-detail';

describe('PsqlBlog Management Detail Component', () => {
  let comp: PsqlBlogDetail;
  let fixture: ComponentFixture<PsqlBlogDetail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./psql-blog-detail').then(m => m.PsqlBlogDetail),
              resolve: { psqlBlog: () => of({ id: '126971d1-2552-470f-b163-e06cf6bfbda4' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    });
    const library = TestBed.inject(FaIconLibrary);
    library.addIcons(faArrowLeft);
    library.addIcons(faPencilAlt);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PsqlBlogDetail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load psqlBlog on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PsqlBlogDetail);

      // THEN
      expect(instance.psqlBlog()).toEqual(expect.objectContaining({ id: '126971d1-2552-470f-b163-e06cf6bfbda4' }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      vitest.spyOn(globalThis.history, 'back');
      comp.previousState();
      expect(globalThis.history.back).toHaveBeenCalled();
    });
  });
});
