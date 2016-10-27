(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CustomerPaymentMethodDetailController', CustomerPaymentMethodDetailController);

    CustomerPaymentMethodDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerPaymentMethod', 'Customer', 'ReferencePaymentMethod'];

    function CustomerPaymentMethodDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerPaymentMethod, Customer, ReferencePaymentMethod) {
        var vm = this;

        vm.customerPaymentMethod = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:customerPaymentMethodUpdate', function(event, result) {
            vm.customerPaymentMethod = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
