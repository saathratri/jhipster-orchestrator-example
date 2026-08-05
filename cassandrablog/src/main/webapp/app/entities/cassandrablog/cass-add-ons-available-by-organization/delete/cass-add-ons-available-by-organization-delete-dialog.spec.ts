import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../cass-add-ons-available-by-organization.test-samples';
import { CassAddOnsAvailableByOrganizationService } from '../service/cass-add-ons-available-by-organization.service';

import { CassAddOnsAvailableByOrganizationDeleteDialogComponent } from './cass-add-ons-available-by-organization-delete-dialog';

describe('CassAddOnsAvailableByOrganization Management Delete Component', () => {
  let comp: CassAddOnsAvailableByOrganizationDeleteDialogComponent;
  let fixture: ComponentFixture<CassAddOnsAvailableByOrganizationDeleteDialogComponent>;
  let service: CassAddOnsAvailableByOrganizationService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NgbActiveModal],
    });
    fixture = TestBed.createComponent(CassAddOnsAvailableByOrganizationDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CassAddOnsAvailableByOrganizationService);
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
