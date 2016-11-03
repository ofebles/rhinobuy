(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('OrderItemSearch', OrderItemSearch);

    OrderItemSearch.$inject = ['$resource'];

    function OrderItemSearch($resource) {
        var resourceUrl =  'api/_search/order-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
