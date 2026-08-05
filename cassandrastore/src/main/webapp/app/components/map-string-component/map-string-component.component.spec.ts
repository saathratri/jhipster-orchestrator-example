/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { beforeEach, describe, expect, it } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapStringComponent } from './map-string-component.component';

describe('MapStringComponent', () => {
  let component: MapStringComponent;
  let fixture: ComponentFixture<MapStringComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapStringComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MapStringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
