let nextDom = document.getElementById('next');
let prevDom = document.getElementById('prev');
let carouselDom = document.querySelector('.caroussel');
let listItemDom = document.querySelector('.caroussel .list');
let thumbnailDom = document.querySelector('.caroussel .thumbnail');

prevDom.onclick = function(){
    showSlider('prev');
}
nextDom.onclick = function(){
    showSlider('next');
}
let autoSlide = false; 
let timeRunning = 3000;
let timeAutoNext = 7000;
let runTimeOut;
let runAutoRun = setTimeout(()=>{
        nextDom.click();
    }, timeAutoNext);

function showSlider(type){
    let itemSlider = document.querySelectorAll('.caroussel .list .item');
    let itemThumbnail = document.querySelectorAll('.caroussel .thumbnail .item');

    

    if(type === 'next'){
        listItemDom.appendChild(itemSlider[0]);
        thumbnailDom.appendChild(itemThumbnail[0]);
        carouselDom.classList.add('next')
    }
    else{
        let positionLastItem =itemSlider.length - 1;
        listItemDom.prepend(itemSlider[positionLastItem]);
        thumbnailDom.prepend(itemThumbnail[positionLastItem]);
        carouselDom.classList.add('prev');
    }
    clearTimeout(runTimeOut);
    runTimeOut = setTimeout(() =>{
    carouselDom.classList.remove('next');
    carouselDom.classList.remove('prev');
    }, timeRunning);

    clearTimeout(runAutoRun);
        runAutoRun = setTimeout(()=>{
            nextDom.click();
        }, timeAutoNext)
}
