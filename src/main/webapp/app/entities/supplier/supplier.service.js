(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('Supplier', Supplier);

    Supplier.$inject = ['$resource'];

    function Supplier ($resource) {
        var resourceUrl =  'api/suppliers/:id';

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
