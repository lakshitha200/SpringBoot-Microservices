import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent implements OnInit{
  constructor(private orderService:OrderService){}
  ngOnInit(): void {
    this.orderService.getOrderList().subscribe(data=>{
      console.log(data);
    })
  }

}
