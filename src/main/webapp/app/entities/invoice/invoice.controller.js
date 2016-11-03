(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('InvoiceController', InvoiceController);

    InvoiceController.$inject = ['$scope', '$state', 'Invoice', 'InvoiceSearch'];

    function InvoiceController ($scope, $state, Invoice, InvoiceSearch) {
        var vm = this;
        
        vm.invoices = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Invoice.query(function(result) {
                vm.invoices = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            InvoiceSearch.query({query: vm.searchQuery}, function(result) {
                vm.invoices = result;
            });
        }    }
})();
