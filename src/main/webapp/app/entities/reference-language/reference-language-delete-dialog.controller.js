(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceLanguageDeleteController',ReferenceLanguageDeleteController);

    ReferenceLanguageDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReferenceLanguage'];

    function ReferenceLanguageDeleteController($uibModalInstance, entity, ReferenceLanguage) {
        var vm = this;

        vm.referenceLanguage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ReferenceLanguage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
