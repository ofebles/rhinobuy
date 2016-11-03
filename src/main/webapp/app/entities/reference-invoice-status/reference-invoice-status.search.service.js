(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('ReferenceInvoiceStatusSearch', ReferenceInvoiceStatusSearch);

    ReferenceInvoiceStatusSearch.$inject = ['$resource'];

    function ReferenceInvoiceStatusSearch($resource) {
        var resourceUrl =  'api/_search/reference-invoice-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
