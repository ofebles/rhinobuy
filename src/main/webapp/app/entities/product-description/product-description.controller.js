(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ProductDescriptionController', ProductDescriptionController);

    ProductDescriptionController.$inject = ['$scope', '$state', 'ProductDescription'];

    function ProductDescriptionController ($scope, $state, ProductDescription) {
        var vm = this;
        
        vm.productDescriptions = [];

        loadAll();

        function loadAll() {
            ProductDescription.query(function(result) {
                vm.productDescriptions = result;
            });
        }
    }
})();
