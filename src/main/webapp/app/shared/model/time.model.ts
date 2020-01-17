import { Moment } from 'moment';
import { IClient } from 'app/shared/model/client.model';
import { IJob } from 'app/shared/model/job.model';
import { ISalon } from 'app/shared/model/salon.model';

export interface ITime {
  id?: number;
  date?: Moment;
  duration?: number;
  client?: IClient;
  job?: IJob;
  salon?: ISalon;
}

export class Time implements ITime {
  constructor(
    public id?: number,
    public date?: Moment,
    public duration?: number,
    public client?: IClient,
    public job?: IJob,
    public salon?: ISalon
  ) {}
}
