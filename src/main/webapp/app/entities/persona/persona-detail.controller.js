(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PersonaDetailController', PersonaDetailController);

    PersonaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Persona'];

    function PersonaDetailController($scope, $rootScope, $stateParams, previousState, entity, Persona) {
        var vm = this;

        vm.persona = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:personaUpdate', function(event, result) {
            vm.persona = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
