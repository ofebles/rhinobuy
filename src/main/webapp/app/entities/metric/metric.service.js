(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('Metric', Metric);

    Metric.$inject = ['$resource'];

    function Metric ($resource) {
        var resourceUrl =  'api/metrics/:id';

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
