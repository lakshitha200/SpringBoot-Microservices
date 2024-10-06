export class OrderItems{

    productId: string;
    quantity: number;
    price:number;

    constructor( productId: string,quantity: number,price:number){
        this.productId=productId;
        this.quantity=quantity;
        this.price=price;
    }

}