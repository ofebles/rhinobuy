(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CasaDialogController', CasaDialogController);

    CasaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Casa'];

    function CasaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Casa) {
        var vm = this;

        vm.casa = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.casa.id !== null) {
                Casa.update(vm.casa, onSaveSuccess, onSaveError);
            } else {
                Casa.save(vm.casa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:casaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
