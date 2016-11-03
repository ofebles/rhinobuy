(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('ReferenceLanguageSearch', ReferenceLanguageSearch);

    ReferenceLanguageSearch.$inject = ['$resource'];

    function ReferenceLanguageSearch($resource) {
        var resourceUrl =  'api/_search/reference-languages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
