document.addEventListener('DOMContentLoaded', function() {
    const starsElement = document.getElementById('stars');
    const form = document.getElementById('bewertungsFormular');
    const ratingInput = document.getElementById('ratingValue');
    const commentTextarea = document.querySelector('.comment_Textarea');
    let currentRating = parseInt(ratingInput.value) || 0;

    // Funktion zur Aktualisierung der Sterneanzeige
    function updateStars(rating) {
        let stars = '';
        for (let i = 0; i < 5; i++) {
            stars += i < rating ? '★' : '☆';
        }
        starsElement.textContent = stars;
        starsElement.className = rating > 0 ? 'stars active' : 'stars';
        currentRating = rating;
        ratingInput.value = rating;
    }

    // Klick-Handler zur Auswahl der Bewertung
    starsElement.addEventListener('click', function (e) {
        const bounding = starsElement.getBoundingClientRect();
        const offsetX = e.clientX - bounding.left;
        const starWidth = bounding.width / 5;
        const rating = Math.ceil(offsetX / starWidth);
        updateStars(rating);
    });


    // Hover-Effekt zur Vorschau der Bewertung
    starsElement.addEventListener('mouseover', function(e) {
        const rect = this.getBoundingClientRect();
        const mouseX = e.clientX - rect.left;
        const starWidth = rect.width / 5;
        const hoverIndex = Math.min(4, Math.floor(mouseX / starWidth));

        let tempStars = '';
        for (let i = 0; i < 5; i++) {
            tempStars += i <= hoverIndex ? '★' : '☆';
        }
        this.textContent = tempStars;
    });

    // Zurück zur tatsächlichen Bewertung beim Verlassen
    starsElement.addEventListener('mouseout', function() {
        updateStars(currentRating);
    });

    // Validierung und Absenden des Formulars
    form.addEventListener('submit', function(e) {
        if (currentRating === 0) {
            e.preventDefault();
            alert('Bitte geben Sie eine Bewertung ab!');
            return;
        }

        if (!commentTextarea.value.trim()) {
            e.preventDefault();
            alert('Bitte geben Sie einen Kommentar ein!');
        }
    });

    // Initiale Sterneanzeige bei Seitenaufruf
    updateStars(currentRating);
});
