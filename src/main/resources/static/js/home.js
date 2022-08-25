function method1(){
  const createcard = document.getElementById("createCard");

  const card = document.createElement("div");
  card.className = "card";
  card.setAttribute("style","width: 40rem; height: 40rem;");

  const cardBody = document.createElement("div");
  const hFive = document.createElement("h5");
  const hSix = document.createElement("h6");
  const p = document.createElement("p");
  const a1 = document.createElement("a");
  const a2 = document.createElement("a");

  hFive.className = "card-title";
  hFive.innerText = "Card title";

  hSix.className = "card-subtitle mb-2 text-muted";
  hSix.innerText = "Card Subtitle";

  p.className = "card-text";
  p.innerText = "Some quick example text to build on the card title and make up the bulk of the card's content.j";

  a1.href = "https://www.google.com";
  a1.className = "card-link";
  a1.innerText = "Card link";

  a2.href = "https://www.reddit.com";
  a2.className = "card-link";
  a2.innerText = "Another link";

  card.appendChild(cardBody);
  cardBody.appendChild(hFive);
  cardBody.appendChild(hSix);
  cardBody.appendChild(p);
  cardBody.appendChild(a1);
  cardBody.appendChild(a2);

  createCard.appendChild(card);
}