(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PaymentController', PaymentController);

    PaymentController.$inject = ['$scope', '$state', 'Payment'];

    function PaymentController ($scope, $state, Payment) {
        var vm = this;
        
        vm.payments = [];

        loadAll();

        function loadAll() {
            Payment.query(function(result) {
                vm.payments = result;
            });
        }
    }
})();
