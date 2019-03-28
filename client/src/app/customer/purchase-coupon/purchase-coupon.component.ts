import {Component, OnInit} from '@angular/core'
import {MatSnackBar, MatTableDataSource} from '@angular/material'
import {Coupon} from "../../models/coupon"
import {CustomerService} from "../../shared/services/customer.service"
import {MyErrorStateMatcher} from "../../models/errorStateMatcher"
import {animate, state, style, transition, trigger} from "@angular/animations"
import {Company} from "../../models/company"

@Component({
  selector: 'app-purchase-coupon',
  templateUrl: './purchase-coupon.component.html',
  styleUrls: ['../../../app/shared/layouts/customer/customer.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class PurchaseCouponComponent implements OnInit {

  couponColumns: string[] = ['title', 'startDate', 'endDate', 'amount', 'couponType', 'message', 'price', 'image']
  couponSource: MatTableDataSource<Coupon>
  expandedElement: Company | null

  matcher = new MyErrorStateMatcher()

  constructor(private customer: CustomerService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.customer.getAvailableCoupons().subscribe(coupons => this.couponSource = new MatTableDataSource(coupons),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

  couponFilter(filterValue: string) {
    this.couponSource.filter = filterValue.trim().toLowerCase()
  }

  onPurchase(id: number) {
    this.customer.purchaseCoupon(id).subscribe(
      () => this.snackBar.open('Coupon successfully purchased.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

}
