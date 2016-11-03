(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferencePaymentMethodDeleteController',ReferencePaymentMethodDeleteController);

    ReferencePaymentMethodDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReferencePaymentMethod'];

    function ReferencePaymentMethodDeleteController($uibModalInstance, entity, ReferencePaymentMethod) {
        var vm = this;

        vm.referencePaymentMethod = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ReferencePaymentMethod.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
