function saveArticle(clicked_id)
{
        let saveArticleUrl= "http://192.168.1.6:10000/" + "saveArticle?id=" + clicked_id;
        console.log(saveArticleUrl);
        let xmlHttpReq = new XMLHttpRequest();
         xmlHttpReq.open("GET", saveArticleUrl, false);
         xmlHttpReq.send(null);
         console.log(xmlHttpReq.responseText);
}
function deleteArticle(clicked_element){
    let url = clicked_element.getAttribute('url');
    let deleteArticleUrl = "http://192.168.1.6:10000/deleteArticle";
    let xhr = new XMLHttpRequest();
    xhr.open("POST", deleteArticleUrl);
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onReadyStateChange = function () {
      if (xhr.readyState === 4) {
        console.log(xhr.status);
        console.log(xhr.responseText);
      }};

    let data = {
     "url":url
    };

    xhr.send(data);
    console.log(xhr.responseText);
}