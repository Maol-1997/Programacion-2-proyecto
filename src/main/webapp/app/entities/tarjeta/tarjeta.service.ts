import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarjeta } from 'app/shared/model/tarjeta.model';

type EntityResponseType = HttpResponse<ITarjeta>;
type EntityArrayResponseType = HttpResponse<ITarjeta[]>;

@Injectable({ providedIn: 'root' })
export class TarjetaService {
  public resourceUrl = SERVER_API_URL + 'api/tarjetas';

  constructor(protected http: HttpClient) {}

  create(tarjeta: ITarjeta): Observable<EntityResponseType> {
    return this.http.post<ITarjeta>(this.resourceUrl, tarjeta, { observe: 'response' });
  }

  update(tarjeta: ITarjeta): Observable<EntityResponseType> {
    return this.http.put<ITarjeta>(this.resourceUrl, tarjeta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarjeta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarjeta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
