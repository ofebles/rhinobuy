(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceLanguageDetailController', ReferenceLanguageDetailController);

    ReferenceLanguageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ReferenceLanguage'];

    function ReferenceLanguageDetailController($scope, $rootScope, $stateParams, previousState, entity, ReferenceLanguage) {
        var vm = this;

        vm.referenceLanguage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:referenceLanguageUpdate', function(event, result) {
            vm.referenceLanguage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
