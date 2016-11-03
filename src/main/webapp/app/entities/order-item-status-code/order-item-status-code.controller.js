(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrderItemStatusCodeController', OrderItemStatusCodeController);

    OrderItemStatusCodeController.$inject = ['$scope', '$state', 'OrderItemStatusCode', 'OrderItemStatusCodeSearch'];

    function OrderItemStatusCodeController ($scope, $state, OrderItemStatusCode, OrderItemStatusCodeSearch) {
        var vm = this;
        
        vm.orderItemStatusCodes = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            OrderItemStatusCode.query(function(result) {
                vm.orderItemStatusCodes = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            OrderItemStatusCodeSearch.query({query: vm.searchQuery}, function(result) {
                vm.orderItemStatusCodes = result;
            });
        }    }
})();
