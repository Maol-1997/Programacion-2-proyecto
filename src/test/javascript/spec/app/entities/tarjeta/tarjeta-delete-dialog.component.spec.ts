import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrincipalTestModule } from '../../../test.module';
import { TarjetaDeleteDialogComponent } from 'app/entities/tarjeta/tarjeta-delete-dialog.component';
import { TarjetaService } from 'app/entities/tarjeta/tarjeta.service';

describe('Component Tests', () => {
  describe('Tarjeta Management Delete Component', () => {
    let comp: TarjetaDeleteDialogComponent;
    let fixture: ComponentFixture<TarjetaDeleteDialogComponent>;
    let service: TarjetaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrincipalTestModule],
        declarations: [TarjetaDeleteDialogComponent]
      })
        .overrideTemplate(TarjetaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarjetaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarjetaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
