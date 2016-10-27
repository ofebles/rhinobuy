(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ShipmentController', ShipmentController);

    ShipmentController.$inject = ['$scope', '$state', 'Shipment'];

    function ShipmentController ($scope, $state, Shipment) {
        var vm = this;
        
        vm.shipments = [];

        loadAll();

        function loadAll() {
            Shipment.query(function(result) {
                vm.shipments = result;
            });
        }
    }
})();
