import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material'  
import { registerLocaleData } from '@angular/common';
import localeES from '@angular/common/locales/es';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NO_ERRORS_SCHEMA,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MatDatepickerModule } from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { LoginComponent } from './usuarios/login.component';
import { AuthGuard } from './usuarios/guards/auth.guard';
import { RoleGuard } from './usuarios/guards/role.guard';
import { TokenInterceptor } from './usuarios/interceptors/token.interceptor';
import { AuthInterceptor } from './usuarios/interceptors/auth.interceptor';
import { TipocambioComponent } from './tipocambio/tipocambio.component';
import { ActualizartipocambioComponent } from './actualizartipocambio/actualizartipocambio.component';
import { ReactiveFormsModule } from '@angular/forms';

registerLocaleData(localeES, 'es');

const routes: Routes = [
  { path: '', redirectTo: '/tipocambio', pathMatch: 'full' },
  { path: 'tipocambio', component: TipocambioComponent },
  { path: 'actualizartipocambio', component: ActualizartipocambioComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    TipocambioComponent,
    ActualizartipocambioComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatTableModule,
    FormsModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule, MatDatepickerModule, MatMomentDateModule
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'es' },
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },],
  bootstrap: [AppComponent],
  schemas: [
      CUSTOM_ELEMENTS_SCHEMA,
     // NO_ERRORS_SCHEMA
    ]
})
export class AppModule { }
