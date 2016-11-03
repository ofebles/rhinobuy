(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrdersDialogController', OrdersDialogController);

    OrdersDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orders', 'Invoice', 'Shipment', 'OrderItem', 'Product'];

    function OrdersDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Orders, Invoice, Shipment, OrderItem, Product) {
        var vm = this;

        vm.orders = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.invoices = Invoice.query();
        vm.shipments = Shipment.query();
        vm.orderitems = OrderItem.query();
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orders.id !== null) {
                Orders.update(vm.orders, onSaveSuccess, onSaveError);
            } else {
                Orders.save(vm.orders, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:ordersUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datePlaced = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
