(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('MetricDialogController', MetricDialogController);

    MetricDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Metric', 'Entry', 'Goal'];

    function MetricDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Metric, Entry, Goal) {
        var vm = this;

        vm.metric = entity;
        vm.clear = clear;
        vm.save = save;
        vm.entries = Entry.query();
        vm.goals = Goal.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.metric.id !== null) {
                Metric.update(vm.metric, onSaveSuccess, onSaveError);
            } else {
                Metric.save(vm.metric, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:metricUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
