(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PromotionDialogController', PromotionDialogController);

    PromotionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Promotion', 'Product'];

    function PromotionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Promotion, Product) {
        var vm = this;

        vm.promotion = entity;
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
            if (vm.promotion.id !== null) {
                Promotion.update(vm.promotion, onSaveSuccess, onSaveError);
            } else {
                Promotion.save(vm.promotion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:promotionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
