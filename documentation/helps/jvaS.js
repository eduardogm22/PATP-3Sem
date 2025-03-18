

window.addEventListener("scroll", function(){
    let header = document.querySelector('#header')
    header.classList.toggle('rolagem', window.scrollY > 0)
})



class MenuBars {
    constructor(menuBar, navList, navLinks) {
        this.menuBar = document.querySelector(menuBar);
        this.navList = document.querySelector(navList);
        this.navLinks = document.querySelectorAll(navLinks);
        this.activeClass = "active";

        this.handleClick = this.handleClick.bind(this);
    }

    animateLinks() {
        this.navLinks.forEach((link, index) => {
            link.style.setProperty("--i", index); 
        });
    }

    handleClick() {
        this.navList.classList.toggle(this.activeClass);
        this.menuBar.classList.toggle(this.activeClass);
        this.animateLinks();
    }

    addClickEvent() {
        this.menuBar.addEventListener("click", this.handleClick);
    }

    init() {
        if (this.menuBar) {
            this.addClickEvent();
        }
        return this;
    }
}

const menuBars = new MenuBars(
    ".menu-bar",
    ".nav-list",
    ".nav-list li"
);

menuBars.init();
