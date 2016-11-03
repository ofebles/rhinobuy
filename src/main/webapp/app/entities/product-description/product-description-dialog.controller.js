(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ProductDescriptionDialogController', ProductDescriptionDialogController);

    ProductDescriptionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductDescription', 'ReferenceLanguage', 'Product'];

    function ProductDescriptionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductDescription, ReferenceLanguage, Product) {
        var vm = this;

        vm.productDescription = entity;
        vm.clear = clear;
        vm.save = save;
        vm.referencelanguages = ReferenceLanguage.query();
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.productDescription.id !== null) {
                ProductDescription.update(vm.productDescription, onSaveSuccess, onSaveError);
            } else {
                ProductDescription.save(vm.productDescription, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:productDescriptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
