(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrdersDetailController', OrdersDetailController);

    OrdersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orders', 'Invoice', 'Shipment', 'OrderItem', 'Product'];

    function OrdersDetailController($scope, $rootScope, $stateParams, previousState, entity, Orders, Invoice, Shipment, OrderItem, Product) {
        var vm = this;

        vm.orders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:ordersUpdate', function(event, result) {
            vm.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
