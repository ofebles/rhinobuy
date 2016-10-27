(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferencePaymentMethodController', ReferencePaymentMethodController);

    ReferencePaymentMethodController.$inject = ['$scope', '$state', 'ReferencePaymentMethod'];

    function ReferencePaymentMethodController ($scope, $state, ReferencePaymentMethod) {
        var vm = this;
        
        vm.referencePaymentMethods = [];

        loadAll();

        function loadAll() {
            ReferencePaymentMethod.query(function(result) {
                vm.referencePaymentMethods = result;
            });
        }
    }
})();
