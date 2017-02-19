/**
 * Created by Anjulaw on 12/26/2016.
 */
var INVALID_BUG_RATIO_URL="http://localhost:8080/api/getInvalidCount?createdDate=1";
var DEFECT_REMOVAL_RATIO_URL = "http://localhost:8080/api/getDefectRemoval?issuetype=ta";

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

function getDefectRemovalRatio() {

    $.ajax({
        method: "GET",
        url: DEFECT_REMOVAL_RATIO_URL,
        dataType: 'json',
    })
        .error(function() {
            alert("ERROR");
        })
        .done(function(data) {

            if(data!=null){

                alert(data.defectRemovalEfficiency);
            }
        });

}

