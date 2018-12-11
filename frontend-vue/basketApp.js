var v = new Vue({

el: '#basket',
data: {

baskets: [],
basket : '',
visible: false,
style: 'none',
id: '',
divHeight: 0,
div2Height: 0,
div3Height: 0,
div4Height: 0,
basketWithIDUri: '',
AllBasketsUri: 'http://localhost:8080/megastore/webapi/baskets/',

postBody: ' "VAT": 10, "address": "Rruga e Durresit", "basketID": 1, "cashier": " Eralldoo", "netAmount": 49.5, "totalAmount": 45',
errors: [],
cashier: '',
VAT: '',
netAmount: '',
totalAmount: '',
address: '',
basketID: '',
n: 1,
n4: 1,
deleteID: '',
deleteSuccess:'',


updateCashier: '',
updateVAT: '',
updateNetAmount: '',
updateTotalAmount: '',
updateAddress: '',
updateBasketID: ''

},
  methods: {

    getAllBaskets :function(){
      axios.get(v.AllBasketsUri)
      .then(function (response) {
        console.log(response); // provo beje .data[0].cashier do dali emri...
        v.baskets = response.data;

        v.divHeight = 300;
        // v.visible = true;
        return v.baskets;
      })
      .catch(function (error) {
        console.log(error);
      });
    },

clearDiv: function(){
  /*var div = document.getElementById('MyDiv');
while(div.firstChild){
    div.removeChild(div.firstChild);
}
*/
//v.visible = false;
//document.getElementById("MyDiv").innerHTML = "";
  v.divHeight=0;
},

  clearDiv2: function(){
    v.div2Height=0;
  },

  getBasketWithId: function(){
      v.basketWithIDUri =(v.AllBasketsUri   +  v.id);
      axios.get(v.basketWithIDUri)
      .then(function (response) {
        console.log(response); // provo beje .data[0].cashier do dali emri...
        v.basket = response.data;
        v.div2Height=300;
        return v.basket;
      })
      .catch(function (error) {
        console.log(error);
        v.basket = 404;
        return 404;
      });
    },

    showDiv3: function(){
      if(v.n == 1){
        v.div3Height = 300;
        v.n++;
      }
      else {
        v.div3
        v.div3Height =0;
        v.n--;
      }
    },
    showDiv4: function(){
      if(v.n4 == 1){
        v.div4Height = 300;
        v.n4++;
      }
      else {
        v.div4Height =0;
        v.n4--;
      }
    },

    addBasket: function(){
      axios.post(v.AllBasketsUri, {
          "VAT": v.VAT,
          "address": v.address ,
          "basketID": v.basketID,
          "cashier": v.cashier,
          "netAmount": v.netAmount ,
           "totalAmount": v.totalAmount
          },
          {
            headers: {
                'Content-Type': 'application/json'
//              'Content-Type': 'multipart/form-data'
    //        'Content-Type': 'application/x-www-form-urlencoded'
            }
          })  .then(response => {})
            .catch(e => {
              this.errors.push(e);
              document.getElementById("show").innerHTML="WRONG INPUT";
            })

            document.getElementById("myForm").reset();
            document.getElementById("show").innerHTML="SUCCESS";
          },

          deleteBasket: function(){
            axios.delete(v.AllBasketsUri + v.deleteID).then(response => {})
            .catch(e => {
              this.errors.push(e)
              v.deleteSuccess='Error, basket not found';
            })
            v.deleteSuccess='Deleted';
          },

          updateBasket: function(){
            axios.put(v.AllBasketsUri + v.updateBasketID, {
              "VAT": v.updateVAT,
              "address": v.updateAddress ,
              "basketID": v.updateBasketID,
              "cashier": v.updateCashier,
              "netAmount": v.updateNetAmount ,
              "totalAmount": v.updateTotalAmount
            },
            {
              headers: {
                'Content-Type': 'application/json'
//              'Content-Type': 'multipart/form-data'
    //        'Content-Type': 'application/x-www-form-urlencoded'
            }
          })  .then(response => {})
            .catch(e => {
              this.errors.push(e);
              document.getElementById("show2").innerHTML="WRONG INPUT";
            })

      /*     document.getElementById("myForm2").reset();*/
            document.getElementById("show2").innerHTML="SUCCESS";

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
