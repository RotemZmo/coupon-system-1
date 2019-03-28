import {Component, OnInit} from '@angular/core'
import {FormControl, FormGroup, Validators} from '@angular/forms'
import {AdminService} from "../../shared/services/admin.service"
import {MyErrorStateMatcher} from "../../models/errorStateMatcher"
import {MatSnackBar} from "@angular/material"

@Component({
  selector: 'app-create-customer',
  templateUrl: './create-customer.component.html',
  styleUrls: ['../../../app/shared/layouts/admin/admin.component.css']
})
export class CreateCustomerComponent implements OnInit{

  form: FormGroup

  matcher = new MyErrorStateMatcher()

  constructor(private admin: AdminService,
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
      ])

    })
  }

  onSubmit() {
    this.admin.createCustomer(this.form.value).subscribe(
      () => this.snackBar.open('New customer successfully added.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

}
