import {Component, OnInit} from '@angular/core'
import {FormControl, FormGroup, Validators} from "@angular/forms"
import {MyErrorStateMatcher} from "../../models/errorStateMatcher"
import {Coupon, CouponType} from "../../models/coupon"
import {MatSnackBar} from "@angular/material"
import {AdminService} from "../../shared/services/admin.service"
import {DatePipe} from "@angular/common"

@Component({
  selector: 'app-create-coupon',
  templateUrl: './create-coupon.component.html',
  styleUrls: ['../../../app/shared/layouts/company/company.component.css']
})
export class CreateCouponComponent implements OnInit {

  form: FormGroup

  matcher = new MyErrorStateMatcher()

  coupons: CouponType[] = [
    {value: 'FOOD'},
    {value: 'SPORTS'},
    {value: 'HEALTH'},
    {value: 'CAMPING'},
    {value: 'TRAVELLING'},
    {value: 'RESTAURANTS'},
    {value: 'ELECTRICITY'}
  ]

  constructor(private admin: AdminService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.form = new FormGroup({

      title: new FormControl(null, [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(20)
      ]),

      startDate: new FormControl(null, [
        Validators.required
      ]),

      endDate: new FormControl(null, [
        Validators.required
      ]),

      amount: new FormControl(null, [
        Validators.required,
        Validators.min(1)
      ]),

      couponType: new FormControl(null, [
        Validators.required
      ]),

      message: new FormControl(null, [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(20)
      ]),

      price: new FormControl(null, [
        Validators.required,
        Validators.min(1)
      ]),

      image: new FormControl(null, [
        Validators.required,
        Validators.maxLength(20)
      ]),

      companyId: new FormControl(null, [
        Validators.required
      ])

    })
  }

  onSubmit() {
    let coupon: Coupon = {
      title: this.form.value.title,
      startDate: new DatePipe('en-US').transform(this.form.value.startDate, 'yyyy-MM-dd'),
      endDate: new DatePipe('en-US').transform(this.form.value.endDate, 'yyyy-MM-dd'),
      amount: this.form.value.amount,
      couponType: this.form.value.couponType,
      message: this.form.value.message,
      price: this.form.value.price,
      image: this.form.value.image,
      companyId: this.form.value.companyId
    }

    this.admin.createCoupon(coupon).subscribe(
      () => this.snackBar.open('New coupon successfully added.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

}
