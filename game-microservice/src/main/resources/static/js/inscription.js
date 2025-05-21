document.getElementById('register-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    const messageEl = document.getElementById('message');

    if (password !== confirmPassword) {
      messageEl.textContent = "Les mots de passe ne correspondent pas.";
      return;
    }

    try {
      const response = await fetch('http://localhost:8081/api/users/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username,
          password
        })
      });

      if (!response.ok) {
            const error = await response.text();
            messageEl.textContent = `Erreur : ${error}`;
            return;
      }
      const data = await response.text();
      console.log(data);

      if (data) {

        document.cookie = `authToken=${data}; path=/; max-age=${60 * 60 * 24 * 7}; Secure; SameSite=Strict`;
        messageEl.style.color = 'green';
        messageEl.textContent = "Inscription réussie ! Token enregistré.";
        window.location.href = '/';
      } else {
        messageEl.textContent = "Erreur : aucun token reçu.";
      }

    } catch (err) {
      console.error(err);
      messageEl.textContent = "Erreur de connexion au serveur.";
    }
  });