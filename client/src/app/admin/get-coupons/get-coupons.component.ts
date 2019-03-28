import {Component, OnInit} from '@angular/core'
import {MatSnackBar, MatTableDataSource} from "@angular/material"
import {Coupon, CouponType} from "../../models/coupon"
import {AdminService} from "../../shared/services/admin.service"
import {animate, state, style, transition, trigger} from '@angular/animations'
import {Company} from "../../models/company"
import {FormControl, FormGroup, Validators} from "@angular/forms"
import {MyErrorStateMatcher} from "../../models/errorStateMatcher"
import {DatePipe} from "@angular/common"

@Component({
  selector: 'app-get-coupons',
  templateUrl: './get-coupons.component.html',
  styleUrls: ['../../../app/shared/layouts/admin/admin.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class GetCouponsComponent implements OnInit {

  couponColumns: string[] = ['title', 'startDate', 'endDate', 'amount', 'couponType', 'message', 'price', 'image', 'companyId']
  couponSource: MatTableDataSource<Coupon>
  expandedElement: Company | null

  form: FormGroup
  matcher = new MyErrorStateMatcher()
  showUpdate = false

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
    this.admin.getAllCoupons().subscribe(coupons => this.couponSource = new MatTableDataSource(coupons),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })

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

  couponFilter(filterValue: string) {
    this.couponSource.filter = filterValue.trim().toLowerCase()
  }

  onDelete(id: number) {
    this.admin.deleteCoupon(id).subscribe(
      () => this.snackBar.open('Coupon successfully removed.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

  onUpdate(id: number) {
    let coupon: Coupon = {
      id: id,
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

    this.admin.updateCoupon(coupon).subscribe(
      () => this.snackBar.open('Coupon successfully updated.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

  onShowUpdate() {
    this.showUpdate === false ? this.showUpdate = true : this.showUpdate = false
  }

}
