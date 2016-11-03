(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferencePaymentMethodDialogController', ReferencePaymentMethodDialogController);

    ReferencePaymentMethodDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ReferencePaymentMethod'];

    function ReferencePaymentMethodDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ReferencePaymentMethod) {
        var vm = this;

        vm.referencePaymentMethod = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.referencePaymentMethod.id !== null) {
                ReferencePaymentMethod.update(vm.referencePaymentMethod, onSaveSuccess, onSaveError);
            } else {
                ReferencePaymentMethod.save(vm.referencePaymentMethod, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:referencePaymentMethodUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
