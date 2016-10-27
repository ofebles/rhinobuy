(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('MetricDetailController', MetricDetailController);

    MetricDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Metric', 'Entry', 'Goal'];

    function MetricDetailController($scope, $rootScope, $stateParams, previousState, entity, Metric, Entry, Goal) {
        var vm = this;

        vm.metric = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:metricUpdate', function(event, result) {
            vm.metric = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
