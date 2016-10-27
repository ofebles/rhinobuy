(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CasaDetailController', CasaDetailController);

    CasaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Casa'];

    function CasaDetailController($scope, $rootScope, $stateParams, previousState, entity, Casa) {
        var vm = this;

        vm.casa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:casaUpdate', function(event, result) {
            vm.casa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
