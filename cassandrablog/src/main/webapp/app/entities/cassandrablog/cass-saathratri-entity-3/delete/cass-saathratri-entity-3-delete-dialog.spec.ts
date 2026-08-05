import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../cass-saathratri-entity-3.test-samples';
import { CassSaathratriEntity3Service } from '../service/cass-saathratri-entity-3.service';

import { CassSaathratriEntity3DeleteDialogComponent } from './cass-saathratri-entity-3-delete-dialog';

describe('CassSaathratriEntity3 Management Delete Component', () => {
  let comp: CassSaathratriEntity3DeleteDialogComponent;
  let fixture: ComponentFixture<CassSaathratriEntity3DeleteDialogComponent>;
  let service: CassSaathratriEntity3Service;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NgbActiveModal],
    });
    fixture = TestBed.createComponent(CassSaathratriEntity3DeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CassSaathratriEntity3Service);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('should call delete service on confirmDelete', () => {
      // GIVEN
      vitest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse<{}>()));
      vitest.spyOn(mockActiveModal, 'close');

      // WHEN
      comp.confirmDelete(sampleWithRequiredData);

      // THEN
      expect(service.delete).toHaveBeenCalledWith(sampleWithRequiredData);
      expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
    });

    it('should not call delete service on clear', () => {
      // GIVEN
      vitest.spyOn(service, 'delete');
      vitest.spyOn(mockActiveModal, 'close');
      vitest.spyOn(mockActiveModal, 'dismiss');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
