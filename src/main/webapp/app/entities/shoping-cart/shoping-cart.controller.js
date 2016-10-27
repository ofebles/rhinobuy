(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ShopingCartController', ShopingCartController);

    ShopingCartController.$inject = ['$scope', '$state', 'ShopingCart'];

    function ShopingCartController ($scope, $state, ShopingCart) {
        var vm = this;
        
        vm.shopingCarts = [];

        loadAll();

        function loadAll() {
            ShopingCart.query(function(result) {
                vm.shopingCarts = result;
            });
        }
    }
})();
