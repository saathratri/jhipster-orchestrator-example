/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { beforeEach, describe, expect, it } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapDayjsComponent } from './map-dayjs-component.component';

describe('MapDayjsComponent', () => {
  let component: MapDayjsComponent;
  let fixture: ComponentFixture<MapDayjsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapDayjsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MapDayjsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
