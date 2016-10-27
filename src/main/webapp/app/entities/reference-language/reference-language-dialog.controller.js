(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceLanguageDialogController', ReferenceLanguageDialogController);

    ReferenceLanguageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ReferenceLanguage'];

    function ReferenceLanguageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ReferenceLanguage) {
        var vm = this;

        vm.referenceLanguage = entity;
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
            if (vm.referenceLanguage.id !== null) {
                ReferenceLanguage.update(vm.referenceLanguage, onSaveSuccess, onSaveError);
            } else {
                ReferenceLanguage.save(vm.referenceLanguage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:referenceLanguageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
