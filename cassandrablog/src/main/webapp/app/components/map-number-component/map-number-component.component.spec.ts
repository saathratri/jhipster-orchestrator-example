/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { beforeEach, describe, expect, it } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapNumberComponent } from './map-number-component.component';

describe('MapNumberComponent', () => {
  let component: MapNumberComponent;
  let fixture: ComponentFixture<MapNumberComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapNumberComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MapNumberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
