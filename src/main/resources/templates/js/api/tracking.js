/**
 * Created by Anjulaw on 12/26/2016.
 */
var INVALID_BUG_RATIO_URL="http://localhost:8080/api/getInvalidCount?total=1";

$( document ).ready(function() {



});

function getInvalidBugRatio(){

    $.ajax({
        method: "GET",
        url: INVALID_BUG_RATIO_URL,
        dataType: 'json',
    })
        .error(function() {
            alert("ERROR");
        })
        .done(function(data) {

           if(data!=null){

               alert(data.invalidBugCountRatio);
           }
        });

}

