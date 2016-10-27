(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrderItemStatusCodeController', OrderItemStatusCodeController);

    OrderItemStatusCodeController.$inject = ['$scope', '$state', 'OrderItemStatusCode'];

    function OrderItemStatusCodeController ($scope, $state, OrderItemStatusCode) {
        var vm = this;
        
        vm.orderItemStatusCodes = [];

        loadAll();

        function loadAll() {
            OrderItemStatusCode.query(function(result) {
                vm.orderItemStatusCodes = result;
            });
        }
    }
})();
