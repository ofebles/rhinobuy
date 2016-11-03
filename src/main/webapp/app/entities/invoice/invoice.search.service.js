(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('InvoiceSearch', InvoiceSearch);

    InvoiceSearch.$inject = ['$resource'];

    function InvoiceSearch($resource) {
        var resourceUrl =  'api/_search/invoices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
