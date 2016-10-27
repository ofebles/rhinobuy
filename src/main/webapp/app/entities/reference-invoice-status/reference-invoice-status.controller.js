(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceInvoiceStatusController', ReferenceInvoiceStatusController);

    ReferenceInvoiceStatusController.$inject = ['$scope', '$state', 'ReferenceInvoiceStatus'];

    function ReferenceInvoiceStatusController ($scope, $state, ReferenceInvoiceStatus) {
        var vm = this;
        
        vm.referenceInvoiceStatuses = [];

        loadAll();

        function loadAll() {
            ReferenceInvoiceStatus.query(function(result) {
                vm.referenceInvoiceStatuses = result;
            });
        }
    }
})();
