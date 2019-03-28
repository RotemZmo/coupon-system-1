import {Component, OnInit} from '@angular/core'
import {MatSnackBar, MatTableDataSource} from '@angular/material'
import {Coupon, CouponType} from "../../models/coupon"
import {CompanyService} from "../../shared/services/company.service"
import {animate, state, style, transition, trigger} from "@angular/animations"
import {Company} from "../../models/company"
import {FormControl, FormGroup, Validators} from "@angular/forms"
import {MyErrorStateMatcher} from "../../models/errorStateMatcher"
import {DatePipe} from "@angular/common"

@Component({
  selector: 'app-get-coupons',
  templateUrl: './get-company-coupons.component.html',
  styleUrls: ['../../shared/layouts/company/company.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class GetCompanyCouponsComponent implements OnInit {

  couponColumns: string[] = ['title', 'startDate', 'endDate', 'amount', 'couponType', 'message', 'price', 'image']
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

  constructor(private company: CompanyService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.company.getAllCoupons().subscribe(coupons => this.couponSource = new MatTableDataSource(coupons),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })

    this.form = new FormGroup({

      endDate: new FormControl(null, [
        Validators.required
      ]),

      price: new FormControl(null, [
        Validators.min(1),
        Validators.required
      ]),

      amount: new FormControl(null, [
        Validators.min(1),
        Validators.required
      ])

    })
  }

  couponFilter(filterValue: string) {
    this.couponSource.filter = filterValue.trim().toLowerCase()
  }

  onDelete(id: number) {
    this.company.deleteCoupon(id).subscribe(
      () => this.snackBar.open('Coupon successfully removed.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

  onUpdate(id: number) {
    let coupon: Coupon = {
      id: id,
      endDate: new DatePipe('en-US').transform(this.form.value.endDate, 'yyyy-MM-dd'),
      amount: this.form.value.amount,
      price: this.form.value.price
    }
    this.company.updateCoupon(coupon).subscribe(
      () => this.snackBar.open('Coupon successfully updated.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

  onShowUpdate() {
    this.showUpdate === false ? this.showUpdate = true : this.showUpdate = false
  }

}
