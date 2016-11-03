(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ProductDescriptionDetailController', ProductDescriptionDetailController);

    ProductDescriptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductDescription', 'ReferenceLanguage', 'Product'];

    function ProductDescriptionDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductDescription, ReferenceLanguage, Product) {
        var vm = this;

        vm.productDescription = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:productDescriptionUpdate', function(event, result) {
            vm.productDescription = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
