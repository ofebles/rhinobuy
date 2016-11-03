(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CustomerPaymentMethodDialogController', CustomerPaymentMethodDialogController);

    CustomerPaymentMethodDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerPaymentMethod', 'Customer', 'ReferencePaymentMethod'];

    function CustomerPaymentMethodDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerPaymentMethod, Customer, ReferencePaymentMethod) {
        var vm = this;

        vm.customerPaymentMethod = entity;
        vm.clear = clear;
        vm.save = save;
        vm.customers = Customer.query();
        vm.referencepaymentmethods = ReferencePaymentMethod.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerPaymentMethod.id !== null) {
                CustomerPaymentMethod.update(vm.customerPaymentMethod, onSaveSuccess, onSaveError);
            } else {
                CustomerPaymentMethod.save(vm.customerPaymentMethod, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:customerPaymentMethodUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
