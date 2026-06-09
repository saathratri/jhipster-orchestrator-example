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

import { sampleWithRequiredData } from '../cass-product.test-samples';
import { CassProductService } from '../service/cass-product.service';

import { CassProductComponent } from './cass-product';

describe('CassProduct Management Component', () => {
  let comp: CassProductComponent;
  let fixture: ComponentFixture<CassProductComponent>;
  let service: CassProductService;

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
    fixture = TestBed.createComponent(CassProductComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CassProductService);
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
});
