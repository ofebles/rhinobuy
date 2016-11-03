(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('OrderItemStatusCodeSearch', OrderItemStatusCodeSearch);

    OrderItemStatusCodeSearch.$inject = ['$resource'];

    function OrderItemStatusCodeSearch($resource) {
        var resourceUrl =  'api/_search/order-item-status-codes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
