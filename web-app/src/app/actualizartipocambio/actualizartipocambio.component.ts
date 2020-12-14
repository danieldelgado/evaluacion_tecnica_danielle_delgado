import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ExchangesService } from '../tipocambio/exchanges.service';
import { TipoCambio } from '../tipocambio/tipocambio';
import { TipocambioserviceService } from '../tipocambio/tipocambioservice.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-actualizartipocambio',
  templateUrl: './actualizartipocambio.component.html',
  styleUrls: ['./actualizartipocambio.component.css']
})
export class ActualizartipocambioComponent implements OnInit {

  registerForm: FormGroup;
  submitted = false;
  listTipoCambio: TipoCambio[] = [];

  constructor(private tipocambioserviceService: TipocambioserviceService,
    private exchangesService: ExchangesService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      id: ['', Validators.required],
      typeExchange: ['', Validators.required]
    });

    this.tipocambioserviceService.getExchangers().subscribe(res => {
      this.listTipoCambio = res.data;
    });

  }

  get f() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    console.log(this.registerForm.value)

    this.tipocambioserviceService.updateExchange(this.registerForm.value).subscribe(res => {
      swal('Cambio realizado', 'Tipo de cambio realizado');
    });

  }


}
