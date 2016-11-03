(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('ShipmentSearch', ShipmentSearch);

    ShipmentSearch.$inject = ['$resource'];

    function ShipmentSearch($resource) {
        var resourceUrl =  'api/_search/shipments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
