import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {EcommerceComponent} from "./ecommerce/ecommerce.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'ecommerce', component: EcommerceComponent},
  {path : '', component : LoginComponent}
];

export const routing = RouterModule.forRoot(routes);