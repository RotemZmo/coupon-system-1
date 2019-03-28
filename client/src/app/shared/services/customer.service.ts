import {Injectable} from '@angular/core'
import {HttpClient} from "@angular/common/http"
import {ErrorService} from "./error.service"
import {Observable} from "rxjs"
import {catchError} from "rxjs/operators"
import {Coupon} from "../../models/coupon"

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private couponsUrl = 'http://localhost:8080/customer/coupons'
  private availableUrl = 'http://localhost:8080/customer/coupons-available'

  constructor(private http: HttpClient, private errorService: ErrorService) {
  }

  public purchaseCoupon(id: number): Observable<Coupon> {
    return this.http.get<Coupon>(this.couponsUrl + "/" + id, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getPurchasedCoupons(): Observable<Coupon[]> {
    return this.http.get<Coupon[]>(this.couponsUrl, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getAvailableCoupons(): Observable<Coupon[]> {
    return this.http.get<Coupon[]>(this.availableUrl, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

}
