import { ICliente } from 'app/shared/model/cliente.model';

export interface ITarjeta {
  id?: number;
  ultDigitos?: number;
  token?: string;
  alta?: boolean;
  cliente?: ICliente;
}

export class Tarjeta implements ITarjeta {
  constructor(public id?: number, public ultDigitos?: number, public token?: string, public alta?: boolean, public cliente?: ICliente) {
    this.alta = this.alta || false;
  }
}
