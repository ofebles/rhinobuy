(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('CustomerPaymentMethodSearch', CustomerPaymentMethodSearch);

    CustomerPaymentMethodSearch.$inject = ['$resource'];

    function CustomerPaymentMethodSearch($resource) {
        var resourceUrl =  'api/_search/customer-payment-methods/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
