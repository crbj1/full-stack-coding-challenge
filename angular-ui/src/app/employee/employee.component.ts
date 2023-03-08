import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { first } from 'rxjs/operators';

import { Employee } from '../_models/employee';
import { RestService } from '../_services/rest.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {

  private id: number;
  loading = false;
  currentEmployee: Employee;

  constructor(private route: ActivatedRoute, private restService: RestService) { 
    this.route.params.subscribe(params => {
      this.id = params['id'];
    });
  }

  ngOnInit(): void {
    this.loading = true;
    this.restService.getEmployee(this.id).pipe(first()).subscribe(employee => {
      this.currentEmployee = employee;
      this.loading = false;
    });   
  }

}
