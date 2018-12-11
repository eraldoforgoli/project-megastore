
var v = new Vue({

el: '#login',
data: {
  username: "",
  password: "",
  loginURI:"http://localhost:8080/megastore/webapi/login/" ,
  status: "Not Found",
  ok: "OK"

},
methods: {


  identify: function( username,password){
    v.loginURI  = "http://localhost:8080/megastore/webapi/login/" + "?username="+ v.username + "&password=" + v.password;
    console.log(v.loginURI);
    axios.get(v.loginURI)
    .then(function (response) {

// ruaj username ne localstorage per ta shfaqur ne page dhe per te ruajtur ne databaze
    localStorage.setItem('username', v.username);

    console.log(response);
    v.status=response.data;
  })
  .catch(function (error) {
    console.log(error);

  });
},
  redirect: function(){
    window.location.href = "http://localhost:3000/megastorecontroller.html"
  }
}


});
