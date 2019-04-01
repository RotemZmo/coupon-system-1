import {Injectable} from '@angular/core'
import {HttpClient} from "@angular/common/http"
import {Observable} from "rxjs"
import {User} from "../../models/user"
import {ErrorService} from "./error.service"
import {catchError, tap} from "rxjs/operators"
import {Router} from "@angular/router"

@Injectable({
  providedIn: 'root'
})
export class AuthorisationService {

  private logUrl = 'http://localhost:8080/login'
  private regCompanyUrl = 'http://localhost:8080/registration/companies'
  private regCustomerUrl = 'http://localhost:8080/registration/customers'

  constructor(private http: HttpClient,
              private errorService: ErrorService,
              private router: Router) {
  }

  public login(logUser: User): Observable<User> {
    return this.http.post<User>(this.logUrl, logUser, {withCredentials: true}).pipe(
      tap(() => {
        if (logUser.clientType === "ADMIN") {
          sessionStorage.setItem("adminLogged", logUser.clientType)
        } else if (logUser.clientType === "COMPANY") {
          sessionStorage.setItem("companyLogged", logUser.clientType)
        } else if (logUser.clientType === "CUSTOMER") {
          sessionStorage.setItem("customerLogged", logUser.clientType)
        }
      }),
      catchError(err => this.errorService.errorHandler(err)))
  }

  public regCompany(regUser: User): Observable<User> {
    return this.http.post<User>(this.regCompanyUrl, regUser, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public regCustomer(regUser: User): Observable<User> {
    return this.http.post<User>(this.regCustomerUrl, regUser, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public isAdminLoggedIn() {
    if (sessionStorage.getItem("adminLogged") === "ADMIN") {
      return true
    }
  }

  public isCompanyLoggedIn() {
    if (sessionStorage.getItem("companyLogged") === "COMPANY") {
      return true
    }
  }

  public isCustomerLoggedIn() {
    if (sessionStorage.getItem("customerLogged") === "CUSTOMER") {
      return true
    }
  }

  public logout() {
    sessionStorage.clear()
    this.router.navigate(['/login'])
  }

}
