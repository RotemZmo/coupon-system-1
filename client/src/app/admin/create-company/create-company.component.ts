import {Component, OnInit} from '@angular/core'
import {FormControl, FormGroup, Validators} from '@angular/forms'
import {AdminService} from "../../shared/services/admin.service"
import {MatSnackBar} from "@angular/material"
import {MyErrorStateMatcher} from "../../models/errorStateMatcher"

@Component({
  selector: 'app-create-company',
  templateUrl: './create-company.component.html',
  styleUrls: ['../../../app/shared/layouts/admin/admin.component.css']
})
export class CreateCompanyComponent implements OnInit {

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
    this.admin.createCompany(this.form.value).subscribe(
      () => this.snackBar.open('New company successfully added.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

}
