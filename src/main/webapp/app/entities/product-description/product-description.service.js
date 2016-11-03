(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('ProductDescription', ProductDescription);

    ProductDescription.$inject = ['$resource'];

    function ProductDescription ($resource) {
        var resourceUrl =  'api/product-descriptions/:id';

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
