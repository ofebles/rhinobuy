(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceInvoiceStatusController', ReferenceInvoiceStatusController);

    ReferenceInvoiceStatusController.$inject = ['$scope', '$state', 'ReferenceInvoiceStatus', 'ReferenceInvoiceStatusSearch'];

    function ReferenceInvoiceStatusController ($scope, $state, ReferenceInvoiceStatus, ReferenceInvoiceStatusSearch) {
        var vm = this;
        
        vm.referenceInvoiceStatuses = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ReferenceInvoiceStatus.query(function(result) {
                vm.referenceInvoiceStatuses = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ReferenceInvoiceStatusSearch.query({query: vm.searchQuery}, function(result) {
                vm.referenceInvoiceStatuses = result;
            });
        }    }
})();
