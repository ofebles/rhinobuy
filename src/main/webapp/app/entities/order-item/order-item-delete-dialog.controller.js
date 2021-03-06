(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrderItemDeleteController',OrderItemDeleteController);

    OrderItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderItem'];

    function OrderItemDeleteController($uibModalInstance, entity, OrderItem) {
        var vm = this;

        vm.orderItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
