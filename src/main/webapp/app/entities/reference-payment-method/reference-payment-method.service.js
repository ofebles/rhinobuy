(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('ReferencePaymentMethod', ReferencePaymentMethod);

    ReferencePaymentMethod.$inject = ['$resource'];

    function ReferencePaymentMethod ($resource) {
        var resourceUrl =  'api/reference-payment-methods/:id';

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
