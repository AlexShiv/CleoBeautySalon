import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPromo } from 'app/shared/model/promo.model';

type EntityResponseType = HttpResponse<IPromo>;
type EntityArrayResponseType = HttpResponse<IPromo[]>;

@Injectable({ providedIn: 'root' })
export class PromoService {
  public resourceUrl = SERVER_API_URL + 'api/promos';

  constructor(protected http: HttpClient) {}

  create(promo: IPromo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(promo);
    return this.http
      .post<IPromo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(promo: IPromo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(promo);
    return this.http
      .put<IPromo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPromo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPromo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(promo: IPromo): IPromo {
    const copy: IPromo = Object.assign({}, promo, {
      startDate: promo.startDate != null && promo.startDate.isValid() ? promo.startDate.toJSON() : null,
      endDate: promo.endDate != null && promo.endDate.isValid() ? promo.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((promo: IPromo) => {
        promo.startDate = promo.startDate != null ? moment(promo.startDate) : null;
        promo.endDate = promo.endDate != null ? moment(promo.endDate) : null;
      });
    }
    return res;
  }
}
