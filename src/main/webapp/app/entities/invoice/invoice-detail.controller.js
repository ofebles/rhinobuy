(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('InvoiceDetailController', InvoiceDetailController);

    InvoiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Invoice', 'Shipment', 'Payment', 'ReferenceInvoiceStatus', 'Orders'];

    function InvoiceDetailController($scope, $rootScope, $stateParams, previousState, entity, Invoice, Shipment, Payment, ReferenceInvoiceStatus, Orders) {
        var vm = this;

        vm.invoice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:invoiceUpdate', function(event, result) {
            vm.invoice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
