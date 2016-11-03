(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ShipmentDetailController', ShipmentDetailController);

    ShipmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Shipment', 'Orders', 'Invoice'];

    function ShipmentDetailController($scope, $rootScope, $stateParams, previousState, entity, Shipment, Orders, Invoice) {
        var vm = this;

        vm.shipment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:shipmentUpdate', function(event, result) {
            vm.shipment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
