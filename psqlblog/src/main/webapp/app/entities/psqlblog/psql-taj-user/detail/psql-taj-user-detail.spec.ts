import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { PsqlTajUserDetail } from './psql-taj-user-detail';

describe('PsqlTajUser Management Detail Component', () => {
  let comp: PsqlTajUserDetail;
  let fixture: ComponentFixture<PsqlTajUserDetail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./psql-taj-user-detail').then(m => m.PsqlTajUserDetail),
              resolve: { psqlTajUser: () => of({ id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' }) },
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
    fixture = TestBed.createComponent(PsqlTajUserDetail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load psqlTajUser on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PsqlTajUserDetail);

      // THEN
      expect(instance.psqlTajUser()).toEqual(expect.objectContaining({ id: '06fc7b01-0eb0-4521-b87c-d057cad696d7' }));
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
