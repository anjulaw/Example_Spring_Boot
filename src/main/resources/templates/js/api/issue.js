/**
 * Created by Anjulaw on 12/26/2016.
 */
var ISSUE_URL="http://localhost:8080/api/getIssue?issueId=123";

$( document ).ready(function() {


    $.ajax({
        method: "GET",
        url: ISSUE_URL,
        dataType: 'json',
    })
        .error(function() {
            alert("ERROR");
        })
        .done(function(data) {


            alert("Issue id is :-"+data.id);
        });


});

