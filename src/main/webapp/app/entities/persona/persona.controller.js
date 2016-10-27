(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PersonaController', PersonaController);

    PersonaController.$inject = ['$scope', '$state', 'Persona'];

    function PersonaController ($scope, $state, Persona) {
        var vm = this;
        
        vm.personas = [];

        loadAll();

        function loadAll() {
            Persona.query(function(result) {
                vm.personas = result;
            });
        }
    }
})();
