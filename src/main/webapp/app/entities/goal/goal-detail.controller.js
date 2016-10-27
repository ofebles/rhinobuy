(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('GoalDetailController', GoalDetailController);

    GoalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Goal', 'User'];

    function GoalDetailController($scope, $rootScope, $stateParams, previousState, entity, Goal, User) {
        var vm = this;

        vm.goal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:goalUpdate', function(event, result) {
            vm.goal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
