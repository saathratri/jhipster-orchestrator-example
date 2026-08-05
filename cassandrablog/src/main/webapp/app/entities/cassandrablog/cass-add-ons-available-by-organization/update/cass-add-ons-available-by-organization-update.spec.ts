import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassAddOnsAvailableByOrganization } from '../cass-add-ons-available-by-organization.model';
import { sampleWithRequiredData } from '../cass-add-ons-available-by-organization.test-samples';
import { CassAddOnsAvailableByOrganizationService } from '../service/cass-add-ons-available-by-organization.service';

import { CassAddOnsAvailableByOrganizationFormService } from './cass-add-ons-available-by-organization-form.service';
import { CassAddOnsAvailableByOrganizationUpdateComponent } from './cass-add-ons-available-by-organization-update';

describe('CassAddOnsAvailableByOrganization Management Update Component', () => {
  let comp: CassAddOnsAvailableByOrganizationUpdateComponent;
  let fixture: ComponentFixture<CassAddOnsAvailableByOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassAddOnsAvailableByOrganizationFormService: CassAddOnsAvailableByOrganizationFormService;
  let cassAddOnsAvailableByOrganizationService: CassAddOnsAvailableByOrganizationService;

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

    fixture = TestBed.createComponent(CassAddOnsAvailableByOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassAddOnsAvailableByOrganizationFormService = TestBed.inject(CassAddOnsAvailableByOrganizationFormService);
    cassAddOnsAvailableByOrganizationService = TestBed.inject(CassAddOnsAvailableByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassAddOnsAvailableByOrganization });
      comp.ngOnInit();

      expect(comp.cassAddOnsAvailableByOrganization).toEqual(cassAddOnsAvailableByOrganization);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassAddOnsAvailableByOrganization>>();
      const cassAddOnsAvailableByOrganization = { ...sampleWithRequiredData };
      vitest
        .spyOn(cassAddOnsAvailableByOrganizationFormService, 'getCassAddOnsAvailableByOrganization')
        .mockReturnValue(cassAddOnsAvailableByOrganization);
      vitest.spyOn(cassAddOnsAvailableByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassAddOnsAvailableByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassAddOnsAvailableByOrganization }));
      saveSubject.complete();

      // THEN
      expect(cassAddOnsAvailableByOrganizationFormService.getCassAddOnsAvailableByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassAddOnsAvailableByOrganizationService.update).toHaveBeenCalledWith(
        expect.objectContaining(cassAddOnsAvailableByOrganization),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassAddOnsAvailableByOrganization>>();
      const cassAddOnsAvailableByOrganization = { ...sampleWithRequiredData };
      vitest
        .spyOn(cassAddOnsAvailableByOrganizationFormService, 'getCassAddOnsAvailableByOrganization')
        .mockReturnValue(cassAddOnsAvailableByOrganization);
      vitest.spyOn(cassAddOnsAvailableByOrganizationService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassAddOnsAvailableByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassAddOnsAvailableByOrganization }));
      saveSubject.complete();

      // THEN
      expect(cassAddOnsAvailableByOrganizationFormService.getCassAddOnsAvailableByOrganization).toHaveBeenCalled();
      expect(cassAddOnsAvailableByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassAddOnsAvailableByOrganization>>();
      const cassAddOnsAvailableByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(cassAddOnsAvailableByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassAddOnsAvailableByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassAddOnsAvailableByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
