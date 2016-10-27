(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceInvoiceStatusDialogController', ReferenceInvoiceStatusDialogController);

    ReferenceInvoiceStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ReferenceInvoiceStatus'];

    function ReferenceInvoiceStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ReferenceInvoiceStatus) {
        var vm = this;

        vm.referenceInvoiceStatus = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.referenceInvoiceStatus.id !== null) {
                ReferenceInvoiceStatus.update(vm.referenceInvoiceStatus, onSaveSuccess, onSaveError);
            } else {
                ReferenceInvoiceStatus.save(vm.referenceInvoiceStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:referenceInvoiceStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.invoiceDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
