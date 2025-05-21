// Trouvé sur https://www.freecodecamp.org/news/javascript-get-current-date-todays-date-in-js/
export function getTodayDate() {
  let now = new Date();
  let y = now.getFullYear();
  let m = now.getMonth() + 1;
  let d = now.getDate();

  // Si le jour est <10 on met un 0 devant (ajout perso)
  m = m < 10 ? "0" + m : m;
  d = d < 10 ? "0" + d : d;

  return y + "-" + m + "-" + d;
}

// Désolé j'avais pas la force de coder plus intelligent
// Permet de retirer / ajouter les tags valids / invalids des champs
// dans un form pour mes forms
export function valid_unvalid(id, cond = true) {
  if (typeof id === "object") {
    if (cond) {
      id.classList.remove("valid-input");
      id.classList.add("invalid-input");
    } else {
      id.classList.add("valid-input");
      id.classList.remove("invalid-input");
    }
    return;
  }
  $("#" + id).removeClass("valid-input");
  $("#" + id).addClass("invalid-input");
}

/* Fonction honteusement copiée colée de ce site internet :
   https://www.equinode.com/fonctions-javascript/calculer-le-nombre-de-jours-entre-deux-dates-avec-javascript 
   Merci pour les services
*/
export function datesDiff(a, b) {
  a = a.getTime();
  b = (b || new Date()).getTime();
  var c = a > b ? a : b,
    d = a > b ? b : a;
  return Math.abs(Math.ceil((c - d) / 86400000));
}
