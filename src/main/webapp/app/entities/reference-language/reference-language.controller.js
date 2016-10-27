(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceLanguageController', ReferenceLanguageController);

    ReferenceLanguageController.$inject = ['$scope', '$state', 'ReferenceLanguage'];

    function ReferenceLanguageController ($scope, $state, ReferenceLanguage) {
        var vm = this;
        
        vm.referenceLanguages = [];

        loadAll();

        function loadAll() {
            ReferenceLanguage.query(function(result) {
                vm.referenceLanguages = result;
            });
        }
    }
})();
