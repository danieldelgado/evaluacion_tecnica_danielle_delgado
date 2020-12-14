import { Component, OnInit } from '@angular/core';
import { TipocambioserviceService } from './tipocambioservice.service';
import { TipoCambio } from './tipocambio';
import swal from 'sweetalert2';
import { ExchangesService } from './exchanges.service';

@Component({
  selector: 'app-tipocambio',
  templateUrl: './tipocambio.component.html',
  styleUrls: ['./tipocambio.component.css']
})
export class TipocambioComponent implements OnInit {

  dataSource;

  listMonedas = [];
  listTipoCambio: TipoCambio[] = [];

  constructor(private tipocambioserviceService: TipocambioserviceService,
    private exchangesService: ExchangesService) { }

  ngOnInit() {
    this.tipocambioserviceService.getCurrencies().subscribe(res => {
      this.listMonedas = res.data;
    });

    this.tipocambioserviceService.getExchangers().subscribe(res => {
      this.listTipoCambio = res.data;
    });
  }

  ingresandoMonto(event: any) {
    try {
      let va2 = Math.floor(event.target.value);
      if (Number.isNaN(va2) || va2 < -0) {
        swal('Error monto', 'Monto ingresado es erroneo', 'error');
      }
      let listPromises = [];

      this.listTipoCambio.forEach(element => {
        listPromises.push(this.getPromise(element, va2));
      });

      Promise.all(listPromises)
        .then(result => {

          console.log("result");
          console.log(result);

          result.forEach(element => {
            this.listTipoCambio.forEach(element2 => {
              if (element.id == element2.id) {
                element2.exchangeAmount = Number((Math.round(element.exchangeAmount * 100) / 100).toFixed(2)) ;
                element2.mountorigin = Number((Math.round(va2 * 100) / 100).toFixed(2)) ;
              }
            });
          });
          /*console.log("result");
          console.log(JSON.stringify(result));
          console.log("this.listTipoCambio");
          console.log(JSON.stringify(this.listTipoCambio));*/
        })

    } catch (error) {
      console.log("no se puede cambiar")

    }
  }

  getPromise(element, val2) {
    return new Promise((resolve, reject) => {
      let originCurrencyId = null;
      let exchangeCurrencyId = null;
      this.listMonedas.forEach(moneda => {
        if (element.origin == moneda.currency) {
          originCurrencyId = moneda.id;
        }
        if (element.exchange == moneda.currency) {
          exchangeCurrencyId = moneda.id;
        }
      });
      this.exchangesService.exchange({
        "originCurrencyId": originCurrencyId,
        "exchangeCurrencyId": exchangeCurrencyId,
        "amount": val2
      }).subscribe(res => {
        resolve(res.data);
      })
    });
  }

}
