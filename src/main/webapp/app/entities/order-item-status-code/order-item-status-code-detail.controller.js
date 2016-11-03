(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrderItemStatusCodeDetailController', OrderItemStatusCodeDetailController);

    OrderItemStatusCodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderItemStatusCode'];

    function OrderItemStatusCodeDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderItemStatusCode) {
        var vm = this;

        vm.orderItemStatusCode = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:orderItemStatusCodeUpdate', function(event, result) {
            vm.orderItemStatusCode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
