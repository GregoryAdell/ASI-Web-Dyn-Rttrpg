function deleteCookie(name) {
    // Crée un cookie avec une date d'expiration dans le passé (pour le supprimer)
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/';
}

deleteCookie('authToken');

const form = document.getElementById('loginForm');
const messageDiv = document.getElementById('message');

form.addEventListener('submit', async (e) => {
  e.preventDefault();

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  try {
    const response = await fetch('http://localhost:8081/api/users/login', {
      method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username,
          password
        })
      });

    if (!response.ok) {
      throw new Error('Identifiants incorrects');
    }

    const token = await response.text(); // Si le backend renvoie juste une chaîne de caractères
    document.cookie = `authToken=${token}; path=/; max-age=${60 * 60 * 24 * 7}; Secure; SameSite=Strict`;
    messageDiv.style.color = 'green';
    messageDiv.textContent = 'Connexion réussie ! Token : ' + token;
    window.location.href = '/';
  } catch (error) {
    messageDiv.style.color = 'red';
    messageDiv.textContent = error.message;
  }
});