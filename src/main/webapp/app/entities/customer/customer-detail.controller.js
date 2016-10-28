(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Customer', 'ShopingCart', 'CustomerPaymentMethod', 'WishList', 'User'];

    function CustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Customer, ShopingCart, CustomerPaymentMethod, WishList, User) {
        var vm = this;

        vm.customer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
