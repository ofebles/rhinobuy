(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('OrdersSearch', OrdersSearch);

    OrdersSearch.$inject = ['$resource'];

    function OrdersSearch($resource) {
        var resourceUrl =  'api/_search/orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
