function saveArticle(clicked_id)
{
        let saveArticleUrl= "http://192.168.1.6:10000/" + "saveArticle?id=" + clicked_id;
        console.log(saveArticleUrl);
        let xmlHttpReq = new XMLHttpRequest();
         xmlHttpReq.open("GET", saveArticleUrl, false);
         xmlHttpReq.send(null);
         console.log(xmlHttpReq.responseText);
}

  function httpGet(theUrl) {
    let xmlHttpReq = new XMLHttpRequest();
    xmlHttpReq.open("GET", theUrl, false);
    xmlHttpReq.send(null);
    return xmlHttpReq.responseText;
}
