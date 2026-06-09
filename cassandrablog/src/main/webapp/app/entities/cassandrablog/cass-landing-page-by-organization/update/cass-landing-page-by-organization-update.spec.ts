import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassLandingPageByOrganization } from '../cass-landing-page-by-organization.model';
import { sampleWithRequiredData } from '../cass-landing-page-by-organization.test-samples';
import { CassLandingPageByOrganizationService } from '../service/cass-landing-page-by-organization.service';

import { CassLandingPageByOrganizationFormService } from './cass-landing-page-by-organization-form.service';
import { CassLandingPageByOrganizationUpdateComponent } from './cass-landing-page-by-organization-update';

describe('CassLandingPageByOrganization Management Update Component', () => {
  let comp: CassLandingPageByOrganizationUpdateComponent;
  let fixture: ComponentFixture<CassLandingPageByOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassLandingPageByOrganizationFormService: CassLandingPageByOrganizationFormService;
  let cassLandingPageByOrganizationService: CassLandingPageByOrganizationService;

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

    fixture = TestBed.createComponent(CassLandingPageByOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassLandingPageByOrganizationFormService = TestBed.inject(CassLandingPageByOrganizationFormService);
    cassLandingPageByOrganizationService = TestBed.inject(CassLandingPageByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassLandingPageByOrganization: ICassLandingPageByOrganization = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassLandingPageByOrganization });
      comp.ngOnInit();

      expect(comp.cassLandingPageByOrganization).toEqual(cassLandingPageByOrganization);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassLandingPageByOrganization>>();
      const cassLandingPageByOrganization = { ...sampleWithRequiredData };
      vitest
        .spyOn(cassLandingPageByOrganizationFormService, 'getCassLandingPageByOrganization')
        .mockReturnValue(cassLandingPageByOrganization);
      vitest.spyOn(cassLandingPageByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassLandingPageByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassLandingPageByOrganization }));
      saveSubject.complete();

      // THEN
      expect(cassLandingPageByOrganizationFormService.getCassLandingPageByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassLandingPageByOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(cassLandingPageByOrganization));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassLandingPageByOrganization>>();
      const cassLandingPageByOrganization = { ...sampleWithRequiredData };
      vitest
        .spyOn(cassLandingPageByOrganizationFormService, 'getCassLandingPageByOrganization')
        .mockReturnValue(cassLandingPageByOrganization);
      vitest.spyOn(cassLandingPageByOrganizationService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassLandingPageByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassLandingPageByOrganization }));
      saveSubject.complete();

      // THEN
      expect(cassLandingPageByOrganizationFormService.getCassLandingPageByOrganization).toHaveBeenCalled();
      expect(cassLandingPageByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassLandingPageByOrganization>>();
      const cassLandingPageByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(cassLandingPageByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cassLandingPageByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassLandingPageByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
