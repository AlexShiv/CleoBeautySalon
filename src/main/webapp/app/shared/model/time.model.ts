import { Moment } from 'moment';
import { IClient } from 'app/shared/model/client.model';
import { IService } from 'app/shared/model/service.model';

export interface ITime {
  id?: number;
  date?: Moment;
  phone?: string;
  client?: IClient;
  service?: IService;
}

export class Time implements ITime {
  constructor(public id?: number, public date?: Moment, public phone?: string, public client?: IClient, public service?: IService) {}
}
