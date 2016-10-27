(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('OrderItemStatusCode', OrderItemStatusCode);

    OrderItemStatusCode.$inject = ['$resource'];

    function OrderItemStatusCode ($resource) {
        var resourceUrl =  'api/order-item-status-codes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
