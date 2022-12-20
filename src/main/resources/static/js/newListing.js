function saveArticle(clicked_element)
{
        let saveArticleUrl= "http://192.168.1.6:11000/" + "saveArticle?id=" + clicked_element.getAttribute('id');
        console.log(saveArticleUrl);
        let xmlHttpReq = new XMLHttpRequest();
         xmlHttpReq.open("GET", saveArticleUrl, false);
         xmlHttpReq.send(null);
         console.log(xmlHttpReq.responseText);
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
                  // var response = JSON.parse(xhr.responseText);
//                   if(xhr.status == 200) {
//                       singleFileUploadError.style.display = "none";
//                       singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>DownloadUrl : <a href='" + response.fileDownloadUri + "' target='_blank'>" + response.fileDownloadUri + "</a></p>";
//                       singleFileUploadSuccess.style.display = "block";
//                   } else {
//                       singleFileUploadSuccess.style.display = "none";
//                       singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
//                   }
    }

    xhr.send(data);
    console.log(xhr.status);
    console.log(xhr.responseText);
    let cardId = clicked_element.getAttribute('id');
    const cardToDelete = document.getElementById(cardId);
    cardToDelete.remove();

}