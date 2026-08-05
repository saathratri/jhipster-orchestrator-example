import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../cass-add-ons-available-by-organization.test-samples';

import { CassAddOnsAvailableByOrganizationDetailComponent } from './cass-add-ons-available-by-organization-detail';

describe('CassAddOnsAvailableByOrganization Management Detail Component', () => {
  let comp: CassAddOnsAvailableByOrganizationDetailComponent;
  let fixture: ComponentFixture<CassAddOnsAvailableByOrganizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./cass-add-ons-available-by-organization-detail').then(m => m.CassAddOnsAvailableByOrganizationDetailComponent),
              resolve: { cassAddOnsAvailableByOrganization: () => of(sampleWithRequiredData) },
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
    fixture = TestBed.createComponent(CassAddOnsAvailableByOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load cassAddOnsAvailableByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CassAddOnsAvailableByOrganizationDetailComponent);

      // THEN
      expect(instance.cassAddOnsAvailableByOrganization()).toEqual(expect.objectContaining(sampleWithRequiredData));
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
