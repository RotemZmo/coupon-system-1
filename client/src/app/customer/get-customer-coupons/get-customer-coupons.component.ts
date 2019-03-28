import {Component, OnInit} from '@angular/core'
import {MatSnackBar, MatTableDataSource} from '@angular/material'
import {Coupon} from "../../models/coupon"
import {CustomerService} from "../../shared/services/customer.service"

@Component({
  selector: 'app-get-coupons',
  templateUrl: './get-customer-coupons.component.html',
  styleUrls: ['../../shared/layouts/customer/customer.component.css']
})
export class GetCustomerCouponsComponent implements OnInit {

  couponColumns: string[] = ['title', 'startDate', 'endDate', 'amount', 'couponType', 'message', 'price', 'image']
  couponSource: MatTableDataSource<Coupon>

  constructor(private customer: CustomerService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.customer.getPurchasedCoupons().subscribe(coupons => this.couponSource = new MatTableDataSource(coupons),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

  couponFilter(filterValue: string) {
    this.couponSource.filter = filterValue.trim().toLowerCase()
  }

}
