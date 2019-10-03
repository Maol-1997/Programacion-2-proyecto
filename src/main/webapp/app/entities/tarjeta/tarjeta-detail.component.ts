import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarjeta } from 'app/shared/model/tarjeta.model';

@Component({
  selector: 'jhi-tarjeta-detail',
  templateUrl: './tarjeta-detail.component.html'
})
export class TarjetaDetailComponent implements OnInit {
  tarjeta: ITarjeta;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tarjeta }) => {
      this.tarjeta = tarjeta;
    });
  }

  previousState() {
    window.history.back();
  }
}
