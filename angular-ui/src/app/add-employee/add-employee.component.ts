import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { Employee } from '../_models/employee';
import { Logger } from '../_services/logging.service';
import { RestService } from '../_services/rest.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent implements OnInit {

  createEmployeeForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';

  //State names
  State: any = ['AK', 'AL', 'AR', 'AS', 'AZ', 'CA', 'CM', 'CO', 'CT', 'DC', 'DE', 'FL', 'GA', 'GU', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'PR', 'RI', 'SC', 'SD', 'TN', 'TT', 'TX', 'UT', 'VA', 'VI', 'VT', 'WA', 'WI', 'WV', 'WY']

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private restService: RestService,
    private logger: Logger
  ) { }

  ngOnInit(): void {
    this.createEmployeeForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(35)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(35)]],
      address: ['', [Validators.maxLength(50)]],
      city: ['', [Validators.maxLength(50)]],
      stateName: ['', []],
      zipCode: ['', [Validators.minLength(5), Validators.maxLength(9)]],
      homePhone: ['', [Validators.minLength(10), Validators.maxLength(10)]],
      cellPhone: ['', [Validators.minLength(10), Validators.maxLength(10)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(50)]]
    })

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  get f() { return this.createEmployeeForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.createEmployeeForm.invalid) {
      this.logger.log("Form is invalid");
      return;
    }

    this.loading = true;

    let newEmployee = new Employee();
    newEmployee.firstName = this.f.firstName.value;
    newEmployee.lastName = this.f.lastName.value;
    newEmployee.address = this.f.address.value;
    newEmployee.city = this.f.city.value;
    newEmployee.state = this.f.stateName.value;
    newEmployee.zipCode = this.f.zipCode.value;
    newEmployee.homePhone = this.f.homePhone.value;
    newEmployee.cellPhone = this.f.cellPhone.value;
    newEmployee.email = this.f.email.value;

    this.restService.createEmployee(newEmployee)
      .pipe(first())
      .subscribe(
        data => {
          this.logger.log('Newly created employee: ' + data);
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.logger.error(error);
          this.error = error;
          this.loading = false;
        }
      );
  }

}
