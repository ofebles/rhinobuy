(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceInvoiceStatusDetailController', ReferenceInvoiceStatusDetailController);

    ReferenceInvoiceStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ReferenceInvoiceStatus'];

    function ReferenceInvoiceStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, ReferenceInvoiceStatus) {
        var vm = this;

        vm.referenceInvoiceStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:referenceInvoiceStatusUpdate', function(event, result) {
            vm.referenceInvoiceStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
