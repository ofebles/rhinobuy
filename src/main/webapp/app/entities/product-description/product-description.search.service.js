(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('ProductDescriptionSearch', ProductDescriptionSearch);

    ProductDescriptionSearch.$inject = ['$resource'];

    function ProductDescriptionSearch($resource) {
        var resourceUrl =  'api/_search/product-descriptions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
