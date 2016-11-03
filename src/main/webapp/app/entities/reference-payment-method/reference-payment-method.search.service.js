(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('ReferencePaymentMethodSearch', ReferencePaymentMethodSearch);

    ReferencePaymentMethodSearch.$inject = ['$resource'];

    function ReferencePaymentMethodSearch($resource) {
        var resourceUrl =  'api/_search/reference-payment-methods/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
