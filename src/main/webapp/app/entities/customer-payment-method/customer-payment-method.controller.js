(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CustomerPaymentMethodController', CustomerPaymentMethodController);

    CustomerPaymentMethodController.$inject = ['$scope', '$state', 'CustomerPaymentMethod', 'CustomerPaymentMethodSearch'];

    function CustomerPaymentMethodController ($scope, $state, CustomerPaymentMethod, CustomerPaymentMethodSearch) {
        var vm = this;
        
        vm.customerPaymentMethods = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            CustomerPaymentMethod.query(function(result) {
                vm.customerPaymentMethods = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CustomerPaymentMethodSearch.query({query: vm.searchQuery}, function(result) {
                vm.customerPaymentMethods = result;
            });
        }    }
})();
