(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('InvoiceDialogController', InvoiceDialogController);

    InvoiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Invoice', 'Shipment', 'Payment', 'ReferenceInvoiceStatus', 'Orders'];

    function InvoiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Invoice, Shipment, Payment, ReferenceInvoiceStatus, Orders) {
        var vm = this;

        vm.invoice = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.shipments = Shipment.query();
        vm.payments = Payment.query();
        vm.referenceinvoicestatuses = ReferenceInvoiceStatus.query();
        vm.orders = Orders.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.invoice.id !== null) {
                Invoice.update(vm.invoice, onSaveSuccess, onSaveError);
            } else {
                Invoice.save(vm.invoice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:invoiceUpdate', result);
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
