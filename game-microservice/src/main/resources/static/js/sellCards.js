import { templater } from "./modules/templater.js";

// Configuration
const API_URL = 'http://127.0.0.1:8081/api/cards/available';

// Éléments DOM
const cardsContainer = document.getElementById('cards-grid');
const filtersContainer = document.getElementById('filters-container');

// État global
let allCards = [];
let filteredCards = [];

function flatten(json, parentKey = "", result = {}) {
  for (let [key, value] of Object.entries(json)) {
    let newKey = parentKey ? `${parentKey}-${key}` : key;

    if (typeof value === "object" && value !== null && !Array.isArray(value)) {
      flatten(value, newKey, result);
    } else {
      result[newKey] = value;
    }
  }
  return result;
}

// Récupère le token depuis les cookies
function getAuthTokenFromCookie() {
  const match = document.cookie.match(/(?:^|; )authToken=([^;]+)/);
  return match ? match[1] : null;
}

// Fait la requête avec le token en header
async function fetchMyCards() {
  const token = getAuthTokenFromCookie();

  if (!token) {
    console.error('Token non trouvé dans les cookies');
    return;
  }

  try {
    const response = await fetch('/api/cards/my', {
      method: 'GET',
      headers: {
        'Authorization': token
      }
    });

    if (!response.ok) {
      throw new Error(`Erreur HTTP : ${response.status}`);
    }

    allCards = await response.json();
    filteredCards = [...allCards];
    console.table(filteredCards);
    templater("card-template", "cards-grid", filteredCards);
  } catch (error) {
    console.error('Erreur lors de la récupération des cartes :', error);
  }
}

// Appelle la fonction
fetchMyCards();

// Gestion des événements
function setupEventListeners() {
    // Filtres
    document.getElementById('affinity-filter')?.addEventListener('change', applyFilters);
    document.getElementById('family-filter')?.addEventListener('change', applyFilters);
    document.getElementById('price-filter')?.addEventListener('change', applyFilters);

    // Réinitialisation
    document.getElementById('reset-filters-btn')?.addEventListener('click', resetFilters);
}

// Applique les filtres
function applyFilters() {
    const affinityFilter = document.getElementById('affinity-filter').value;
    const familyFilter = document.getElementById('family-filter').value;
    const priceFilter = document.getElementById('price-filter').value;

    filteredCards = allCards.filter(card => {
        return (affinityFilter === 'all' || card.affinity === affinityFilter) &&
               (familyFilter === 'all' || card.family === familyFilter) &&
               (priceFilter === 'all' || checkPriceRange(card.price, priceFilter));
    });

    currentPage = 1;
}

// Vérifie la plage de prix
function checkPriceRange(price, range) {
    const [min, max] = range === '20+' ? [20, Infinity] : range.split('-').map(Number);
    return price >= min && price <= max;
}

// Réinitialise les filtres
function resetFilters() {
    document.getElementById('affinity-filter').value = 'all';
    document.getElementById('family-filter').value = 'all';
    document.getElementById('price-filter').value = 'all';
    filteredCards = [...allCards];
    currentPage = 1;
    renderCards();
}

document.addEventListener('DOMContentLoaded', function () {
  // Ici on attache l'événement à un parent, par exemple le body ou le conteneur des cartes
  document.body.addEventListener('click', function (event) {

    if (event.target && event.target.classList.contains('sell-button')) {

      const cardElement = event.target.closest('.card');
      const form = cardElement.querySelector('.buy-form');

      // Empêche le comportement par défaut du bouton (soumettre le formulaire normalement)
      event.preventDefault();

      // Crée un FormData à partir du formulaire caché
      const formData = new FormData(form);
      async function oui(form, token) {
          try {
                  // Envoi du formulaire avec fetch
                  const response = await fetch(form.action, {
                    headers: {
                      'Authorization': token
                    },
                    method: form.method,
                  });

                  if (!response.ok) {
                    throw new Error(`Erreur HTTP : ${response.status}`);
                  }
                  console.log('oui');
                   window.location.href = '/sellCards';

                } catch (err) {
                  console.error('Erreur lors de l\'envoi du formulaire :', err);
          }
      }
        const token = getAuthTokenFromCookie();
      oui(form, token);

    }
  });
});