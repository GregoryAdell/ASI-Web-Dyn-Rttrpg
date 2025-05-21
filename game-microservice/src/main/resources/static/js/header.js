document.addEventListener('DOMContentLoaded', function () {
function getAuthTokenFromCookie() {
  const match = document.cookie.match(/(?:^|; )authToken=([^;]+)/);
  return match ? match[1] : null;
}
    const token = getAuthTokenFromCookie();

    // Appel API pour récupérer les informations de l'utilisateur
    async function fetchUserInfo() {
        try {
            const response = await fetch('/api/users/info', {
                method: 'GET',
                headers: {
                    'Authorization': token
                }
            });

            if (!response.ok) {
                throw new Error(`Erreur lors de la récupération des infos : ${response.status}`);
            }

            const data = await response.json();

            // Affichage de l'argent et du nom d'utilisateur dans le header
            document.getElementById('player-money').textContent = data.money + ' €';  // Affiche l'argent
            document.getElementById('player-username').textContent = data.username;  // Affiche le nom d'utilisateur

        } catch (error) {
            console.error('Erreur dans la récupération des infos utilisateurs :', error);
        }
    }

    // On lance la récupération des infos de l'utilisateur dès que le DOM est chargé
    fetchUserInfo();
});