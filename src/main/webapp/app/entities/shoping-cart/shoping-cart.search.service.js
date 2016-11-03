(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('ShopingCartSearch', ShopingCartSearch);

    ShopingCartSearch.$inject = ['$resource'];

    function ShopingCartSearch($resource) {
        var resourceUrl =  'api/_search/shoping-carts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
