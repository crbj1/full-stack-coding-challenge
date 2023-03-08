import { Routes, RouterModule } from "@angular/router";
import { AddEmployeeComponent } from "./add-employee/add-employee.component";
import { EmployeeComponent } from "./employee/employee.component";

import { HomeComponent } from "./home/home.component";
import { LoginComponent } from "./login/login.component";
import { AuthGuardGuard } from './_helpers/auth-guard.guard';

const routes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuardGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'addEmployee', component: AddEmployeeComponent, canActivate: [AuthGuardGuard] },
    { path: 'employee/:id', component: EmployeeComponent, canActivate: [AuthGuardGuard] },

    { path: '**', redirectTo: '' }
];

export const appRoutingModule = RouterModule.forRoot(routes);