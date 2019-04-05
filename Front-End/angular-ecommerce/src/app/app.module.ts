import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import {AppComponent} from './app.component';
import {EcommerceComponent} from './ecommerce/ecommerce.component';
import {ProductsComponent} from './ecommerce/products/products.component';
import {ShoppingCartComponent} from './ecommerce/shopping-cart/shopping-cart.component';
import {OrdersComponent} from './ecommerce/orders/orders.component';
import {EcommerceService} from "./ecommerce/services/EcommerceService";
import { LoginComponent } from './login/login.component';
import {routing} from "./app.routing";
import {TokenInterceptor} from "./core/interceptor";
import { ApiService } from './core/api.service';

@NgModule({
    declarations: [
        AppComponent,
        EcommerceComponent,
        ProductsComponent,
        ShoppingCartComponent,
        OrdersComponent,
        LoginComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        routing,
        ReactiveFormsModule
    ],
    providers: [EcommerceService,ApiService,{provide: HTTP_INTERCEPTORS,
        useClass: TokenInterceptor,
        multi : true}],
    bootstrap: [AppComponent]
})
export class AppModule {
}