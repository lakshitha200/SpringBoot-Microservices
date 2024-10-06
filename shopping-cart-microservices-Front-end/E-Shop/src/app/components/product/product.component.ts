import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../model/Product';
import { Order } from '../../model/Order';
import { OrderItems } from '../../model/OrderItems';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit{
  
  @ViewChild('productDetails') productDetails:any;
  @ViewChild('cardPayment') cardPayment:any;
  @ViewChild('paypal') paypal:any;

  constructor(private productService:ProductService){}

  productList:Product[] = [];
  selectedProduct!: Product;
  isClickedBuy:boolean = false;
  quantityCount: number = 0;

  ngOnInit(): void {
  
    //get all products
    this.productService.getProductList().subscribe((product)=>{
        this.productList = product;
    });
  }

  openProductDetails(){
    window.alert(this.productDetails.nativeElement)
  }

  payment(id:string){
    this.isClickedBuy = true;
    this.productService.getProductById(id).subscribe(data=>{
      this.selectedProduct = data;
      
    })
  }

  increase(){
      if(this.quantityCount < this.selectedProduct.quantity){
        ++this.quantityCount;
      }
  }

  decrease(){
    if(this.quantityCount>0){
      --this.quantityCount;
    }
  }
  makeOrder(){
    let totalPrice = this.selectedProduct.price * this.quantityCount;
    const orderItem:OrderItems = new OrderItems(this.selectedProduct.id,this.quantityCount,this.selectedProduct.price);
    const neworder = new Order(100,totalPrice,orderItem);
  }

}
