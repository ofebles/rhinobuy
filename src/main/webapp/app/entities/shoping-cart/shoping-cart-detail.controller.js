(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ShopingCartDetailController', ShopingCartDetailController);

    ShopingCartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ShopingCart', 'Customer', 'Product'];

    function ShopingCartDetailController($scope, $rootScope, $stateParams, previousState, entity, ShopingCart, Customer, Product) {
        var vm = this;

        vm.shopingCart = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:shopingCartUpdate', function(event, result) {
            vm.shopingCart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
