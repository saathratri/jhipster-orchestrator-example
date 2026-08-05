/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { beforeEach, describe, expect, it } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { SetStringEditDialogComponent } from './set-string-edit-dialog-component.component';

describe('SetStringEditDialogComponent', () => {
  let component: SetStringEditDialogComponent;
  let fixture: ComponentFixture<SetStringEditDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SetStringEditDialogComponent],
      providers: [
        { provide: MatDialogRef, useValue: { close() {} } },
        { provide: MAT_DIALOG_DATA, useValue: { value: 'a' } },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(SetStringEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
