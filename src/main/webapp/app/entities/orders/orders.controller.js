(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrdersController', OrdersController);

    OrdersController.$inject = ['$scope', '$state', 'Orders', 'OrdersSearch'];

    function OrdersController ($scope, $state, Orders, OrdersSearch) {
        var vm = this;
        
        vm.orders = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Orders.query(function(result) {
                vm.orders = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            OrdersSearch.query({query: vm.searchQuery}, function(result) {
                vm.orders = result;
            });
        }    }
})();
