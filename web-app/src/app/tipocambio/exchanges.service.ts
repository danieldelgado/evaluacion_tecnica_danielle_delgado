import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TipoCambio } from './tipocambio';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExchangesService {

  private urlEndPoint: string = environment.server ;

  constructor(private http: HttpClient, private router: Router) { }

  exchange(data): Observable<any> {
    return this.http.post(this.urlEndPoint + '/api/exchange',data);
  }


}
