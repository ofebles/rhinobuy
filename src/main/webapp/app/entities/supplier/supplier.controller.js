(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('SupplierController', SupplierController);

    SupplierController.$inject = ['$scope', '$state', 'Supplier', 'SupplierSearch'];

    function SupplierController ($scope, $state, Supplier, SupplierSearch) {
        var vm = this;
        
        vm.suppliers = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Supplier.query(function(result) {
                vm.suppliers = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SupplierSearch.query({query: vm.searchQuery}, function(result) {
                vm.suppliers = result;
            });
        }    }
})();
