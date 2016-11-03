(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('WishListDialogController', WishListDialogController);

    WishListDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WishList', 'Product'];

    function WishListDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WishList, Product) {
        var vm = this;

        vm.wishList = entity;
        vm.clear = clear;
        vm.save = save;
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.wishList.id !== null) {
                WishList.update(vm.wishList, onSaveSuccess, onSaveError);
            } else {
                WishList.save(vm.wishList, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:wishListUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
