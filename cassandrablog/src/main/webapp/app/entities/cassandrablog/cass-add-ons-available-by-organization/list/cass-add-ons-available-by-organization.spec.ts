import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpHeaders, HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, inject } from '@angular/core/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faSort } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { TranslateModule } from '@ngx-translate/core';
import { Subject, of } from 'rxjs';

import { sampleWithRequiredData } from '../cass-add-ons-available-by-organization.test-samples';
import { CassAddOnsAvailableByOrganizationService } from '../service/cass-add-ons-available-by-organization.service';

import { CassAddOnsAvailableByOrganizationComponent } from './cass-add-ons-available-by-organization';

describe('CassAddOnsAvailableByOrganization Management Component', () => {
  let comp: CassAddOnsAvailableByOrganizationComponent;
  let fixture: ComponentFixture<CassAddOnsAvailableByOrganizationComponent>;
  let service: CassAddOnsAvailableByOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({ defaultSort: 'id,asc' }),
            queryParamMap: of(convertToParamMap({})),
            snapshot: { queryParams: {}, queryParamMap: convertToParamMap({}) },
          },
        },
      ],
    });
    fixture = TestBed.createComponent(CassAddOnsAvailableByOrganizationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CassAddOnsAvailableByOrganizationService);
    const library = TestBed.inject(FaIconLibrary);
    library.addIcons(faSort);
  });

  it('should load all on init', () => {
    // GIVEN — the Cassandra list pages via querySlice (not query)
    vitest
      .spyOn(service, 'querySlice')
      .mockReturnValue(of(new HttpResponse({ body: [sampleWithRequiredData], headers: new HttpHeaders() })));

    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.querySlice).toHaveBeenCalled();
  });

  describe('delete', () => {
    let ngbModal: NgbModal;
    let deleteModalMock: any;

    beforeEach(() => {
      deleteModalMock = { componentInstance: {}, closed: new Subject() };
      ngbModal = (comp as any).modalService;
      vitest.spyOn(ngbModal, 'open').mockReturnValue(deleteModalMock);
    });

    it('on confirm should call load', inject([], () => {
      // GIVEN
      vitest.spyOn(comp, 'load');

      // WHEN
      comp.delete(sampleWithRequiredData);
      deleteModalMock.closed.next('deleted');

      // THEN
      expect(ngbModal.open).toHaveBeenCalled();
      expect(comp.load).toHaveBeenCalled();
    }));

    it('on dismiss should not call load', inject([], () => {
      // GIVEN
      vitest.spyOn(comp, 'load');

      // WHEN
      comp.delete(sampleWithRequiredData);
      deleteModalMock.closed.next();

      // THEN
      expect(ngbModal.open).toHaveBeenCalled();
      expect(comp.load).not.toHaveBeenCalled();
    }));
  });

  describe('search form', () => {
    it('should toggle the search form collapsed state', () => {
      const initial = comp.isSearchFormCollapsed;

      comp.toggleSearchForm();
      expect(comp.isSearchFormCollapsed).toBe(!initial);

      comp.toggleSearchForm();
      expect(comp.isSearchFormCollapsed).toBe(initial);
    });

    it('should report the form invalid and inactive until a partition key is entered', () => {
      // A fresh form has no partition-key criteria, so it is neither valid to submit nor active.
      expect(comp.isSearchFormValid()).toBe(false);
      expect(comp.hasActiveSearch()).toBe(false);
    });

    it('should not run a search while the form is invalid (no partition key set)', () => {
      const loadSpy = vitest.spyOn(comp, 'load');

      comp.performSearch();

      expect(comp.hasActiveSearch()).toBe(false);
      expect(loadSpy).not.toHaveBeenCalled();
    });

    it('should clear the search and reload', () => {
      const loadSpy = vitest.spyOn(comp, 'load').mockImplementation(() => {});
      comp.isSearchActive = true;

      comp.clearSearch();

      expect(comp.hasActiveSearch()).toBe(false);
      expect(loadSpy).toHaveBeenCalled();
    });
  });
});
