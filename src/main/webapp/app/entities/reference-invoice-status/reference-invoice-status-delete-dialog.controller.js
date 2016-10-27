(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceInvoiceStatusDeleteController',ReferenceInvoiceStatusDeleteController);

    ReferenceInvoiceStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReferenceInvoiceStatus'];

    function ReferenceInvoiceStatusDeleteController($uibModalInstance, entity, ReferenceInvoiceStatus) {
        var vm = this;

        vm.referenceInvoiceStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ReferenceInvoiceStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
