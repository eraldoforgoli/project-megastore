var v = new Vue({

el: '#megastoreController',
data: {

baskets: [],
basket : '',
visible: false,
selected: '',

id: '',
divHeight: 300,

basketWithIDUri: '',
AllBasketsUri: 'http://localhost:8080/megastore/webapi/baskets/',
AllItemsUri: 'http://localhost:8080/megastore/webapi/items/',

items: [],

selectedItemList: [],
selectedItemPrice: [],
cashier: '',
VAT: 10,
netAmount: '',
totalAmount: '',
address: 'Komuna e Parisit',
basketID: 1,
n: 1,
n4: 1,
deleteID: '',
deleteSuccess:'',

totalPrice: 0.0,
itemPrice:0.0,
selecteditemIndex: -1,

cashier: ""
},

methods: {
    getAllItems :function(){
      axios.get(v.AllItemsUri)
  .then(function (response) {
    console.log(response); // provo beje .data[0].cashier do dali emri...
   v.items = response.data;
  // v.visible = true;
  return v.items;
  })
  .catch(function (error) {
    console.log(error);
  });

  v.totalPrice = 0.0;
  v.selectedItemList = [];
  v.selectedItemPrice =[];
},

selectedItem: function(){
console.log(v.selected);

v.selectedItemList.push(v.selected);
v.calculatePrice();
},

calculatePrice: function(){
  v.totalPrice=0.0;
  v.selectedItemPrice =[];

  for(var i = 0; i < v.selectedItemList.length; i++){
    for(var j = 0; j < v.items.length; j++){
      if(v.selectedItemList[i] === (v.items[j].name)){
        console.log("price of item: " + v.items[j].price);
        v.totalPrice += v.items[j].price;
        v.selectedItemPrice.push(v.items[j].price);
        break;
      }
    }
  }
},

  purchaseBasket: function(){
  alert(v.selectedItemList);

  v.cashier = v.getCashier();
  v.addBasket();
  v.addItemsToBasket();

  v.totalPrice = 0.0;
  v.selectedItemList = [];

},



addBasket: function(){
  axios.post(v.AllBasketsUri, {
         "VAT": v.VAT,
         "address": v.address ,
         "basketID": v.basketID,
         "cashier": v.cashier,
         "netAmount": (v.totalPrice + (0.1*v.totalPrice) ),
         "totalAmount": v.totalPrice
         },
         {
           headers: {
              'Content-Type': 'application/json'
//            'Content-Type': 'multipart/form-data'
    //       'Content-Type': 'application/x-www-form-urlencoded'
           }
         })  .then(response => {})
           .catch(e => {
             console.log(e);
           })
},

addItemsToBasket: function(){
  axios.post(v.AllBasketsUri + "addItems", {
         "names": v.selectedItemList,
         "prices": v.selectedItemPrice,
         },
         {
           headers: {
              'Content-Type': 'application/json'
//            'Content-Type': 'multipart/form-data'
    //       'Content-Type': 'application/x-www-form-urlencoded'
           }
         })  .then(response => {})
           .catch(e => {
             console.log(e);
           })
},

  setCashier: function(c){
    v.cashier = c;
  },

  getCashier: function(){
    return  localStorage.getItem('username');
  },

  removeItem: function(index) {

     for(var j = 0; j < v.items.length; j++){
       if(v.selectedItemList[index] === (v.items[j].name)){
         console.log(v.items[j].price);
         v.totalPrice -= v.items[j].price
     }
 }

  v.selectedItemList.splice(index, 1);
},

  logout: function(){
  // fshi emrin e cashier nga local storage dhe e ridrejtoj ne login page
    localStorage.removeItem('cashier');
    window.location.href = "http://localhost:3000/login.html"
}

/*
postBasket: function(){
  axios.post(v.AllBasketsUri, {
     v.postBody
    })
    .then(response => {})
    .catch(e => {
      this.errors.push(e)
    })


}
*/

}
});
