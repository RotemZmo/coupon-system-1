import {BrowserModule} from '@angular/platform-browser'
import {NgModule} from '@angular/core'
import {BrowserAnimationsModule} from '@angular/platform-browser/animations'
import {AppRoutingModule} from './app-routing.module'
import {NgProgressModule} from '@ngx-progressbar/core'
import {NgProgressRouterModule} from '@ngx-progressbar/router'
import {ServiceWorkerModule} from '@angular/service-worker'
import {HttpClientModule} from '@angular/common/http'
import {FormsModule, ReactiveFormsModule} from '@angular/forms'
import {environment} from '../environments/environment'

import {AppComponent} from './app.component'
import {SharedModule} from './shared/shared.module'
import {AuthorisationComponent} from './shared/layouts/authorisation/authorisation.component'
import {LoginComponent} from './login/login.component'
import {RegistrationComponent} from './registration/registration.component'
import {AdminComponent} from './shared/layouts/admin/admin.component'
import {CompanyComponent} from './shared/layouts/company/company.component'
import {CustomerComponent} from './shared/layouts/customer/customer.component'
import {CreateCompanyComponent} from './admin/create-company/create-company.component'
import {GetCompaniesComponent} from './admin/get-companies/get-companies.component'
import {CreateCustomerComponent} from './admin/create-customer/create-customer.component'
import {GetCustomersComponent} from './admin/get-customers/get-customers.component'
import {CreateCompanyCouponComponent} from './company/create-company-coupon/create-company-coupon.component'
import {GetCompanyCouponsComponent} from './company/get-company-coupons/get-company-coupons.component'
import {PurchaseCouponComponent} from './customer/purchase-coupon/purchase-coupon.component'
import {GetCustomerCouponsComponent} from './customer/get-customer-coupons/get-customer-coupons.component'
import {GetCouponsComponent} from './admin/get-coupons/get-coupons.component'
import {CreateCouponComponent} from "./admin/create-coupon/create-coupon.component"
import {Page404Component} from "./shared/page404/page404.component"

@NgModule({
  declarations: [
    AppComponent,
    Page404Component,
    AuthorisationComponent,
    LoginComponent,
    RegistrationComponent,
    AdminComponent,
    CreateCompanyComponent,
    GetCompaniesComponent,
    CreateCouponComponent,
    GetCouponsComponent,
    CreateCustomerComponent,
    GetCustomersComponent,
    CompanyComponent,
    CreateCompanyCouponComponent,
    GetCompanyCouponsComponent,
    CustomerComponent,
    PurchaseCouponComponent,
    GetCustomerCouponsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    SharedModule,
    AppRoutingModule,
    NgProgressModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgProgressRouterModule,
    ServiceWorkerModule.register('/ngsw-worker.js', {enabled: environment.production})

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
