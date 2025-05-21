import { templater } from "./templater.js";

// Permet de faire un fetch avec un appel de fonction intégré
// index = -1 si on veut manipuler tout le resultat
// index positif si on veut prednre que certaines iterations d'un tableau
export async function load(
  path,
  rules = (object) => {
    return object;
  },
  index = -1
) {
  const reponse = await fetch(path);
  const json = await reponse.json();
  if (index == -1) {
    rules(json);
  } else {
    rules(json[index - 1]);
  }
}
// load similaire au premier mais specialisé pour le templating
// voir doc templater
export async function loadJsonForTemplating(
  template_id,
  parent_id,
  datasource,
  pre_rules,
  post_rules
) {
  const reponse = await fetch(datasource);
  const json = await reponse.json();
  templater(template_id, parent_id, json, pre_rules, post_rules);
}
