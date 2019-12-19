import { Moment } from 'moment';

export interface IPromo {
  id?: number;
  promoName?: string;
  description?: number;
  startDate?: Moment;
  endDate?: Moment;
  sale?: number;
}

export class Promo implements IPromo {
  constructor(
    public id?: number,
    public promoName?: string,
    public description?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public sale?: number
  ) {}
}
