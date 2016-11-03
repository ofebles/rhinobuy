(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferencePaymentMethodDetailController', ReferencePaymentMethodDetailController);

    ReferencePaymentMethodDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ReferencePaymentMethod'];

    function ReferencePaymentMethodDetailController($scope, $rootScope, $stateParams, previousState, entity, ReferencePaymentMethod) {
        var vm = this;

        vm.referencePaymentMethod = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:referencePaymentMethodUpdate', function(event, result) {
            vm.referencePaymentMethod = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
