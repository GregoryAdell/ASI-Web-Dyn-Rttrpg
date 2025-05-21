// gère toute la chaine de templating
// permet des actions de transformations sur la data injectée pour chaque élément
// permet un post traitement si besoin (ici pour gérer les ajouts de températures a posteriori)
export function templater(
  templateID,
  parentID,
  data,
  object_rules = (object) => {
    return object;
  },
  post_rules = (object) => {}
) {
  let template = document.getElementById(templateID);
  let parent = document.getElementById(parentID);
  let index = 0;
  for (let object of data) {
    index += 1;
    let clone = document.importNode(template.content, true);
    let newContent = clone.firstElementChild.innerHTML;
    object = object_rules(object);
    object.index = index;

    // On veut que les clés du json en entrée correspondent aux éléments entre {{}}
    let keys = Object.keys(object);
    for (const key of keys) {
      newContent = replacer(newContent, object[key], key);
    }

    clone.firstElementChild.innerHTML = newContent;
    parent.appendChild(clone);

    post_rules(object);
  }
}

// Helper pratique pour la fonction replace utilisée avec l'option "g" globale
export function replacer(node, value, key) {
  let regex = new RegExp(`{{${key}}}`, "g");
  return node.replace(regex, value);
}
