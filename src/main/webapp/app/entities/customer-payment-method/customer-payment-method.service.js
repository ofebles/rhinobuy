(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('CustomerPaymentMethod', CustomerPaymentMethod);

    CustomerPaymentMethod.$inject = ['$resource'];

    function CustomerPaymentMethod ($resource) {
        var resourceUrl =  'api/customer-payment-methods/:id';

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
