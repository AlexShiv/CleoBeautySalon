import { ITime } from 'app/shared/model/time.model';

export interface IClient {
  id?: number;
  name?: string;
  phone?: string;
  times?: ITime[];
}

export class Client implements IClient {
  constructor(public id?: number, public name?: string, public phone?: string, public times?: ITime[]) {}
}
