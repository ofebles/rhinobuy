(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('PictureDetailController', PictureDetailController);

    PictureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Picture', 'Product'];

    function PictureDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Picture, Product) {
        var vm = this;

        vm.picture = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('rhinobuyApp:pictureUpdate', function(event, result) {
            vm.picture = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
