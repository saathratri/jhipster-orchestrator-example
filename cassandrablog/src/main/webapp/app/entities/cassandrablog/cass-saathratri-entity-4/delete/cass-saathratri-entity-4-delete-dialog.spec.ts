import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../cass-saathratri-entity-4.test-samples';
import { CassSaathratriEntity4Service } from '../service/cass-saathratri-entity-4.service';

import { CassSaathratriEntity4DeleteDialogComponent } from './cass-saathratri-entity-4-delete-dialog';

describe('CassSaathratriEntity4 Management Delete Component', () => {
  let comp: CassSaathratriEntity4DeleteDialogComponent;
  let fixture: ComponentFixture<CassSaathratriEntity4DeleteDialogComponent>;
  let service: CassSaathratriEntity4Service;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NgbActiveModal],
    });
    fixture = TestBed.createComponent(CassSaathratriEntity4DeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CassSaathratriEntity4Service);
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
