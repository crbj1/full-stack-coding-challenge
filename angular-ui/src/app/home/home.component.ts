import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { Employee } from '../_models/employee';
import { RestService } from '../_services/rest.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  loading = false;
  employees: Employee[];

  constructor(private restService: RestService) { }

  ngOnInit(): void {
    this.loading = true;
    this.restService.getEmployees().pipe(first()).subscribe(employees => {
      this.loading = false;
      this.employees = this.sortEmployeeList(employees);
    });
  }

  sortEmployeeList(list: Employee[]): Employee[] {
    list.sort(function(a, b){
      let x = a.lastName.toLowerCase();
      let y = b.lastName.toLowerCase();
      if (x < y) {return -1;}
      if (x > y) {return 1;}
      return 0;
    })
    return list;
  }

}
