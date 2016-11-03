(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PromotionDetailController', PromotionDetailController);

    PromotionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Promotion', 'Product'];

    function PromotionDetailController($scope, $rootScope, $stateParams, previousState, entity, Promotion, Product) {
        var vm = this;

        vm.promotion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:promotionUpdate', function(event, result) {
            vm.promotion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
