import {Injectable} from '@angular/core'
import {HttpClient} from "@angular/common/http"
import {ErrorService} from "./error.service"
import {Observable} from "rxjs"
import {catchError} from "rxjs/operators"
import {Coupon} from "../../models/coupon"

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private companyUrl = 'http://localhost:8080/company/coupons'

  constructor(private http: HttpClient, private errorService: ErrorService) {
  }

  public createCoupon(coupon: Coupon): Observable<Coupon> {
    return this.http.post<Coupon>(this.companyUrl, coupon, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public deleteCoupon(id: number): Observable<Coupon> {
    return this.http.delete<Coupon>(this.companyUrl + '/' + id, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public updateCoupon(coupon: Coupon): Observable<Coupon> {
    return this.http.put<Coupon>(this.companyUrl, coupon, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getAllCoupons(): Observable<Coupon[]> {
    return this.http.get<Coupon[]>(this.companyUrl, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

}
