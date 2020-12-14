import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualizartipocambioComponent } from './actualizartipocambio.component';

describe('ActualizartipocambioComponent', () => {
  let component: ActualizartipocambioComponent;
  let fixture: ComponentFixture<ActualizartipocambioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActualizartipocambioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActualizartipocambioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
