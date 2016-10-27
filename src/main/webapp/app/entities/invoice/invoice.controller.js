(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('InvoiceController', InvoiceController);

    InvoiceController.$inject = ['$scope', '$state', 'Invoice'];

    function InvoiceController ($scope, $state, Invoice) {
        var vm = this;
        
        vm.invoices = [];

        loadAll();

        function loadAll() {
            Invoice.query(function(result) {
                vm.invoices = result;
            });
        }
    }
})();
