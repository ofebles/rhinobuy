(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('ReferenceLanguage', ReferenceLanguage);

    ReferenceLanguage.$inject = ['$resource'];

    function ReferenceLanguage ($resource) {
        var resourceUrl =  'api/reference-languages/:id';

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
