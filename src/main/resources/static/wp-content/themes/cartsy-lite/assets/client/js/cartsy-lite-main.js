(function ($) {
  'use strict';

  init();

  function init() {
    showMobileHeaderSearch();
    windowHasScrollbar();
    checkClientUser();
  }

  function windowHasScrollbar() {
    if (window.innerWidth > document.body.clientWidth) {
      $('body').addClass('windowHasScrollbar');
    }
  }

  function checkClientUser() {
    if (window.navigator.appVersion.indexOf('Mac') !== -1) {
      $('body').addClass('usingMacOS');
    }
  }

  //ioS device detection
  function iOS() {
    return (
      [
        'iPad Simulator',
        'iPhone Simulator',
        'iPod Simulator',
        'iPad',
        'iPhone',
        'iPod',
      ].includes(navigator.platform) ||
      // iPad on iOS 13 detection
      (navigator.userAgent.includes('Mac') && 'ontouchend' in document)
    );
  }

  // Mobile Menu
  if ($('.cartsylite-main-menu').length) {
    $(
      '.cartsylite-main-menu .menu-item-has-children>.menu-drop-down-selector'
    ).on('click', function (e) {
      e.preventDefault();

      $(this).toggleClass('children-active');
      $(this).siblings('ul').slideToggle();
      $(this).attr('title') == 'open'
        ? $(this).attr('title', 'close')
        : $(this).attr('title', 'open');
    });
  }

  // Header position observer
  function scrollPositionObserver() {
    var header = $('.cartsylite-menu-area');
    var body = $('body');
    var scrollPosition = $(window).scrollTop();
    if (scrollPosition >= 30) {
      header.addClass('header-on-float');
      body.addClass('cartsylite-on-scroll');
    } else {
      header.removeClass('header-on-float');
      body.removeClass('cartsylite-on-scroll');
    }
  }

  // Window on load functions here
  $(window).on('load', function () {
    //init scroll position observer
    scrollPositionObserver();

    $("link[rel='preload']").attr('rel', 'stylesheet');

    // Sidebar custom scrollbar
    var menuScrollbar = $('.cartsylite-menu-drawer .cartsylite-menu-wrapper')
      .overlayScrollbars({
        autoUpdate: true,
        scrollbars: {
          autoHide: 'leave',
        },
      })
      .overlayScrollbars();

    $('.cartsylite-menu-toggler').on('click', function () {
      menuScrollbar.update();
    });

    //Destroy scrollbar on ios device
    if (iOS()) {
      if ($('.cartsylite-menu-drawer').length) {
        menuScrollbar.destroy();
      }
    }

    //Site preloader
    if ($('.cartsylite-site-preloader').length) {
      $('.cartsylite-site-preloader').fadeOut();
    }
  });

  $(window).on("scroll", function () {
    scrollPositionObserver();
  });

  // Mobile menu Toggler
  if ($('.cartsylite-menu-toggler').length) {
    var menuDrawer = $('.cartsylite-menu-drawer');
    var drawerOverlay = $('.cartsylite-drawer-overlay');

    $(document).mouseup((e) => {
      if (
        !$('.cartsylite-menu-drawer, .cartsylite-menu-drawer *').is(e.target)
      ) {
        $(menuDrawer).removeClass('open');
        $(drawerOverlay).removeClass('show');
      }
    });

    $('.cartsylite-menu-toggler').on('click', function () {
      $(menuDrawer).addClass('open');
      $(drawerOverlay).addClass('show');
      $('body').css({
        overflow: 'hidden',
        touchAction: 'none',
      });
    });

    $('.cartsylite-menu-drawer-close').on('click', function () {
      $(menuDrawer).removeClass('open');
      $(drawerOverlay).removeClass('show');
      $('body').css({
        overflow: '',
        touchAction: '',
        width: '',
      });
    });
  }

  //Show mobile header search
  function showMobileHeaderSearch() {
    var cartsyliteHeaderSearchBtn = $('.cartsylite-header-search-button');
    handleHeaderSearchToggle(cartsyliteHeaderSearchBtn);
  }

  // home page and native search toggle handler
  function handleHeaderSearchToggle(searchHandler) {
    searchHandler.on('click', function () {
      //Search form toggle
      if ($('.cartsylite-header-search-form').length) {
        $('.cartsylite-header-search-form').toggleClass('show-mobile-search');
      }
      if ($('.cartsylite-global-search-container').length) {
        $('.cartsylite-global-search-container').toggleClass(
          'show-mobile-search'
        );
      }
      $(document).keyup(function (e) {
        if (e.key === 'Escape') {
          // escape key maps to keycode `27`
          $('.cartsylite-header-search-form').removeClass('show-mobile-search');
        }
      });
      $('.cartsylite-site-header').on('click', function (e) {
        e.stopPropagation();
      });
      $('body').on('click', function () {
        $('.cartsylite-header-search-form').removeClass('show-mobile-search');
      });
    });
  }

  // main-woo-script.js
  if ($('.cartsylite-main-menu').length) {
    let menu = $('.cartsylite-main-menu');
    let links = menu.children('.menu-item').find('a');

    function toggleFocus(element, event) {
      let $this = element,
        $parents = $this.parents('.menu-item');

      if (-1 !== $parents.attr('class').indexOf('focus')) {
        $parents.removeClass('focus');
      } else {
        $parents.addClass('focus');
      }

      $this = $this.parents('#site-horizontal-navigation');
    }

    links.each(function () {
      $(this).on('focus', function (el) {
        toggleFocus($(this), el.type);
      });
      $(this).on('blur', function (el) {
        toggleFocus($(this), el.type);
      });
    });
  }

  // product grid gallery slider
  if ($('.cartsylite-product-card-slider-init').length) {
    $('.cartsylite-product-card-slider-init').flexslider({
      animation: 'slide',
      pausePlay: false,
      animationLoop: false,
      slideshow: false,
      keyboard: true,
    });
  }

  // mini cart
  if ($('.cartsylite-mini-cart-wrapper').length) {
    $('.cartsylite-mini-cart-wrapper').on('click', function () {
      $('.cartsylite-mini-cart-items-wrapper').toggleClass(
        'cartsylite-mini-cart-active'
      );
    });
    $('.cartsylite-mini-cart-items-wrapper, .cartsylite-mini-cart-wrapper').on(
      'click',
      function (e) {
        e.stopPropagation();
      }
    );
    $('body').on('click', function () {
      $('.cartsylite-mini-cart-items-wrapper').removeClass(
        'cartsylite-mini-cart-active'
      );
    });
    $(document).keyup(function (e) {
      if (e.key === 'Escape') {
        // escape key maps to keycode `27`
        $('.cartsylite-mini-cart-items-wrapper').removeClass(
          'cartsylite-mini-cart-active'
        );
      }
    });
  }
})(jQuery);
