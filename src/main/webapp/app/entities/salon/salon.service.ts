import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISalon } from 'app/shared/model/salon.model';

type EntityResponseType = HttpResponse<ISalon>;
type EntityArrayResponseType = HttpResponse<ISalon[]>;

@Injectable({ providedIn: 'root' })
export class SalonService {
  public resourceUrl = SERVER_API_URL + 'api/salons';

  constructor(protected http: HttpClient) {}

  create(salon: ISalon): Observable<EntityResponseType> {
    return this.http.post<ISalon>(this.resourceUrl, salon, { observe: 'response' });
  }

  update(salon: ISalon): Observable<EntityResponseType> {
    return this.http.put<ISalon>(this.resourceUrl, salon, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISalon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISalon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
