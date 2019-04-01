import {NgModule} from '@angular/core'
import {RouterModule, Routes} from '@angular/router'
import {AdminComponent} from './shared/layouts/admin/admin.component'
import {LoginComponent} from './login/login.component'
import {CompanyComponent} from './shared/layouts/company/company.component'
import {CustomerComponent} from './shared/layouts/customer/customer.component'
import {AuthorisationComponent} from './shared/layouts/authorisation/authorisation.component'
import {RegistrationComponent} from './registration/registration.component'
import {CreateCompanyComponent} from './admin/create-company/create-company.component'
import {GetCompaniesComponent} from './admin/get-companies/get-companies.component'
import {CreateCustomerComponent} from './admin/create-customer/create-customer.component'
import {GetCustomersComponent} from './admin/get-customers/get-customers.component'
import {CreateCompanyCouponComponent} from './company/create-company-coupon/create-company-coupon.component'
import {GetCompanyCouponsComponent} from './company/get-company-coupons/get-company-coupons.component'
import {PurchaseCouponComponent} from './customer/purchase-coupon/purchase-coupon.component'
import {GetCustomerCouponsComponent} from './customer/get-customer-coupons/get-customer-coupons.component'
import {CreateCouponComponent} from "./admin/create-coupon/create-coupon.component"
import {GetCouponsComponent} from "./admin/get-coupons/get-coupons.component"
import {AdminAuthGuard} from "./shared/guards/admin-auth.guard"
import {Page404Component} from "./shared/page404/page404.component"
import {CompanyAuthGuard} from "./shared/guards/company-auth.guard"
import {CustomerAuthGuard} from "./shared/guards/customer-auth.guard"
import {GetIncomesComponent} from "./admin/get-incomes/get-incomes.component"

const routes: Routes = [
  {
    path: '', component: AuthorisationComponent, children: [
      {path: '', redirectTo: 'login', pathMatch: 'full'},
      {path: 'login', component: LoginComponent},
      {path: 'registration', component: RegistrationComponent}
    ]
  },
  {
    path: 'admin', component: AdminComponent, canActivate: [AdminAuthGuard], children: [
      {path: 'create-company', component: CreateCompanyComponent},
      {path: 'get-companies', component: GetCompaniesComponent},
      {path: 'create-coupon', component: CreateCouponComponent},
      {path: 'get-coupons', component: GetCouponsComponent},
      {path: 'create-customer', component: CreateCustomerComponent},
      {path: 'get-customers', component: GetCustomersComponent},
      {path: 'get-incomes', component: GetIncomesComponent}
    ]
  },
  {
    path: 'company', component: CompanyComponent, canActivate: [CompanyAuthGuard], children: [
      {path: 'create-coupon', component: CreateCompanyCouponComponent},
      {path: 'my-coupons', component: GetCompanyCouponsComponent}
    ]
  },
  {
    path: 'customer', component: CustomerComponent, canActivate: [CustomerAuthGuard], children: [
      {path: 'purchase-coupon', component: PurchaseCouponComponent},
      {path: 'my-coupons', component: GetCustomerCouponsComponent}
    ]
  },
  {path: '**', component: Page404Component}

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
