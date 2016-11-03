(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ShopingCartDialogController', ShopingCartDialogController);

    ShopingCartDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ShopingCart', 'Customer', 'Product'];

    function ShopingCartDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ShopingCart, Customer, Product) {
        var vm = this;

        vm.shopingCart = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customers = Customer.query();
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shopingCart.id !== null) {
                ShopingCart.update(vm.shopingCart, onSaveSuccess, onSaveError);
            } else {
                ShopingCart.save(vm.shopingCart, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:shopingCartUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
