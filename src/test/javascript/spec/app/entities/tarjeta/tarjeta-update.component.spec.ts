import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PrincipalTestModule } from '../../../test.module';
import { TarjetaUpdateComponent } from 'app/entities/tarjeta/tarjeta-update.component';
import { TarjetaService } from 'app/entities/tarjeta/tarjeta.service';
import { Tarjeta } from 'app/shared/model/tarjeta.model';

describe('Component Tests', () => {
  describe('Tarjeta Management Update Component', () => {
    let comp: TarjetaUpdateComponent;
    let fixture: ComponentFixture<TarjetaUpdateComponent>;
    let service: TarjetaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrincipalTestModule],
        declarations: [TarjetaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TarjetaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarjetaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarjetaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tarjeta(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tarjeta();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
