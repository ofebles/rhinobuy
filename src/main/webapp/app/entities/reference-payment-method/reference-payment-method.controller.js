(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferencePaymentMethodController', ReferencePaymentMethodController);

    ReferencePaymentMethodController.$inject = ['$scope', '$state', 'ReferencePaymentMethod', 'ReferencePaymentMethodSearch'];

    function ReferencePaymentMethodController ($scope, $state, ReferencePaymentMethod, ReferencePaymentMethodSearch) {
        var vm = this;
        
        vm.referencePaymentMethods = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ReferencePaymentMethod.query(function(result) {
                vm.referencePaymentMethods = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ReferencePaymentMethodSearch.query({query: vm.searchQuery}, function(result) {
                vm.referencePaymentMethods = result;
            });
        }    }
})();
