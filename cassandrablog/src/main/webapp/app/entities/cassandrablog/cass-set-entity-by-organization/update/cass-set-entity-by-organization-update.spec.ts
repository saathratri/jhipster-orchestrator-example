import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICassSetEntityByOrganization } from '../cass-set-entity-by-organization.model';
import { sampleWithRequiredData } from '../cass-set-entity-by-organization.test-samples';
import { CassSetEntityByOrganizationService } from '../service/cass-set-entity-by-organization.service';

import { CassSetEntityByOrganizationFormService } from './cass-set-entity-by-organization-form.service';
import { CassSetEntityByOrganizationUpdateComponent } from './cass-set-entity-by-organization-update';

describe('CassSetEntityByOrganization Management Update Component', () => {
  let comp: CassSetEntityByOrganizationUpdateComponent;
  let fixture: ComponentFixture<CassSetEntityByOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cassSetEntityByOrganizationFormService: CassSetEntityByOrganizationFormService;
  let cassSetEntityByOrganizationService: CassSetEntityByOrganizationService;

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

    fixture = TestBed.createComponent(CassSetEntityByOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cassSetEntityByOrganizationFormService = TestBed.inject(CassSetEntityByOrganizationFormService);
    cassSetEntityByOrganizationService = TestBed.inject(CassSetEntityByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cassSetEntityByOrganization: ICassSetEntityByOrganization = { ...sampleWithRequiredData };

      activatedRoute.data = of({ cassSetEntityByOrganization });
      comp.ngOnInit();

      expect(comp.cassSetEntityByOrganization).toEqual(cassSetEntityByOrganization);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSetEntityByOrganization>>();
      const cassSetEntityByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(cassSetEntityByOrganizationFormService, 'getCassSetEntityByOrganization').mockReturnValue(cassSetEntityByOrganization);
      vitest.spyOn(cassSetEntityByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassSetEntityByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSetEntityByOrganization }));
      saveSubject.complete();

      // THEN
      expect(cassSetEntityByOrganizationFormService.getCassSetEntityByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cassSetEntityByOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(cassSetEntityByOrganization));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSetEntityByOrganization>>();
      const cassSetEntityByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(cassSetEntityByOrganizationFormService, 'getCassSetEntityByOrganization').mockReturnValue(cassSetEntityByOrganization);
      vitest.spyOn(cassSetEntityByOrganizationService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ cassSetEntityByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cassSetEntityByOrganization }));
      saveSubject.complete();

      // THEN
      expect(cassSetEntityByOrganizationFormService.getCassSetEntityByOrganization).toHaveBeenCalled();
      expect(cassSetEntityByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICassSetEntityByOrganization>>();
      const cassSetEntityByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(cassSetEntityByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ cassSetEntityByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cassSetEntityByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
