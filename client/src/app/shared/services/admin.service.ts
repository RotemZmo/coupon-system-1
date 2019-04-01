import {Injectable} from '@angular/core'
import {HttpClient} from "@angular/common/http"
import {Observable} from "rxjs"
import {catchError} from "rxjs/operators"
import {ErrorService} from "./error.service"
import {Company} from "../../models/company"
import {Customer} from "../../models/customer"
import {Coupon} from "../../models/coupon"
import {Income} from "../../models/income"

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private companyUrl = 'http://localhost:8080/admin/companies'
  private couponUrl = 'http://localhost:8080/admin/coupons'
  private customerUrl = 'http://localhost:8080/admin/customers'
  private incomeUrl = 'http://localhost:8080/admin/incomes'

  constructor(private http: HttpClient, private errorService: ErrorService) {
  }

  public createCompany(company: Company): Observable<Company> {
    return this.http.post<Company>(this.companyUrl, company, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public deleteCompany(id: number): Observable<Company> {
    return this.http.delete<Company>(this.companyUrl + '/' + id, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public updateCompany(company: Company): Observable<Company> {
    return this.http.put<Company>(this.companyUrl, company, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getAllCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(this.companyUrl, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public createCoupon(coupon: Coupon): Observable<Coupon> {
    return this.http.post<Coupon>(this.couponUrl + '/' + coupon.companyId, coupon, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public deleteCoupon(id: number): Observable<Coupon> {
    return this.http.delete<Coupon>(this.couponUrl + '/' + id, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public updateCoupon(coupon: Coupon): Observable<Coupon> {
    return this.http.put<Coupon>(this.couponUrl + '/' + coupon.companyId, coupon, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getAllCoupons(): Observable<Coupon[]> {
    return this.http.get<Coupon[]>(this.couponUrl, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public createCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.customerUrl, customer, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public deleteCustomer(id: number): Observable<Customer> {
    return this.http.delete<Customer>(this.customerUrl + '/' + id, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public updateCustomer(customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(this.customerUrl, customer, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getAllCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.customerUrl, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getIncomes(): Observable<Income[]> {
    return this.http.get<Income[]>(this.incomeUrl, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

}
