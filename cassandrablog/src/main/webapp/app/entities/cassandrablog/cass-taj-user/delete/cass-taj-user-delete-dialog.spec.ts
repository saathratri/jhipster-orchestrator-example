import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../cass-taj-user.test-samples';
import { CassTajUserService } from '../service/cass-taj-user.service';

import { CassTajUserDeleteDialogComponent } from './cass-taj-user-delete-dialog';

describe('CassTajUser Management Delete Component', () => {
  let comp: CassTajUserDeleteDialogComponent;
  let fixture: ComponentFixture<CassTajUserDeleteDialogComponent>;
  let service: CassTajUserService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NgbActiveModal],
    });
    fixture = TestBed.createComponent(CassTajUserDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CassTajUserService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('should call delete service on confirmDelete', () => {
      // GIVEN
      vitest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse<{}>()));
      vitest.spyOn(mockActiveModal, 'close');

      // WHEN
      comp.confirmDelete(sampleWithRequiredData.id);

      // THEN
      expect(service.delete).toHaveBeenCalledWith(sampleWithRequiredData.id);
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
