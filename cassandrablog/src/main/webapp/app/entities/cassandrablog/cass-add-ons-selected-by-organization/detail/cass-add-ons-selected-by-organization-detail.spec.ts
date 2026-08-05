import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../cass-add-ons-selected-by-organization.test-samples';

import { CassAddOnsSelectedByOrganizationDetailComponent } from './cass-add-ons-selected-by-organization-detail';

describe('CassAddOnsSelectedByOrganization Management Detail Component', () => {
  let comp: CassAddOnsSelectedByOrganizationDetailComponent;
  let fixture: ComponentFixture<CassAddOnsSelectedByOrganizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./cass-add-ons-selected-by-organization-detail').then(m => m.CassAddOnsSelectedByOrganizationDetailComponent),
              resolve: { cassAddOnsSelectedByOrganization: () => of(sampleWithRequiredData) },
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
    fixture = TestBed.createComponent(CassAddOnsSelectedByOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load cassAddOnsSelectedByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CassAddOnsSelectedByOrganizationDetailComponent);

      // THEN
      expect(instance.cassAddOnsSelectedByOrganization()).toEqual(expect.objectContaining(sampleWithRequiredData));
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
