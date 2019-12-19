import { IJob } from 'app/shared/model/job.model';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  patronomic?: string;
  experience?: string;
  job?: IJob;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public patronomic?: string,
    public experience?: string,
    public job?: IJob
  ) {}
}
