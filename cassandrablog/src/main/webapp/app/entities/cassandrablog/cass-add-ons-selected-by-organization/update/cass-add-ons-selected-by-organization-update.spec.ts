import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassAddOnsSelectedByOrganization } from '../cass-add-ons-selected-by-organization.model';
import { sampleWithRequiredData } from '../cass-add-ons-selected-by-organization.test-samples';
import { CassAddOnsSelectedByOrganizationService } from '../service/cass-add-ons-selected-by-organization.service';

import { CassAddOnsSelectedByOrganizationFormService } from './cass-add-ons-selected-by-organization-form.service';
import { CassAddOnsSelectedByOrganizationUpdateComponent } from './cass-add-ons-selected-by-organization-update';

describe('CassAddOnsSelectedByOrganization Management Update Component', () => {
  let comp: CassAddOnsSelectedByOrganizationUpdateComponent;
  let fixture: ComponentFixture<CassAddOnsSelectedByOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassAddOnsSelectedByOrganizationFormService: CassAddOnsSelectedByOrganizationFormService;
  let cassAddOnsSelectedByOrganizationService: CassAddOnsSelectedByOrganizationService;

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

    fixture = TestBed.createComponent(CassAddOnsSelectedByOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassAddOnsSelectedByOrganizationFormService = TestBed.inject(CassAddOnsSelectedByOrganizationFormService);
    cassAddOnsSelectedByOrganizationService = TestBed.inject(CassAddOnsSelectedByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassAddOnsSelectedByOrganization: ICassAddOnsSelectedByOrganization = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassAddOnsSelectedByOrganization });
      comp.ngOnInit();

      expect(comp.cassAddOnsSelectedByOrganization).toEqual(cassAddOnsSelectedByOrganization);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassAddOnsSelectedByOrganization>>();
      const cassAddOnsSelectedByOrganization = { ...sampleWithRequiredData };
      vitest
        .spyOn(cassAddOnsSelectedByOrganizationFormService, 'getCassAddOnsSelectedByOrganization')
        .mockReturnValue(cassAddOnsSelectedByOrganization);
      vitest.spyOn(cassAddOnsSelectedByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassAddOnsSelectedByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassAddOnsSelectedByOrganization }));
      saveSubject.complete();

      // THEN
      expect(cassAddOnsSelectedByOrganizationFormService.getCassAddOnsSelectedByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassAddOnsSelectedByOrganizationService.update).toHaveBeenCalledWith(
        expect.objectContaining(cassAddOnsSelectedByOrganization),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassAddOnsSelectedByOrganization>>();
      const cassAddOnsSelectedByOrganization = { ...sampleWithRequiredData };
      vitest
        .spyOn(cassAddOnsSelectedByOrganizationFormService, 'getCassAddOnsSelectedByOrganization')
        .mockReturnValue(cassAddOnsSelectedByOrganization);
      vitest.spyOn(cassAddOnsSelectedByOrganizationService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassAddOnsSelectedByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassAddOnsSelectedByOrganization }));
      saveSubject.complete();

      // THEN
      expect(cassAddOnsSelectedByOrganizationFormService.getCassAddOnsSelectedByOrganization).toHaveBeenCalled();
      expect(cassAddOnsSelectedByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassAddOnsSelectedByOrganization>>();
      const cassAddOnsSelectedByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(cassAddOnsSelectedByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassAddOnsSelectedByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassAddOnsSelectedByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
