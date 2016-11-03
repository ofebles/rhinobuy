(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CustomerPaymentMethodDeleteController',CustomerPaymentMethodDeleteController);

    CustomerPaymentMethodDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerPaymentMethod'];

    function CustomerPaymentMethodDeleteController($uibModalInstance, entity, CustomerPaymentMethod) {
        var vm = this;

        vm.customerPaymentMethod = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerPaymentMethod.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
