(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ShipmentDialogController', ShipmentDialogController);

    ShipmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Shipment', 'Orders', 'Invoice'];

    function ShipmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Shipment, Orders, Invoice) {
        var vm = this;

        vm.shipment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.orders = Orders.query();
        vm.invoices = Invoice.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shipment.id !== null) {
                Shipment.update(vm.shipment, onSaveSuccess, onSaveError);
            } else {
                Shipment.save(vm.shipment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:shipmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.shipmentDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
