import { ITarjeta } from 'app/shared/model/tarjeta.model';
import { IUser } from 'app/core/user/user.model';

export interface ICliente {
  id?: number;
  nombre?: string;
  apellido?: string;
  tarjetas?: ITarjeta[];
  user?: IUser;
}

export class Cliente implements ICliente {
  constructor(public id?: number, public nombre?: string, public apellido?: string, public tarjetas?: ITarjeta[], public user?: IUser) {}
}
