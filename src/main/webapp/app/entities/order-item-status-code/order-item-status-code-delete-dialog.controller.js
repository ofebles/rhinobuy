(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrderItemStatusCodeDeleteController',OrderItemStatusCodeDeleteController);

    OrderItemStatusCodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderItemStatusCode'];

    function OrderItemStatusCodeDeleteController($uibModalInstance, entity, OrderItemStatusCode) {
        var vm = this;

        vm.orderItemStatusCode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderItemStatusCode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
