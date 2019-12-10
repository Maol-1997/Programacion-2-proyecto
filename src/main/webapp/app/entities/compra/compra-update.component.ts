import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompra, Compra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';
import { ITarjeta } from 'app/shared/model/tarjeta.model';
import { TarjetaService } from 'app/entities/tarjeta/tarjeta.service';

@Component({
  selector: 'jhi-compra-update',
  templateUrl: './compra-update.component.html'
})
export class CompraUpdateComponent implements OnInit {
  isSaving: boolean;

  tarjetas: ITarjeta[];

  editForm = this.fb.group({
    id: [],
    precio: [null, [Validators.required]],
    tarjetaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compraService: CompraService,
    protected tarjetaService: TarjetaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compra }) => {
      this.updateForm(compra);
    });
    this.tarjetaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITarjeta[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITarjeta[]>) => response.body)
      )
      .subscribe((res: ITarjeta[]) => (this.tarjetas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(compra: ICompra) {
    this.editForm.patchValue({
      id: compra.id,
      precio: compra.precio,
      tarjetaId: compra.tarjetaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compra = this.createFromForm();
    if (compra.id !== undefined) {
      this.subscribeToSaveResponse(this.compraService.update(compra));
    } else {
      this.subscribeToSaveResponse(this.compraService.create(compra));
    }
  }

  private createFromForm(): ICompra {
    return {
      ...new Compra(),
      id: this.editForm.get(['id']).value,
      precio: this.editForm.get(['precio']).value,
      tarjetaId: this.editForm.get(['tarjetaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompra>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTarjetaById(index: number, item: ITarjeta) {
    return item.id;
  }
}
