(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('ReferenceLanguageController', ReferenceLanguageController);

    ReferenceLanguageController.$inject = ['$scope', '$state', 'ReferenceLanguage', 'ReferenceLanguageSearch'];

    function ReferenceLanguageController ($scope, $state, ReferenceLanguage, ReferenceLanguageSearch) {
        var vm = this;
        
        vm.referenceLanguages = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ReferenceLanguage.query(function(result) {
                vm.referenceLanguages = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ReferenceLanguageSearch.query({query: vm.searchQuery}, function(result) {
                vm.referenceLanguages = result;
            });
        }    }
})();
