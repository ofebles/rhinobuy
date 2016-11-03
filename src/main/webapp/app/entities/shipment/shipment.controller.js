(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ShipmentController', ShipmentController);

    ShipmentController.$inject = ['$scope', '$state', 'Shipment', 'ShipmentSearch'];

    function ShipmentController ($scope, $state, Shipment, ShipmentSearch) {
        var vm = this;
        
        vm.shipments = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Shipment.query(function(result) {
                vm.shipments = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ShipmentSearch.query({query: vm.searchQuery}, function(result) {
                vm.shipments = result;
            });
        }    }
})();
