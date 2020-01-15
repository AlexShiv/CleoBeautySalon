import { ITime } from 'app/shared/model/time.model';

export interface ISalon {
  id?: number;
  address?: string;
  phone?: string;
  times?: ITime[];
}

export class Salon implements ISalon {
  constructor(public id?: number, public address?: string, public phone?: string, public times?: ITime[]) {}
}
