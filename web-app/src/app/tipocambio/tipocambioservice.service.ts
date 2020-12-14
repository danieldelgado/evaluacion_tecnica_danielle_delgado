import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TipoCambio } from './tipocambio';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TipocambioserviceService {

  private urlEndPoint: string = environment.server ;

  constructor(private http: HttpClient, private router: Router) { }

  getExchangers(): Observable<any> {
    return this.http.get(this.urlEndPoint + '/api/exchange');
  }

  getCurrencies(): Observable<any> {
    return this.http.get(this.urlEndPoint + '/api/currency');
  }

  updateExchange(data): Observable<any> {
    return this.http.patch(this.urlEndPoint + '/api/exchange', data);
  }


}
