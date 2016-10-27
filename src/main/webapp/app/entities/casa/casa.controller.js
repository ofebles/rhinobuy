(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('CasaController', CasaController);

    CasaController.$inject = ['$scope', '$state', 'Casa'];

    function CasaController ($scope, $state, Casa) {
        var vm = this;
        
        vm.casas = [];

        loadAll();

        function loadAll() {
            Casa.query(function(result) {
                vm.casas = result;
            });
        }
    }
})();
