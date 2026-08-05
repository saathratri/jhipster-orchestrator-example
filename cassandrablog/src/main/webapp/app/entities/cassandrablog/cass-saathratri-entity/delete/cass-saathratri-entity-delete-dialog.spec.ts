import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../cass-saathratri-entity.test-samples';
import { CassSaathratriEntityService } from '../service/cass-saathratri-entity.service';

import { CassSaathratriEntityDeleteDialogComponent } from './cass-saathratri-entity-delete-dialog';

describe('CassSaathratriEntity Management Delete Component', () => {
  let comp: CassSaathratriEntityDeleteDialogComponent;
  let fixture: ComponentFixture<CassSaathratriEntityDeleteDialogComponent>;
  let service: CassSaathratriEntityService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NgbActiveModal],
    });
    fixture = TestBed.createComponent(CassSaathratriEntityDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CassSaathratriEntityService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('should call delete service on confirmDelete', () => {
      // GIVEN
      vitest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse<{}>()));
      vitest.spyOn(mockActiveModal, 'close');

      // WHEN
      comp.confirmDelete(sampleWithRequiredData.entityId);

      // THEN
      expect(service.delete).toHaveBeenCalledWith(sampleWithRequiredData.entityId);
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
