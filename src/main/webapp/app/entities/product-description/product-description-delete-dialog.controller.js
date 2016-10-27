(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ProductDescriptionDeleteController',ProductDescriptionDeleteController);

    ProductDescriptionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProductDescription'];

    function ProductDescriptionDeleteController($uibModalInstance, entity, ProductDescription) {
        var vm = this;

        vm.productDescription = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProductDescription.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
