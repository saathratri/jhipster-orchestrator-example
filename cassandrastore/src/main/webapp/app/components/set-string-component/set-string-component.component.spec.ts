/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { beforeEach, describe, expect, it } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SetStringComponent } from './set-string-component.component';

describe('SetStringComponent', () => {
  let component: SetStringComponent;
  let fixture: ComponentFixture<SetStringComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SetStringComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SetStringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
