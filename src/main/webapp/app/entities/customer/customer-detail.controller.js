(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Customer', 'WishList', 'User', 'ShopingCart', 'CustomerPaymentMethod'];

    function CustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Customer, WishList, User, ShopingCart, CustomerPaymentMethod) {
        var vm = this;

        vm.customer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
