function saveArticle(clicked_element)
{
        let saveArticleUrl= "http://192.168.1.6:11000/" + "saveArticle?id=" + clicked_element.getAttribute('id');
        console.log(saveArticleUrl);
        let xmlHttpReq = new XMLHttpRequest();
         xmlHttpReq.open("GET", saveArticleUrl, false);
        xmlHttpReq.onload = function(){
                console.log("inside onload function")
                    console.log(xmlHttpReq.responseText);
            }
         xmlHttpReq.send();
         console.log(xmlHttpReq.responseText);
         console.log(xmlHttpReq.status);

         if(xmlHttpReq.status === 200){
          var toastElemId = "articleSavedSuccessToast";
          var toastInnerHtmlMessage = "<span>Article Saved Successfully!</span>";

          showToast(toastElemId);
            setTimeout(function(){
              HideSuccessToast();
            }, 3000);
         }
        else{
          //To do failed article saved toast
        }

}

function showToast(elemId){

var toastElem = document.getElementById(elemId);
toastElem.style.visibility = 'visible';

}

function HideSuccessToast(){

  var toastElem = document.getElementById("articleSavedSuccessToast");
  toastElem.style.visibility = 'hidden';

}



function deleteArticle(clicked_element){
    let url = clicked_element.getAttribute('url');
    let deleteArticleUrl = "http://192.168.1.6:11000/deleteArticle";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", deleteArticleUrl, true);
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
   //  xhr.setRequestHeader("Authorization","Basic am9objoxMjM0NQ==");

    xhr.onReadyStateChange = function () {
      if (xhr.readyState === 4) {
        console.log(xhr.status);
        console.log(xhr.responseText);
      }};

    let data = '{"url":"'+url+ '"}';
    xhr.onload = function(){
            console.log(xhr.responseText);
    }

    xhr.send(data);
    console.log(xhr.status);
    console.log(xhr.responseText);
    let cardId = clicked_element.getAttribute('id');
    const cardToDelete = document.getElementById(cardId);
    cardToDelete.remove();
}