import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITarjeta, Tarjeta } from 'app/shared/model/tarjeta.model';
import { TarjetaService } from './tarjeta.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-tarjeta-update',
  templateUrl: './tarjeta-update.component.html'
})
export class TarjetaUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];

  editForm = this.fb.group({
    id: [],
    ultDigitos: [null, [Validators.required]],
    token: [null, [Validators.required]],
    alta: [null, [Validators.required]],
    cliente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tarjetaService: TarjetaService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tarjeta }) => {
      this.updateForm(tarjeta);
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tarjeta: ITarjeta) {
    this.editForm.patchValue({
      id: tarjeta.id,
      ultDigitos: tarjeta.ultDigitos,
      token: tarjeta.token,
      alta: tarjeta.alta,
      cliente: tarjeta.cliente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tarjeta = this.createFromForm();
    if (tarjeta.id !== undefined) {
      this.subscribeToSaveResponse(this.tarjetaService.update(tarjeta));
    } else {
      this.subscribeToSaveResponse(this.tarjetaService.create(tarjeta));
    }
  }

  private createFromForm(): ITarjeta {
    return {
      ...new Tarjeta(),
      id: this.editForm.get(['id']).value,
      ultDigitos: this.editForm.get(['ultDigitos']).value,
      token: this.editForm.get(['token']).value,
      alta: this.editForm.get(['alta']).value,
      cliente: this.editForm.get(['cliente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarjeta>>) {
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }
}
