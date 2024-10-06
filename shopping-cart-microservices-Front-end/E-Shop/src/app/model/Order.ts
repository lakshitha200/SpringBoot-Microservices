import { OrderItems } from "./OrderItems";

export class Order{

    customerId!: number;
    totalPrice!: number;
    orderItems!: OrderItems [];

    constructor( customerId: number,totalPrice: number,orderItems:OrderItems []){
        this.customerId=customerId;
        this.totalPrice=totalPrice;
        this.orderItems=orderItems;
    }
}