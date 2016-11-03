(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrderItemStatusCodeDialogController', OrderItemStatusCodeDialogController);

    OrderItemStatusCodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderItemStatusCode'];

    function OrderItemStatusCodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrderItemStatusCode) {
        var vm = this;

        vm.orderItemStatusCode = entity;
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
            if (vm.orderItemStatusCode.id !== null) {
                OrderItemStatusCode.update(vm.orderItemStatusCode, onSaveSuccess, onSaveError);
            } else {
                OrderItemStatusCode.save(vm.orderItemStatusCode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:orderItemStatusCodeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
