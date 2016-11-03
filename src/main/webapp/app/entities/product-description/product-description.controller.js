(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ProductDescriptionController', ProductDescriptionController);

    ProductDescriptionController.$inject = ['$scope', '$state', 'ProductDescription', 'ProductDescriptionSearch'];

    function ProductDescriptionController ($scope, $state, ProductDescription, ProductDescriptionSearch) {
        var vm = this;
        
        vm.productDescriptions = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ProductDescription.query(function(result) {
                vm.productDescriptions = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProductDescriptionSearch.query({query: vm.searchQuery}, function(result) {
                vm.productDescriptions = result;
            });
        }    }
})();
