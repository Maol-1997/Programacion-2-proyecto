import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarjeta } from 'app/shared/model/tarjeta.model';
import { TarjetaService } from './tarjeta.service';

@Component({
  selector: 'jhi-tarjeta-delete-dialog',
  templateUrl: './tarjeta-delete-dialog.component.html'
})
export class TarjetaDeleteDialogComponent {
  tarjeta: ITarjeta;

  constructor(protected tarjetaService: TarjetaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tarjetaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tarjetaListModification',
        content: 'Deleted an tarjeta'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tarjeta-delete-popup',
  template: ''
})
export class TarjetaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tarjeta }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TarjetaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tarjeta = tarjeta;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tarjeta', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tarjeta', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
