(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('SupplierController', SupplierController);

    SupplierController.$inject = ['$scope', '$state', 'Supplier'];

    function SupplierController ($scope, $state, Supplier) {
        var vm = this;
        
        vm.suppliers = [];

        loadAll();

        function loadAll() {
            Supplier.query(function(result) {
                vm.suppliers = result;
            });
        }
    }
})();
