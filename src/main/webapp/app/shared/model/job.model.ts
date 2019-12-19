import { IService } from 'app/shared/model/service.model';

export interface IJob {
  id?: number;
  jobTitle?: string;
  services?: IService[];
}

export class Job implements IJob {
  constructor(public id?: number, public jobTitle?: string, public services?: IService[]) {}
}
