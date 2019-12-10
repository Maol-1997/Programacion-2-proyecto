export interface ICompra {
  id?: number;
  precio?: number;
  tarjetaId?: number;
}

export class Compra implements ICompra {
  constructor(public id?: number, public precio?: number, public tarjetaId?: number) {}
}
