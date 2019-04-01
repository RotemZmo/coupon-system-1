import {Component, OnInit} from '@angular/core'
import {MatSnackBar, MatTableDataSource} from "@angular/material"
import {Income} from "../../models/income"
import {AdminService} from "../../shared/services/admin.service"

@Component({
  selector: 'app-get-all-incomes',
  templateUrl: './get-incomes.component.html',
  styleUrls: ['../../../app/shared/layouts/admin/admin.component.css']
})
export class GetIncomesComponent implements OnInit {

  incomeColumns: string[] = ['companyName', 'customerName', 'date', 'description']
  incomeSource: MatTableDataSource<Income>

  constructor(private admin: AdminService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.admin.getIncomes().subscribe(incomes => this.incomeSource = new MatTableDataSource(incomes),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

  incomeFilter(filterValue: string) {
    this.incomeSource.filter = filterValue.trim().toLowerCase()
  }

}
