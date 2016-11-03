(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('SupplierDetailController', SupplierDetailController);

    SupplierDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Supplier', 'Product'];

    function SupplierDetailController($scope, $rootScope, $stateParams, previousState, entity, Supplier, Product) {
        var vm = this;

        vm.supplier = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:supplierUpdate', function(event, result) {
            vm.supplier = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
