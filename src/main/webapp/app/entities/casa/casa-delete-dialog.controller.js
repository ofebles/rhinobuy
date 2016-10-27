(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CasaDeleteController',CasaDeleteController);

    CasaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Casa'];

    function CasaDeleteController($uibModalInstance, entity, Casa) {
        var vm = this;

        vm.casa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Casa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
