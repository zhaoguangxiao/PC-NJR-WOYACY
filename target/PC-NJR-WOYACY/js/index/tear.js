window.onload = function () {
    var mySwiper = new Swiper('.index-banner', {
        autoplay: {
            delay: 3000,
            stopOnLastSlide: false,
            disableOnInteraction: true,
        },
        loop: true, // 循环模式选项

        // 如果需要滚动条
        scrollbar: {
            el: '.swiper-scrollbar',
        },
    });

    var mySwiper2 = new Swiper('.ta-banner', {
        direction: 'vertical', // 垂直切换选项
        loop: true, // 循环模式选项
        autoplay: {
            delay: 200,
            stopOnLastSlide: false,
            disableOnInteraction: false,
        },
        slidesPerView: 5,
        centeredSlides: true,
    })
}
