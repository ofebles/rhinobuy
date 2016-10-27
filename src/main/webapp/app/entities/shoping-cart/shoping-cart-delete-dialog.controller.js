(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ShopingCartDeleteController',ShopingCartDeleteController);

    ShopingCartDeleteController.$inject = ['$uibModalInstance', 'entity', 'ShopingCart'];

    function ShopingCartDeleteController($uibModalInstance, entity, ShopingCart) {
        var vm = this;

        vm.shopingCart = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ShopingCart.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
