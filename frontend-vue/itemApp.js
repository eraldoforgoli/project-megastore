var v = new Vue({

el: '#item',
data: {

items: [],
item : '',
visible: false,
style: 'none',
id: '',
divHeight: 0,
div2Height: 0,
div3Height: 0,
div4Height: 0,
itemWithIDUri: '',
AllItemsUri: 'http://localhost:8080/megastore/webapi/items/',


errors: [],

discount: '',
inOffer: '',
itemId: '',
name: '',
partlyIndependence: '',
price: '',
n: 1,
n4: 1,
deleteID: '',
deleteSuccess:'',




updateDiscount: '',
updateInOffer: '',
updateItemId: '',
updateName: '',
updatePartlyIndependence: '',
updatePrice: ''

},
methods: {

  getAllItems :function(){
    axios.get(v.AllItemsUri)
    .then(function (response) {
      console.log(response); // provo beje .data[0].cashier do dali emri...
      v.items = response.data;

      v.divHeight = 300;
      // v.visible = true;
      return v.items;

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

getItemWithId: function(){

    v.itemWithIDUri =(v.AllItemsUri   +  v.id);
    axios.get(v.itemWithIDUri)
    .then(function (response) {
      console.log(response); // provo beje .data[0].cashier do dali emri...
      v.item = response.data;
      v.div2Height=300;
      return v.item;

    })
    .catch(function (error) {
      console.log(error);
      v.item = 404;
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

  addItem: function(){
    axios.post(v.AllItemsUri, {
      "discount": v.discount,
      "inOffer": v.inOffer,
      "itemId": v.itemId,
      "name": v.name,
      "partlyIndependence": v.partlyIndependence,
      "price": v.price,
         },
         {
           headers: {
              'Content-Type': 'application/json'
//            'Content-Type': 'multipart/form-data'
    //       'Content-Type': 'application/x-www-form-urlencoded'
            }
          })  .then(response => {})
            .catch(e => {
              this.errors.push(e);
              document.getElementById("show").innerHTML="WRONG INPUT";
           })
           document.getElementById("myForm").reset();
           document.getElementById("show").innerHTML="SUCCESS";

  },

  deleteItem: function(){
    axios.delete(v.AllItemsUri + v.deleteID).then(response => {})
      .catch(e => {
        this.errors.push(e)
        v.deleteSuccess='Error, item not found';
      })
      v.deleteSuccess='Deleted';
},

  updateItem: function(){
    axios.put(v.AllItemsUri + v.updateItemId, {
        "discount": v.updateDiscount,
        "inOffer": v.updateInOffer,
        "itemId": v.updateItemId,
        "name": v.updateName,
        "partlyIndependence": v.updatePartlyIndependence,
        "price": v.updatePrice,
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
