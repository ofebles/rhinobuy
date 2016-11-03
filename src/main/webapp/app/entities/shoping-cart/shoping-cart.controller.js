(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ShopingCartController', ShopingCartController);

    ShopingCartController.$inject = ['$scope', '$state', 'ShopingCart', 'ShopingCartSearch'];

    function ShopingCartController ($scope, $state, ShopingCart, ShopingCartSearch) {
        var vm = this;
        
        vm.shopingCarts = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ShopingCart.query(function(result) {
                vm.shopingCarts = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ShopingCartSearch.query({query: vm.searchQuery}, function(result) {
                vm.shopingCarts = result;
            });
        }    }
})();
