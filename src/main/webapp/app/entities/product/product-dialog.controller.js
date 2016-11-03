(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ProductDialogController', ProductDialogController);

    ProductDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Product', 'ProductDescription', 'Picture', 'Category', 'Supplier', 'WishList', 'ShopingCart', 'Promotion'];

    function ProductDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Product, ProductDescription, Picture, Category, Supplier, WishList, ShopingCart, Promotion) {
        var vm = this;

        vm.product = entity;
        vm.clear = clear;
        vm.save = save;
        vm.products = Product.query();
        vm.productdescriptions = ProductDescription.query();
        vm.pictures = Picture.query();
        vm.categories = Category.query();
        vm.suppliers = Supplier.query();
        vm.wishlists = WishList.query();
        vm.shopingcarts = ShopingCart.query();
        vm.promotions = Promotion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.product.id !== null) {
                Product.update(vm.product, onSaveSuccess, onSaveError);
            } else {
                Product.save(vm.product, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:productUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
