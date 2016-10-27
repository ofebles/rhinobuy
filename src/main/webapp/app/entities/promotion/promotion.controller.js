(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PromotionController', PromotionController);

    PromotionController.$inject = ['$scope', '$state', 'Promotion'];

    function PromotionController ($scope, $state, Promotion) {
        var vm = this;
        
        vm.promotions = [];

        loadAll();

        function loadAll() {
            Promotion.query(function(result) {
                vm.promotions = result;
            });
        }
    }
})();
