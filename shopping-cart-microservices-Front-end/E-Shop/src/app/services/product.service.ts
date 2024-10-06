import { Injectable } from "@angular/core";
import { Product } from "../model/Product";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
    providedIn: "root"
})
export class ProductService{

    private baseUrl = "http://localhost:8080/api/products";
    constructor(private httpClient:HttpClient){}
    
    getProductList():Observable<Product[]>{
        return this.httpClient.get<Product[]>(this.baseUrl);
    }
    getProductById(id:string): Observable<Product>{
        return this.httpClient.get<Product>(`${this.baseUrl}/${id}`);
    }

    // createProduct(employee:Product):Observable<Object>{
    //     return this.httpClient.post(`${this.baseUrl}`,employee);
    // }

    // updateProduct(id:number,employee:Product): Observable<Object>{
    //     return this.httpClient.put(`${this.baseUrl}/${id}`,employee);
    // }

    // deleteProduct(id:number):Observable<object>{
    //     return this.httpClient.delete(`${this.baseUrl}/${id}`);
    // }
}