import {Injectable} from '@angular/core'
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router'
import {Observable, of} from 'rxjs'
import {AuthorisationService} from "../services/authorisation.service"

@Injectable({
  providedIn: 'root'
})
export class CustomerAuthGuard implements CanActivate, CanActivateChild {

  constructor(private auth: AuthorisationService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    if (this.auth.isCustomerLoggedIn()) {
      return of(true)
    } else {
      this.router.navigate(['/login'])
      return of(false)
    }
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.canActivate(childRoute, state)
  }

}
