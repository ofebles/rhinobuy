(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CustomerPaymentMethodController', CustomerPaymentMethodController);

    CustomerPaymentMethodController.$inject = ['$scope', '$state', 'CustomerPaymentMethod'];

    function CustomerPaymentMethodController ($scope, $state, CustomerPaymentMethod) {
        var vm = this;
        
        vm.customerPaymentMethods = [];

        loadAll();

        function loadAll() {
            CustomerPaymentMethod.query(function(result) {
                vm.customerPaymentMethods = result;
            });
        }
    }
})();
