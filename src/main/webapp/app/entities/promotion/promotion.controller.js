(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PromotionController', PromotionController);

    PromotionController.$inject = ['$scope', '$state', 'Promotion', 'PromotionSearch'];

    function PromotionController ($scope, $state, Promotion, PromotionSearch) {
        var vm = this;
        
        vm.promotions = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Promotion.query(function(result) {
                vm.promotions = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PromotionSearch.query({query: vm.searchQuery}, function(result) {
                vm.promotions = result;
            });
        }    }
})();
