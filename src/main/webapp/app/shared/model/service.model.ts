import { IJob } from 'app/shared/model/job.model';

export interface IService {
  id?: number;
  serviceName?: string;
  maxDuration?: number;
  price?: number;
  job?: IJob;
}

export class Service implements IService {
  constructor(public id?: number, public serviceName?: string, public maxDuration?: number, public price?: number, public job?: IJob) {}
}
