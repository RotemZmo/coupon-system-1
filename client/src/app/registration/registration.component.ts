import {Component, OnDestroy, OnInit} from '@angular/core'
import {FormControl, FormGroup, Validators} from '@angular/forms'
import {ClientType, User} from "../models/user"
import {Subscription} from "rxjs"
import {AuthorisationService} from "../shared/services/authorisation.service"
import {MyErrorStateMatcher} from "../models/errorStateMatcher"
import {MatSnackBar} from "@angular/material"

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../shared/layouts/authorisation/authorisation.component.css']
})
export class RegistrationComponent implements OnInit, OnDestroy {

  private sub: Subscription

  form: FormGroup

  matcher = new MyErrorStateMatcher()

  clients: ClientType[] = [
    {value: 'COMPANY'},
    {value: 'CUSTOMER'}
  ]

  constructor(private auth: AuthorisationService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.form = new FormGroup({

      email: new FormControl(null, [
        Validators.required,
        Validators.email,
        Validators.maxLength(20)
      ]),

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

    let regUser: User = {
      name: this.form.value.name,
      password: this.form.value.password,
      email: this.form.value.email
    }

    if (this.form.value.clientType === 'COMPANY') {
      this.sub = this.auth.regCompany(regUser).subscribe(
        () => this.snackBar.open('Successful registration.', '', {duration: 3000}),
        error => {
          this.snackBar.open(error, '', {duration: 3000})
          this.form.enable()
        }
      )
    } else if (this.form.value.clientType === 'CUSTOMER') {
      this.sub = this.auth.regCustomer(regUser).subscribe(
        () => this.snackBar.open('Successful registration.', '', {duration: 3000}),
        error => {
          this.snackBar.open(error, '', {duration: 3000})
          this.form.enable()
        }
      )
    }
  }

}
