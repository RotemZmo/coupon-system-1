import {Component, OnDestroy, OnInit} from '@angular/core'
import {FormControl, FormGroup, Validators} from '@angular/forms'
import {AuthorisationService} from "../shared/services/authorisation.service"
import {ClientType} from "../models/user"
import {Subscription} from "rxjs"
import {Router} from "@angular/router"
import {MyErrorStateMatcher} from "../models/errorStateMatcher"
import {MatSnackBar} from "@angular/material"

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../shared/layouts/authorisation/authorisation.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  private sub: Subscription

  form: FormGroup

  matcher = new MyErrorStateMatcher()

  clients: ClientType[] = [
    {value: 'ADMIN'},
    {value: 'COMPANY'},
    {value: 'CUSTOMER'}
  ]

  constructor(private auth: AuthorisationService,
              private router: Router,
              private snackBar: MatSnackBar
  ) {
  }

  ngOnInit() {
    this.form = new FormGroup({

      name: new FormControl(null, [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(20)
      ]),

      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(20)
      ]),

      clientType: new FormControl(null, [
        Validators.required
      ])

    })
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe()
    }
  }

  onSubmit() {
    this.form.disable()
    this.sub = this.auth.login(this.form.value).subscribe(
      () => {
        if (this.form.value.clientType === 'ADMIN') {
          this.router.navigate(['/admin'])
        } else if (this.form.value.clientType === 'COMPANY') {
          this.router.navigate(['/company'])
        } else if (this.form.value.clientType === 'CUSTOMER') {
          this.router.navigate(['/customer'])
        }
      },
      error => {
        this.snackBar.open(error, '', {duration: 3000})
        this.form.enable()
      }
    )
  }
}
