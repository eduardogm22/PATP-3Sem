let nextDom = document.getElementById('next');
let prevDom = document.getElementById('prev');
let carouselDom = document.querySelector('.caroussel');
let listItemDom = document.querySelector('.caroussel .list');
let thumbnailDom = document.querySelector('.caroussel .thumbnail');

prevDom.onclick = function () {
    showSlider('prev');
}
nextDom.onclick = function () {
    showSlider('next');
}

let timeRunning = 3000;
let runTimeOut;

function showSlider(type) {
    let itemSlider = document.querySelectorAll('.caroussel .list .item');
    let itemThumbnail = document.querySelectorAll('.caroussel .thumbnail .item');

    if (type === 'next') {
        listItemDom.appendChild(itemSlider[0]);
        thumbnailDom.appendChild(itemThumbnail[0]);
        carouselDom.classList.add('next');
    } else {
        let positionLastItem = itemSlider.length - 1;
        listItemDom.prepend(itemSlider[positionLastItem]);
        thumbnailDom.prepend(itemThumbnail[positionLastItem]);
        carouselDom.classList.add('prev');
    }

    clearTimeout(runTimeOut);
    runTimeOut = setTimeout(() => {
        carouselDom.classList.remove('next');
        carouselDom.classList.remove('prev');
    }, timeRunning);
}

// PESQUISA
document.getElementById('searchInput').addEventListener('input', function () {
    const keyword = this.value.trim().toLowerCase();
    const items = document.querySelectorAll('.faq-item');

    items.forEach(item => {
        const h3 = item.querySelector('h3');
        const p = item.querySelector('p');
        const match = h3.textContent.toLowerCase().includes(keyword) || p.textContent.toLowerCase().includes(keyword);

        if (keyword === '') {
            item.style.display = '';
            p.style.display = 'none';
        } else if (match) {
            item.style.display = 'block';
            p.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });

    document.querySelectorAll('.section').forEach(section => {
        const isSuporte = section.querySelector('.suporte-titulo');
        if (isSuporte) return;

        const visibleItems = section.querySelectorAll('.faq-item:not([style*="display: none"])');
        section.style.display = visibleItems.length > 0 ? '' : 'none';
    });
});
