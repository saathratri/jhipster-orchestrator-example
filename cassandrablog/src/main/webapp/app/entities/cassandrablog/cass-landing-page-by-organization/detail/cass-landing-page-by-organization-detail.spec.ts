import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../cass-landing-page-by-organization.test-samples';

import { CassLandingPageByOrganizationDetailComponent } from './cass-landing-page-by-organization-detail';

describe('CassLandingPageByOrganization Management Detail Component', () => {
  let comp: CassLandingPageByOrganizationDetailComponent;
  let fixture: ComponentFixture<CassLandingPageByOrganizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./cass-landing-page-by-organization-detail').then(m => m.CassLandingPageByOrganizationDetailComponent),
              resolve: { cassLandingPageByOrganization: () => of(sampleWithRequiredData) },
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
    fixture = TestBed.createComponent(CassLandingPageByOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load cassLandingPageByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CassLandingPageByOrganizationDetailComponent);

      // THEN
      expect(instance.cassLandingPageByOrganization()).toEqual(expect.objectContaining(sampleWithRequiredData));
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
