(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'ProductDescription', 'Picture', 'Category', 'Supplier', 'WishList', 'ShopingCart', 'Promotion'];

    function ProductDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, ProductDescription, Picture, Category, Supplier, WishList, ShopingCart, Promotion) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
