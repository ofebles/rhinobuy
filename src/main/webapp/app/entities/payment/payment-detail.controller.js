(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PaymentDetailController', PaymentDetailController);

    PaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Payment', 'CustomerPaymentMethod', 'Invoice'];

    function PaymentDetailController($scope, $rootScope, $stateParams, previousState, entity, Payment, CustomerPaymentMethod, Invoice) {
        var vm = this;

        vm.payment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:paymentUpdate', function(event, result) {
            vm.payment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
