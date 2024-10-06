import { Injectable } from "@angular/core";
import { Order } from "../model/Order";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
    providedIn: "root"
})
export class OrderService{

    private baseUrl = "http://localhost:8080/api/orders";
    constructor(private httpClient:HttpClient){}
    
    getOrderList():Observable<any[]>{
        return this.httpClient.get<any[]>(this.baseUrl);
    }
    getOrderById(id:string): Observable<any>{
        return this.httpClient.get<any>(`${this.baseUrl}/${id}`);
    }

    createOrder(order:Order):Observable<Order>{
        return this.httpClient.post<Order>(`${this.baseUrl}`,order);
    }

    // updateOrder(id:number,employee:Order): Observable<Object>{
    //     return this.httpClient.put(`${this.baseUrl}/${id}`,employee);
    // }

    // deleteOrder(id:number):Observable<object>{
    //     return this.httpClient.delete(`${this.baseUrl}/${id}`);
    // }
}