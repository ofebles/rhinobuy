(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrderItemDetailController', OrderItemDetailController);

    OrderItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderItem', 'Orders', 'OrderItemStatusCode'];

    function OrderItemDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderItem, Orders, OrderItemStatusCode) {
        var vm = this;

        vm.orderItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:orderItemUpdate', function(event, result) {
            vm.orderItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
