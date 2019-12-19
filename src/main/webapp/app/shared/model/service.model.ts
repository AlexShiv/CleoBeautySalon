import { ITime } from 'app/shared/model/time.model';
import { IJob } from 'app/shared/model/job.model';

export interface IService {
  id?: number;
  serviceName?: string;
  maxDuration?: number;
  price?: number;
  times?: ITime[];
  job?: IJob;
}

export class Service implements IService {
  constructor(
    public id?: number,
    public serviceName?: string,
    public maxDuration?: number,
    public price?: number,
    public times?: ITime[],
    public job?: IJob
  ) {}
}
