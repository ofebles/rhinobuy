(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PictureController', PictureController);

    PictureController.$inject = ['$scope', '$state', 'DataUtils', 'Picture'];

    function PictureController ($scope, $state, DataUtils, Picture) {
        var vm = this;
        
        vm.pictures = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Picture.query(function(result) {
                vm.pictures = result;
            });
        }
    }
})();
