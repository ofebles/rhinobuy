(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CustomerDialogController', CustomerDialogController);

    CustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Customer', 'WishList', 'User', 'ShopingCart', 'CustomerPaymentMethod'];

    function CustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Customer, WishList, User, ShopingCart, CustomerPaymentMethod) {
        var vm = this;

        vm.customer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.wishlists = WishList.query({filter: 'customer-is-null'});
        $q.all([vm.customer.$promise, vm.wishlists.$promise]).then(function() {
            if (!vm.customer.wishlist || !vm.customer.wishlist.id) {
                return $q.reject();
            }
            return WishList.get({id : vm.customer.wishlist.id}).$promise;
        }).then(function(wishlist) {
            vm.wishlists.push(wishlist);
        });
        vm.users = User.query();
        vm.shopingcarts = ShopingCart.query();
        vm.customerpaymentmethods = CustomerPaymentMethod.query();

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
