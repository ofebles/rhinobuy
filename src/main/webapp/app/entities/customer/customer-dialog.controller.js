(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CustomerDialogController', CustomerDialogController);

    CustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Customer', 'ShopingCart', 'CustomerPaymentMethod', 'WishList', 'User'];

    function CustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Customer, ShopingCart, CustomerPaymentMethod, WishList, User) {
        var vm = this;

        vm.customer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.shopingcarts = ShopingCart.query();
        vm.customerpaymentmethods = CustomerPaymentMethod.query();
        vm.wishlists = WishList.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customer.id !== null) {
                Customer.update(vm.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save(vm.customer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:customerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
